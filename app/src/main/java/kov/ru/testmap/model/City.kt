package kov.ru.testmap.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class City(val city: String,
                val country: String,
                @PrimaryKey val key: String,
                val latitude: Double,
                val longitude: Double)