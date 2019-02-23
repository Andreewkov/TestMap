package kov.ru.testmap.view

import kov.ru.testmap.model.City
import kov.ru.testmap.model.db.DBListener
import kov.ru.testmap.ui.presenter.SearchCallback
import kov.ru.testmap.ui.presenter.WeatherCallback

interface Main {

    interface View {

    }

    interface Presenter {
        var isAttached: Boolean
        fun attachView(view: Main.View)
        fun detachView()
        fun search(city: String)
    }

    interface WeatherRepository {
        fun getWeather(key: String, callback: WeatherCallback)
    }

    interface SearchRepository {
        fun searchLocation(city: String, callback: SearchCallback)
    }

    interface DBRepository {
        fun add(city: City)
        fun get(key: Long)
        fun getAll()
        fun delete(city: City)
        fun addListener(listener: DBListener)
    }
}