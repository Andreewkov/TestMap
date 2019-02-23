package kov.ru.testmap.ui.presenter

import kov.ru.testmap.model.City
import kov.ru.testmap.model.Weather
import kov.ru.testmap.view.Main

class MainPresenter : Main.Presenter {

    private var view: Main.View? = null
    override var isAttached: Boolean = view != null

    override fun attachView(view: Main.View) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

    override fun search(city: String) {

    }

}

interface SearchCallback {
    fun returnResult(list: ArrayList<City>)
}

interface WeatherCallback {
    fun returnResult(weather: Weather)
}
