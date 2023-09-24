package app.live.droid.logic.network.kuaishou

import retrofit2.await


object KuaishouNetwork {

    private val service = KuaishouServiceCreator.create<KuaishouService>("https://live.kuaishou.com")

    suspend fun getLives(page: Int) = service.getLives(page).await()

}