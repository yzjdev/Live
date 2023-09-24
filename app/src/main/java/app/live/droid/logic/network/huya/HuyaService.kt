package app.live.droid.logic.network.huya

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface HuyaService {

    //https://live.huya.com/liveHttpUI/getLiveList?iGid=0&iPageSize=120
    @GET("/liveHttpUI/getLiveList?iGid=0&iPageSize=120")
    fun getLives(@Query("pageNo") pageNo: Int): Call<String>

}
