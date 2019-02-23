package kov.ru.testmap.api

import org.intellij.lang.annotations.Language
import retrofit2.Call
import retrofit2.http.*
import java.nio.channels.spi.AbstractSelectionKey

interface ApiInterface {

    @GET("/locations/v1/cities/search")
    fun searchLocation(@Query("apikey") apiKey: String,
                       @Query("q") query: String): Call<String>

    @GET("/currentconditions/v1/{locationKey}")
    fun getWeather(@Path("locationKey") locationKey: String,
                   @Query("apikey") apiKey: String): Call<String>
}