package kov.ru.testmap.model.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import kov.ru.testmap.model.City

@Database (entities = arrayOf(City::class), version = 1, exportSchema = false)
abstract class AppDB: RoomDatabase() {
    abstract val cityDAO: CityDao
}