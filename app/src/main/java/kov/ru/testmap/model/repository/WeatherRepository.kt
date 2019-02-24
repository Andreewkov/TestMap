package kov.ru.testmap.model.repository

import kov.ru.testmap.App
import kov.ru.testmap.model.City
import kov.ru.testmap.ui.presenter.WeatherCallback
import kov.ru.testmap.view.Main
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherRepository private constructor() : Main.WeatherRepository {

    companion object {
        private var instance: WeatherRepository = WeatherRepository()
        fun getInstance() = instance
    }

    override fun getWeather(location: String, callback: WeatherCallback) {
        App.api.getWeather(location, App.apiKey).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.code() == 200) {
                    var jsonObject = JSONObject(response.body())
                    val city = City(
                        jsonObject.getString("name"),
                        jsonObject.getJSONObject("sys").getString("country"),
                        jsonObject.getJSONObject("coord").getDouble("lat"),
                        jsonObject.getJSONObject("coord").getDouble("lon"),
                        jsonObject.getJSONArray("weather").getJSONObject(0).getString("main"),
                        jsonObject.getJSONArray("weather").getJSONObject(0).getString("description")
                    )
                    callback.returnResult(city)
                } else {
                    callback.error()
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                callback.error()
            }
        })
    }

}