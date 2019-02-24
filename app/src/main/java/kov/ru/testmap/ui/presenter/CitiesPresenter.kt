package kov.ru.testmap.ui.presenter

import kov.ru.testmap.model.City
import kov.ru.testmap.model.db.DBListenerGetAll
import kov.ru.testmap.model.db.DBRepository
import kov.ru.testmap.view.Cities

class CitiesPresenter : Cities.Presenter {
    private var view: Cities.View? = null
    override var isAttached: Boolean = view != null

    override fun attachView(view: Cities.View) {
        this.view = view
        val listener = object : DBListenerGetAll {
            override fun returnCities(cities: ArrayList<City>) {
                view.clearList()
                view.showList(cities)
            }
        }
        DBRepository.getInstance().addListener(listener)
        //Фрагменты подписаны на репозиторий для своевременного обновления списков.
        DBRepository.getInstance().getAll(null)
    }

    override fun detachView() {
        view = null
    }


}