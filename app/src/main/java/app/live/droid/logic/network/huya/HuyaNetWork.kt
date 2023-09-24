package app.live.droid.logic.network.huya

import retrofit2.await


object HuyaNetWork{
     var huyaService = HuyaServiceCreator.create<HuyaService>("https://live.huya.com")

    suspend fun getLives(pageNo: Int) = huyaService.getLives(pageNo).await()

}