package kov.ru.testmap.model.db

import kov.ru.testmap.App
import kov.ru.testmap.model.City
import kov.ru.testmap.view.Main

class DBRepository private constructor() : Main.DBRepository {

    val cityDAO: CityDao = App.appDB.cityDAO
    var arrayListeners: ArrayList<DBListener> = ArrayList()

    companion object {
        private var instance: DBRepository = DBRepository()
        fun getInstance() = instance
    }

    override fun addListener(listener: DBListener) {
        arrayListeners.add(listener)
    }

    override fun add(city: City) {
        Thread {
            cityDAO.insert(city)
            for (listener: DBListener in arrayListeners)
                listener.added(city)
        }.start()
    }

    override fun get(key: Long) {
        Thread {
            val city: City = cityDAO.get(key)
            for (listener: DBListener in arrayListeners)
                listener.returnOne(city)
        }.start()
    }

    override fun getAll() {
        Thread {
            var cities: ArrayList<City> = ArrayList()
            cities = cityDAO.getAll() as ArrayList<City>
            for (listener: DBListener in arrayListeners)
                listener.returnAll(cities)
        }.start()
    }

    override fun delete(city: City) {
        Thread {
            cityDAO.delete(city)
            for (listener: DBListener in arrayListeners)
                listener.deleted(city)
        }.start()
    }
}

interface DBListener {
    fun added(city: City)
    fun returnAll(cities: ArrayList<City>)
    fun returnOne(city: City)
    fun deleted(city: City)
}