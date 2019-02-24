package kov.ru.testmap

import android.app.Application
import android.arch.persistence.room.Room
import kov.ru.testmap.api.ApiInterface
import kov.ru.testmap.di.AppComponent
import kov.ru.testmap.di.DaggerAppComponent
import kov.ru.testmap.di.MainModule
import kov.ru.testmap.di.MapsModule
import kov.ru.testmap.model.db.AppDB
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

class App : Application() {

    lateinit var injector: AppComponent
        private set

    companion object {
        lateinit var api: ApiInterface
        val apiKey = "068bd2f40d2217bbb857b5c91374811f"

        private var instance: App? = null
        @JvmStatic
        fun getInstance(): App = instance!!

        lateinit var appDB: AppDB
    }

    override fun onCreate() {
        super.onCreate()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()

        api = retrofit.create(ApiInterface::class.java)

        instance = this
        injector = DaggerAppComponent.builder()
            .mainModule(MainModule())
            .mapsModule(MapsModule())
            .build()

        appDB = Room.databaseBuilder(this, AppDB::class.java, "database").build()
    }
}