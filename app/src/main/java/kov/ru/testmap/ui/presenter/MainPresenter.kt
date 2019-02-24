package kov.ru.testmap.ui.presenter

import kov.ru.testmap.model.City
import kov.ru.testmap.model.db.DBListenerAdd
import kov.ru.testmap.model.db.DBListenerGetAll
import kov.ru.testmap.model.db.DBRepository
import kov.ru.testmap.model.repository.WeatherRepository
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

    override fun addLocation(location: String) {
        //Добавления города в список и на карту
        WeatherRepository.getInstance().getWeather(location, object : WeatherCallback {
            override fun error() {
                view?.showToast("Произошла ошибка при обработке ответа")
            }

            override fun returnResult(city: City) {
                city.weather = ""
                city.description = ""
                DBRepository.getInstance().add(city, object : DBListenerAdd {
                    override fun cityAdded(city: City) {
                        //Фрагменты подписаны на репозиторий для своевременного обновления списков.
                        DBRepository.getInstance().getAll(null)
                    }
                })
            }
        })
    }
}

