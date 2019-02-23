package kov.ru.testmap.model.repository

import kov.ru.testmap.App
import kov.ru.testmap.model.City
import kov.ru.testmap.ui.presenter.SearchCallback
import kov.ru.testmap.view.Main
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchRepository private constructor(): Main.SearchRepository {

    companion object {
        private var instance: SearchRepository = SearchRepository()
        fun getInstance() = instance
    }

    override fun searchLocation(city: String, callback: SearchCallback) {
        App.api.searchLocation(App.apiKey, city).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                val cities: ArrayList<City> = ArrayList()
                val jsonArray = JSONArray(response.body())
                for (i: Int in 0 until jsonArray.length()) {
                    cities.add(getCity(jsonArray.getJSONObject(i)))
                }
                callback.returnResult(cities)
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                val a = 0
            }
        })
    }

    private fun getCity(jsonObject: JSONObject): City {
        return City(jsonObject.getString("LocalizedName"),
            jsonObject.getJSONObject("Country").getString("LocalizedName"),
            jsonObject.getString("Key"),
            jsonObject.getJSONObject("GeoPosition").getDouble("Latitude"),
            jsonObject.getJSONObject("GeoPosition").getDouble("Longitude"))
    }

}