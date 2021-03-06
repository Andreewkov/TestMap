package kov.ru.testmap.model.db

import android.arch.persistence.room.*
import kov.ru.testmap.model.City

@Dao
interface CityDao {

    @Query ("SELECT * FROM city")
    fun getAll(): List<City>

    @Query ("SELECT * FROM city WHERE city = :city")
    fun get(city: String): City

    @Insert
    fun insert(city: City)

    @Update
    fun update(city: City)

    @Delete
    fun delete(city: City)

}