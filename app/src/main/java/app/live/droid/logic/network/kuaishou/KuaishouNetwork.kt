package app.live.droid.logic.network.kuaishou

import androidx.lifecycle.liveData
import app.live.droid.UA_NAME
import app.live.droid.UA_PC
import app.live.droid.logic.model.LiveBean
import app.live.droid.logic.model.Rate
import app.live.droid.logic.model.StreamBean
import cn.hutool.core.net.URLDecoder
import cn.hutool.core.util.CharsetUtil
import cn.hutool.core.util.ReUtil
import cn.hutool.http.HttpRequest
import com.alibaba.fastjson2.JSON
import com.alibaba.fastjson2.parseObject
import com.alibaba.fastjson2.toJSONString
import com.google.gson.JsonParser
import kotlinx.coroutines.Dispatchers


object KuaishouRepository {

    fun getStream(room: String) = liveData(Dispatchers.IO) {
        var stream: StreamBean? = null
        val result = try {
            stream = KuaiShou().getRealUrl(room)
            Result.success(stream)
        } catch (e: Exception) {
            Result.failure<StreamBean>(e)
        }
        emit(result)
    }


    fun getLives(page: Int) = liveData(Dispatchers.IO) {
        val list = ArrayList<LiveBean>()
        val result = try {
            val url =
                "https://live.kuaishou.com/live_api/hot/list?type=HOT&filterType=0&pageSize=20&cursor=&page=$page"
            val json = HttpRequest.get(url).header(UA_NAME, UA_PC).execute().body()
            val arr =
                JsonParser().parse(json).asJsonObject.get("data").asJsonObject.get("list").asJsonArray
            arr.forEach { e ->
                e.asJsonObject.apply {
                    val roomId = get("author").asJsonObject.get("id").asString
                    val roomUrl = "https://live.kuaishou.com/u/$roomId"
                    val name = get("author").asJsonObject.get("name").asString
                    val title = get("caption").asString
                    val gameName = get("gameInfo").asJsonObject.get("name").asString
                    val num = get("watchingCount").asString
                    val avatar = get("author").asJsonObject.get("avatar").asString
                    val coverUrl = get("poster").asString
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


class KuaiShou {
    fun getRealUrl(room: String): StreamBean {
        val res = HttpRequest.get("https://live.kuaishou.com/u/$room").header("User-Agent", UA_PC)
            .execute().body()
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
                                urls.add(
                                    URLDecoder.decode(
                                        getString("url"),
                                        CharsetUtil.CHARSET_UTF_8
                                    )
                                )
                                rates.add(Rate(getString("name"), getIntValue("bitrate")))
                            }
                        }

                }
            }
        return StreamBean(urls, 0, rates)
    }
}
