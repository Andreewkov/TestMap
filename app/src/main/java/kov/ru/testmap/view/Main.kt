package kov.ru.testmap.view

import kov.ru.testmap.model.City
import kov.ru.testmap.model.db.*
import kov.ru.testmap.ui.presenter.WeatherCallback

interface Main {

    interface View {
        fun load(location: String)
        fun showToast(text: String)
    }

    interface Presenter {
        var isAttached: Boolean
        fun attachView(view: Main.View)
        fun detachView()
        fun addLocation(location: String)
    }

    interface WeatherRepository {
        fun getWeather(location: String, callback: WeatherCallback)
    }

    interface DBRepository {
        fun add(city: City, listener: DBListenerAdd?)
        fun get(location: String, listener: DBListenerGet?)
        fun getAll(listener: DBListenerGetAll?)
        fun delete(city: City, listener: DBListenerDelete?)
        fun addListener(listener: DBListener)
    }


}