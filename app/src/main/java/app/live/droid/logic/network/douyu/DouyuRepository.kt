package app.live.droid.logic.network.douyu

import android.util.Log
import androidx.lifecycle.liveData
import app.live.droid.Config
import app.live.droid.extensions.TAG
import app.live.droid.logic.model.LiveBean
import app.live.droid.logic.model.Rate
import app.live.droid.logic.model.StreamBean
import app.live.droid.utils.ScriptUtil
import cn.hutool.core.text.UnicodeUtil
import cn.hutool.core.util.NumberUtil
import cn.hutool.core.util.ReUtil
import cn.hutool.crypto.digest.MD5
import cn.hutool.http.HttpRequest
import cn.hutool.http.HttpUtil
import cn.hutool.json.JSONObject
import cn.hutool.json.JSONUtil
import com.google.gson.JsonParser
import kotlinx.coroutines.Dispatchers
import java.util.regex.Pattern
import kotlin.properties.Delegates

object DouyuRepository {

    fun getStream(room:String) = liveData(Dispatchers.IO) {
        var stream:StreamBean? = null
        val result = try {
            val d = DouYu(room)
            stream = d.getRealUrl()
            Result.success(stream)
        }catch (e:Exception){
            Result.failure<StreamBean>(e)
        }
        emit(result)
    }


    fun getLives(pageNo: Int) = liveData(Dispatchers.IO) {
        val list = ArrayList<LiveBean>()
        val result = try {
            val json = DouyuNetwork.getLives(pageNo)
            val arr = JsonParser().parse(json).asJsonObject.get("data").asJsonObject.get("rl").asJsonArray
            arr.forEach { e ->
                e.asJsonObject.apply {
                    val roomId = get("url").asString.substring(1)
                    val roomUrl = "https://www.douyu.com/$roomId"
                    val name = get("nn").asString
                    val title = get("rn").asString
                    val gameName = get("c2name").asString
                    val num = get("ol").asString.run {
                        NumberUtil.div(this, "10000", 1).toString() + "万"
                    }

                    val avatar = "https://apic.douyucdn.cn/upload/"+ get("av").asString+"_middle.jpg"
                    val coverUrl = get("rs16").asString
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
            Log.d(TAG, "getLives: ${e.message}")
            Result.failure<List<LiveBean>>(e)
        }
        emit(result)
    }
}

class DouYu(val room: String) {
    lateinit var rid: String
    lateinit var did: String
    var res: String
    var t13 by Delegates.notNull<Long>()
    var t10 by Delegates.notNull<Long>()

    init {
        val did = "10000000000000000000000000001501"
        t10 = System.currentTimeMillis() / 1000 as Int
        t13 = System.currentTimeMillis()
        res = HttpUtil.get("https://m.douyu.com/$room")
        val p = Pattern.compile("rid\":(\\d{1,8}),\"vipId")
        val m = p.matcher(res)
        if (m.find())
            rid = m.group(1)!!
    }

    fun getJs():StreamBean {
        var p = Pattern.compile("(function ub98484234.*)\\s(var.*)")
        var m = p.matcher(res)
        var result = ""
        if (m.find()) result = m.group()

        val funcUb9 = result.replace(Regex("eval.*;\\}"), "strc;}")
        var res = ScriptUtil.eval(funcUb9, "ub98484234")
        p = Pattern.compile("v=(\\d+)")
        m = p.matcher(res)
        var v = ""
        if (m.find()) v = m.group(1)!!

        val rb = MD5.create().digestHex("$rid$did$t10$v")
        var func_sign = res.replace(Regex("return rt;\\}\\);?"), "return rt;}")
        func_sign = func_sign.replace("(function (", "function sign(")
        func_sign = func_sign.replace("CryptoJS.MD5(cb).toString()", "\"$rb\"")
        var params = ScriptUtil.eval(func_sign, "sign", rid, did, t10)
        params += "&ver=22107261&rid=$rid&rate=-1"

        val url = "https://m.douyu.com/api/room/ratestream"
        res = HttpRequest.post(url).header("User-Agent", Config.UA).body(params).execute().body()
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
            return StreamBean(arrayListOf(url),rate,rates)

        }
    }


    fun getRealUrl() :StreamBean{
        getDid()
        val data = getJs()
       return data
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
