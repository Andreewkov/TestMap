package kov.ru.testmap

import android.app.Application
import android.arch.persistence.room.Room
import kov.ru.testmap.api.ApiInterface
import kov.ru.testmap.di.AppComponent
import kov.ru.testmap.di.DaggerAppComponent
import kov.ru.testmap.di.MainModule
import kov.ru.testmap.model.db.AppDB
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

class App : Application() {

    lateinit var injector: AppComponent
        private set

    companion object {
        lateinit var api: ApiInterface
        val apiKey = "J6vF5cJb69S9hq9Fg3l1P2JMo048VB0k"

        private var instance: App? = null
        @JvmStatic
        fun getInstance(): App = instance!!

        lateinit var appDB: AppDB
    }

    override fun onCreate() {
        super.onCreate()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("http://dataservice.accuweather.com")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()

        api = retrofit.create(ApiInterface::class.java)

        instance = this
        injector = DaggerAppComponent.builder()
            .mainModule(MainModule())
            .build()

        appDB = Room.databaseBuilder(this, AppDB::class.java, "database").build()
    }
}