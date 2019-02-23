package kov.ru.testmap.model.repository

import kov.ru.testmap.App
import kov.ru.testmap.model.Weather
import kov.ru.testmap.ui.presenter.WeatherCallback
import kov.ru.testmap.view.Main
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherRepository private constructor(): Main.WeatherRepository {

    companion object {
        private var instance: WeatherRepository = WeatherRepository()
        fun getInstance() = instance
    }

    override fun getWeather(key: String, callback: WeatherCallback) {
        App.api.getWeather(key, App.apiKey).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                var jsonObject = JSONArray(response.body()).getJSONObject(0)
                callback.returnResult(Weather(jsonObject.getString("WeatherText"),
                        jsonObject.getJSONObject("Temperature").getJSONObject("Metric").getDouble("Value")))
            }

            override fun onFailure(call: Call<String>, t: Throwable) {

            }
        })
    }

}