package app.live.droid.logic.network.douyu

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface DouyuService {

    //https://www.douyu.com/gapi/rkc/directory/mixList/0_0/1
    @GET("/gapi/rkc/directory/mixList/0_0/{page}")
    fun getLives(@Path("page") page:Int):Call<String>

}