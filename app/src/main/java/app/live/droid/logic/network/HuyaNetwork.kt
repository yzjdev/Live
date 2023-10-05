package app.live.droid.logic.network

import androidx.lifecycle.liveData
import app.live.droid.extensions.UA_MOBILE
import app.live.droid.extensions.UA_NAME
import app.live.droid.extensions.UA_PC
import app.live.droid.logic.model.LiveBean
import app.live.droid.logic.model.Rate
import app.live.droid.logic.model.StreamBean
import cn.hutool.core.codec.Base64Decoder
import cn.hutool.core.net.URLDecoder
import cn.hutool.core.util.CharsetUtil
import cn.hutool.core.util.NumberUtil
import cn.hutool.crypto.digest.MD5
import cn.hutool.http.HttpRequest
import com.alibaba.fastjson2.JSON
import com.alibaba.fastjson2.parseObject
import com.alibaba.fastjson2.toJSONString
import kotlinx.coroutines.Dispatchers
import java.util.regex.Pattern


object HuyaNetwork {

    fun getStream(room: String) = liveData(Dispatchers.IO) {
        var stream: StreamBean? = null
        val result = try {
            val d = HuYa()
            stream = d.getRealUrl(room)!!
            Result.success(stream)
        } catch (e: Exception) {
            Result.failure<StreamBean>(e)
        }
        emit(result)
    }

    fun getLives(page: Int) = liveData(Dispatchers.IO) {

        val result = try {
            val list = ArrayList<LiveBean>()
            val url = "https://live.huya.com/liveHttpUI/getLiveList?iGid=0&iPageSize=120&iPageNo=$page"
            val json = HttpRequest.get(url).header(UA_NAME, UA_PC).execute().body()
            val hasMore = JSON.parseObject(json).run {
                val a = getIntValue("iPageNo")
                val b = getIntValue("iTotalPage")
                a < b
            }
            val arr = json.parseObject().getJSONArray("vList")

            arr.forEach { e ->
                e.toString().parseObject().apply {
                    val roomId = getString("lProfileRoom")
                    val roomUrl = "https://www.huya.com/$roomId"
                    val name = getString("sNick")
                    val title = getString("sIntroduction")
                    val gameName = getString("sGameFullName")
                    val num = getString("lTotalCount").run {
                        NumberUtil.div(this, "10000", 1).toString() + "万"
                    }
                    val avatar = getString("sAvatar180")
                    val coverUrl = getString("sScreenshot")
                    list.add(
                        LiveBean(roomId, roomUrl, name, title, gameName, num, avatar, coverUrl, null, hasMore)
                    )
                }
            }
            Result.success(list)
        } catch (e: Exception) {
            Result.failure<List<LiveBean>>(e)
        }
        emit(result)
    }


}


class HuYa {
    fun getRealUrl(room: String): StreamBean? {
        val url = "https://m.huya.com/$room"
        val res = HttpRequest.get(url)
            .header("Content-Type", "application/x-www-form-urlencoded")
            .header("User-Agent", UA_MOBILE)
            .execute()
            .body()

        val p = Pattern.compile("<script> window.HNF_GLOBAL_INIT =(.*?)</script>")
        val m = p.matcher(res)
        if (m.find()) return live(m.group(1)!!)
        return null
    }

    fun live(info: String): StreamBean {
        val uid = getAnonymousUID()
        val rate = info.parseObject().getJSONObject("roomInfo").getJSONObject("tLiveInfo")
            .getIntValue("iBitRate")
        info.parseObject().getJSONObject("roomInfo")
            .getJSONObject("tLiveInfo").getJSONObject("tLiveStreamInfo").apply {
                val streamArr = getJSONObject("vStreamInfo").getJSONArray("value")
                val rateArr = getJSONObject("vBitRateInfo").getJSONArray("value")

                val urls = arrayListOf<String>()
                streamArr.forEach { item ->
                    item.toJSONString().parseObject().apply {
                        val streamName = getString("sStreamName")

                        val flvUrl = String.format(
                            "%s/%s.%s?%s", getString("sFlvUrl"),
                            streamName,
                            getString("sFlvUrlSuffix"),
                            processAntiCode(getString("sFlvAntiCode"), uid, streamName)
                        )
                        val hlsUrl = String.format(
                            "%s/%s.%s?%s",
                            getString("sHlsUrl"),
                            streamName,
                            getString("sHlsUrlSuffix"),
                            processAntiCode(getString("sHlsAntiCode"), uid, streamName)
                        )
                        urls.add(hlsUrl)
                        urls.add(flvUrl)
                    }
                }

                val rates = arrayListOf<Rate>()
                rateArr.forEach { item ->
                    item.toString().parseObject().apply {
                        rates.add(
                            Rate(
                                getString("sDisplayName"),
                                getIntValue("iBitRate")
                            )
                        )
                    }
                }
                return StreamBean(urls, rate, rates)
            }
    }

    fun processAntiCode(anticode: String, uid: String, streamName: String): String {
        val map = mutableMapOf<String, String>()
        anticode.split("&").forEach { item ->
            item.split("=").apply {
                map[get(0)] = URLDecoder.decode(get(1), CharsetUtil.CHARSET_UTF_8)
            }
        }
        map["ver"] = "1"
        map["sv"] = "2110211124"
        map["seqid"] = "${uid.toLong() + System.currentTimeMillis()}"
        map["uid"] = uid
        map["uuid"] = getUUID()
        var ss = String.format("%s|%s|%s", map["seqid"], map["ctype"], map["t"])
        ss = MD5.create().digestHex(ss)
        map["fm"] = Base64Decoder.decodeStr(map["fm"], CharsetUtil.CHARSET_UTF_8).replace("$0", uid)
            .replace("$1", streamName).replace("$2", ss).replace("$3", map["wsTime"]!!)
        map["wsSecret"] = MD5.create().digestHex(map["fm"])
        map.remove("fm")
        map.remove("txyp")
        val sb = StringBuilder()
        map.forEach { t, u ->
            if (sb.length > 0) sb.append("&")
            sb.append("$t=$u")
        }
        return sb.toString()
    }

    fun getAnonymousUID(): String {
        val res = HttpRequest.post("https://udblgn.huya.com/web/anonymousLogin")
            .body(
                "{\"appId\":5002,\"byPass\":3,\"context\":\"\",\"version\":\"2.4\",\"data\":{}}",
                "application/json;charset=utf-8"
            ).execute().body()

        return res.parseObject().getJSONObject("data").getString("uid")
    }

    fun getUUID(): String {
        val now = System.currentTimeMillis()
        val rand = (Math.random() * 1000).toInt()
        return ((now % 10000000000L * 1000 + rand) % 4294967295L).toString()
    }


}
