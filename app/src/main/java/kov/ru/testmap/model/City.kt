package kov.ru.testmap.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class City(@PrimaryKey val city: String,
                val country: String,
                val latitude: Double,
                val longitude: Double,
                var weather: String,
                var description: String)