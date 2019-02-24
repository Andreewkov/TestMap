package kov.ru.testmap.model.db

import android.database.sqlite.SQLiteConstraintException
import kov.ru.testmap.App
import kov.ru.testmap.model.City
import kov.ru.testmap.view.Main

class DBRepository private constructor() : Main.DBRepository {

    private val cityDAO: CityDao = App.appDB.cityDAO
    //подписка на изменения в репозитории
    private val listeners: ArrayList<DBListener> = ArrayList()

    companion object {
        private var instance: DBRepository = DBRepository()
        fun getInstance() = instance
    }

    override fun addListener(listener: DBListener) {
        listeners.add(listener)
    }

    override fun add(city: City, listener: DBListenerAdd?) {
        Thread {
            try {
                cityDAO.insert(city)
                listener?.cityAdded(city)
                for (listener: DBListener in listeners)
                    if (listener is DBListenerAdd)
                        listener.cityAdded(city)
            } catch (e: SQLiteConstraintException) {

            }
        }.start()
    }

    override fun get(location: String, listener: DBListenerGet?) {
        Thread {
            val city: City = cityDAO.get(location)
            listener?.returnCity(city)
            for (listener: DBListener in listeners)
                if (listener is DBListenerGet)
                    listener.returnCity(city)
        }.start()
    }

    override fun getAll(listener: DBListenerGetAll?) {
        Thread {
            var cities: ArrayList<City> = ArrayList()
            cities = cityDAO.getAll() as ArrayList<City>
            listener?.returnCities(cities)
            for (listener: DBListener in listeners)
                if (listener is DBListenerGetAll)
                    listener.returnCities(cities)
        }.start()
    }

    override fun delete(city: City, listener: DBListenerDelete?) {
        Thread {
            cityDAO.delete(city)
            listener?.cityDeleted(city)
            for (listener: DBListener in listeners)
                if (listener is DBListenerDelete)
                    listener.cityDeleted(city)
        }.start()
    }
}

interface DBListener {}

interface DBListenerGet : DBListener {
    fun returnCity(city: City)
}

interface DBListenerGetAll : DBListener {
    fun returnCities(cities: ArrayList<City>)
}

interface DBListenerAdd : DBListener {
    fun cityAdded(city: City)
}

interface DBListenerDelete : DBListener {
    fun cityDeleted(city: City)
}