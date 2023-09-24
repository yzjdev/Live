package app.live.droid.logic.network.kuaishou

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface KuaishouService {

    //https://live.kuaishou.com/live_api/hot/list?type=HOT&filterType=0&page=2&pageSize=20&cursor=
    //https://live.kuaishou.com/live_api/hot/list?type=HOT&filterType=0&page=1&pageSize=24
    @GET("/live_api/hot/list?type=HOT&filterType=0&pageSize=20&cursor=")
    @Headers("User-Agent:Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.71 Safari/537.36", "Cookie: kuaishou.live.bfb1s=3e261140b0cf7444a0ba411c6f227d88; clientid=3; did=web_6c0bd109712bf01beb46f821df15e2d5; client_key=65890b29; kpn=GAME_ZONE; _did=web_533172690A81DF38; did=web_56d82b579d51c71ecf790d49b95770a19e33")
    fun getLives(@Query("page") page: Int): Call<String>
}