package app.live.droid.logic.network

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

abstract class ServiceCreator{

    fun <T> create(baseUrl: String, serviceClass: Class<T>): T {

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
       return retrofit.create(serviceClass)
    }

    inline fun <reified T> create(baseUrl: String): T = create(baseUrl, T::class.java)
}