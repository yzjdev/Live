package app.live.droid

import app.live.droid.extensions.UA_MOBILE
import app.live.droid.extensions.UA_NAME
import app.live.droid.extensions.UA_PC
import app.live.droid.logic.model.Rate
import app.live.droid.logic.model.StreamBean
import app.live.droid.utils.ScriptUtils
import cn.hutool.core.codec.Base64Decoder
import cn.hutool.core.net.URLDecoder
import cn.hutool.core.text.UnicodeUtil
import cn.hutool.core.util.CharsetUtil
import cn.hutool.core.util.ReUtil
import cn.hutool.crypto.digest.MD5
import cn.hutool.http.HttpRequest
import cn.hutool.http.HttpUtil
import cn.hutool.json.JSONObject
import cn.hutool.json.JSONUtil
import com.alibaba.fastjson2.JSON
import com.alibaba.fastjson2.parseObject
import com.alibaba.fastjson2.toJSONString
import java.util.regex.Pattern
import kotlin.properties.Delegates


fun main() {
    var page = 1
    var url =
        "https://live.huya.com/liveHttpUI/getLiveList?iGid=0&iPageSize=120&iPageNo=$page"
    var json = HttpRequest.get(url).header(UA_NAME, UA_PC).execute().body().replace("\n", " ")

    println(json)


    page = 2
    url =
        "https://live.huya.com/liveHttpUI/getLiveList?iGid=0&iPageSize=120&iPageNo=$page"
    json = HttpRequest.get(url).header(UA_NAME, UA_PC).execute().body().replace("\n", " ")
    println(json)

}


class KuaiShou {
    fun getRealUrl(room: String): StreamBean? {
        val res = HttpRequest.get(room).execute().body()
        val json = ReUtil.findAllGroup1("window.__INITIAL_STATE__=(.*?);\\(function", res)[0]

        val urls = arrayListOf<String>()
        val rates = arrayListOf<Rate>()

        val arr = JSON.parseObject(json).getJSONObject("liveroom")
            .getJSONArray("playList")
            .getJSONObject(0)
            .getJSONObject("liveStream").getJSONArray("playUrls").apply {
                if (size != 0) {
                    getJSONObject(0)
                        .getJSONObject("adaptationSet").getJSONArray("representation")
                        .forEach { item ->
                            item.toJSONString().parseObject().apply {
                                urls.add(getString("url"))
                                rates.add(Rate(getString("name"), getIntValue("bitrate")))
                            }
                        }
                    return StreamBean(urls, 0, rates)
                }
            }
        return null
    }
}


class HuYa {
    fun getRealUrl(): StreamBean? {
        val url = "https://m.huya.com/11342412"
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


class DouYu {
    lateinit var rid: String
    lateinit var did: String
    var res: String
    var t13 by Delegates.notNull<Long>()
    var t10 by Delegates.notNull<Long>()

    init {
        val did = "10000000000000000000000000001501"
        t10 = System.currentTimeMillis() / 1000 as Int
        t13 = System.currentTimeMillis()
        res = HttpUtil.get("https://m.douyu.com/252140")
        val p = Pattern.compile("rid\":(\\d{1,8}),\"vipId")
        val m = p.matcher(res)
        if (m.find())
            rid = m.group(1)!!
    }

    fun getJs(): StreamBean {
        var p = Pattern.compile("(function ub98484234.*)\\s(var.*)")
        var m = p.matcher(res)
        var result = ""
        if (m.find()) result = m.group()

        val funcUb9 = result.replace(Regex("eval.*;}"), "strc;}")
        var res = ScriptUtils.eval(funcUb9, "ub98484234")
        p = Pattern.compile("v=(\\d+)")
        m = p.matcher(res)
        var v = ""
        if (m.find()) v = m.group(1)!!

        val rb = MD5.create().digestHex("$rid$did$t10$v")
        var func_sign = res.replace(Regex("return rt;}\\);?"), "return rt;}")
        func_sign = func_sign.replace("(function (", "function sign(")
        func_sign = func_sign.replace("CryptoJS.MD5(cb).toString()", "\"$rb\"")
        var params = ScriptUtils.eval(func_sign, "sign", rid, did, t10)
        params += "&ver=22107261&rid=$rid&rate=-1"

        val url = "https://m.douyu.com/api/room/ratestream"
        res = HttpRequest.post(url).header("User-Agent", UA_MOBILE).body(params).execute().body()
        val dataObj = JSONUtil.parseObj(UnicodeUtil.toString(res)).getJSONObject("data")

        dataObj.apply {
            val url = getStr("url")
            val rate = getInt("rate")
            val rates = ArrayList<Rate>()
            getJSONArray("settings").forEach {
                val o = it as JSONObject
                val rate = Rate(o.getStr("name"), o.getInt("rate"))
                rates.add(rate)
            }
            return StreamBean(arrayListOf(url), rate, rates)

        }
    }


    fun getRealUrl() {
        getDid()
        val data = getJs()
        println(data)
    }

    fun getDid() {
        val did = "10000000000000000000000000001501"
        val url =
            "https://passport.douyu.com/lapi/did/api/get?client_id=25&_=$t13&callback=axiosJsonpCallback1"
        val res = HttpRequest.get(url)
            .header("referer", "https://m.douyu.com")
            .execute().body()
        this.did = JSONUtil.parseObj(ReUtil.findAllGroup1("axiosJsonpCallback1\\((.*)\\)", res)[0])
            .getJSONObject("data").getStr("did")
    }

}
