package kov.ru.testmap.ui.presenter

import kov.ru.testmap.model.City
import kov.ru.testmap.model.db.DBListenerGetAll
import kov.ru.testmap.model.db.DBRepository
import kov.ru.testmap.model.repository.WeatherRepository
import kov.ru.testmap.view.Maps

class MapPresenter : Maps.Presenter {

    private var view: Maps.View? = null
    override var isAttached: Boolean = view != null

    override fun attachView(view: Maps.View) {
        this.view = view
        val listener = object : DBListenerGetAll {
            override fun returnCities(cities: ArrayList<City>) {
                this@MapPresenter.view?.clearMarkers()
                this@MapPresenter.view?.showMarkers(cities)
            }
        }
        DBRepository.getInstance().addListener(listener)
        //Фрагменты подписаны на репозиторий для своевременного обновления списков.
        DBRepository.getInstance().getAll(null)
    }

    override fun detachView() {
        view = null
    }

    override fun markerClicked(location: String) {
        //Происходит подгрузка погоды для текущего города
        WeatherRepository.getInstance().getWeather(location, object : WeatherCallback {
            override fun error() {
                view?.showToast("Произошла ошибка при обработке ответа")
            }

            override fun returnResult(city: City) {
                view?.showWeather(city)
            }
        })
    }
}

interface WeatherCallback {
    fun returnResult(city: City)
    fun error();
}