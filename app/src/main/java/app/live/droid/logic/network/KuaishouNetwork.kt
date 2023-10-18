package app.live.droid.logic.network

import androidx.lifecycle.liveData
import app.live.droid.extensions.UA_NAME
import app.live.droid.extensions.UA_PC
import app.live.droid.extensions.gson.getBool
import app.live.droid.extensions.gson.getInt
import app.live.droid.extensions.gson.getJsonArray
import app.live.droid.extensions.gson.getJsonObject
import app.live.droid.extensions.gson.getString
import app.live.droid.extensions.parseObject
import app.live.droid.logic.model.LiveBean
import app.live.droid.logic.model.Rate
import app.live.droid.logic.model.StreamBean
import cn.hutool.core.net.URLDecoder
import cn.hutool.core.util.CharsetUtil
import cn.hutool.core.util.ReUtil
import cn.hutool.http.HttpRequest
import kotlinx.coroutines.Dispatchers

object KuaishouNetwork {

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
        val result = try {
            val list = ArrayList<LiveBean>()
            val url = "https://live.kuaishou.com/live_api/hot/list?type=HOT&filterType=0&pageSize=20&cursor=&page=1"
            val json = HttpRequest.get(url).header(UA_NAME, UA_PC).execute().body()
            val hasMore = json.parseObject().getJsonObject("data").getBool("hasMore")
            val arr = json.parseObject().getJsonObject("data").getJsonArray("list")
            arr.forEach { e ->
                e.toString().parseObject().apply {
                    val roomId = getJsonObject("author").getString("id")
                    val roomUrl = "https://live.kuaishou.com/u/$roomId"
                    val name = getJsonObject("author").getString("name")
                    val title = getString("caption").run { if (isNullOrEmpty()) "" else this }
                    val gameName = getJsonObject("gameInfo").getString("name")
                    val num = getString("watchingCount")
                    val avatar = getJsonObject("author").getString("avatar")
                    val coverUrl = getString("poster")
                    list.add(LiveBean(roomId, roomUrl, name, title, gameName, num, avatar, coverUrl, null, hasMore))
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

        val arr = json.parseObject().getJsonObject("liveroom")
            .getJsonArray("playList")[0]
                .parseObject()
            .getJsonObject("liveStream").getJsonArray("playUrls").apply {
                if (size() != 0) {
                    get(0).parseObject()
                        .getJsonObject("adaptationSet").getJsonArray("representation")
                        .forEach { item ->
                            item.toString().parseObject().apply {
                                urls.add(
                                    URLDecoder.decode(
                                        getString("url"),
                                        CharsetUtil.CHARSET_UTF_8
                                    )
                                )
                                val r = getInt("bitrate")
                                rates.add(Rate(getString("name"),r, r==1000))
                            }
                        }

                }
            }
        return StreamBean(urls, rates)
    }
}
