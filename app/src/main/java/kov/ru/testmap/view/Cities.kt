package kov.ru.testmap.view

import kov.ru.testmap.model.City

interface Cities {
    interface View {
        fun clearList()
        fun showList(cities: ArrayList<City>)
        fun showToast(text: String)
    }

    interface Presenter {
        var isAttached: Boolean
        fun attachView(view: Cities.View)
        fun detachView()
    }
}