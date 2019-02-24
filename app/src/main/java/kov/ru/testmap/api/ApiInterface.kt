package kov.ru.testmap.api

import org.intellij.lang.annotations.Language
import retrofit2.Call
import retrofit2.http.*
import java.nio.channels.spi.AbstractSelectionKey

interface ApiInterface {

    @GET("weather")
    fun getWeather(@Query("q") location: String,
                   @Query("appid") apiKey: String): Call<String>
}