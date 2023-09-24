package app.live.droid.logic.network.huya

import androidx.lifecycle.liveData
import app.live.droid.Config
import app.live.droid.logic.model.LiveBean
import app.live.droid.logic.model.Rate
import app.live.droid.logic.model.StreamBean
import cn.hutool.core.codec.Base64Decoder
import cn.hutool.core.net.URLDecoder
import cn.hutool.core.util.CharsetUtil
import cn.hutool.core.util.NumberUtil
import cn.hutool.crypto.digest.MD5
import cn.hutool.http.HttpRequest
import com.alibaba.fastjson2.parseObject
import com.alibaba.fastjson2.toJSONString
import com.google.gson.JsonParser
import kotlinx.coroutines.Dispatchers
import java.util.regex.Pattern

object HuyaRepository {

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


    fun getLives(pageNo: Int) = liveData(Dispatchers.IO) {
        val list = ArrayList<LiveBean>()
        val result = try {
            val json = HuyaNetWork.getLives(pageNo)
            val arr = JsonParser().parse(json).asJsonObject.get("vList").asJsonArray
            arr.forEach { e ->
                e.asJsonObject.apply {
                    val roomId = get("lProfileRoom").asString
                    val roomUrl = "https://www.huya.com/$roomId"
                    val name = get("sNick").asString
                    val title = get("sIntroduction").asString
                    val gameName = get("sGameFullName").asString
                    val num = get("lTotalCount").asString.run {
                        NumberUtil.div(this, "10000", 1).toString() + "万"
                    }
                    val avatar = get("sAvatar180").asString
                    val coverUrl = get("sScreenshot").asString
                    list.add(
                        LiveBean(
                            roomId,
                            roomUrl,
                            name,
                            title,
                            gameName,
                            num,
                            avatar,
                            coverUrl,
                            null
                        )
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
            .header("User-Agent", Config.UA)
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
