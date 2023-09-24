package app.live.droid.logic.network.douyu

import retrofit2.await

object DouyuNetwork {

    private var douyuService = DouyuServiceCreator.create<DouyuService>("https://www.douyu.com")

    suspend fun getLives(page:Int) = douyuService.getLives(page).await()


}