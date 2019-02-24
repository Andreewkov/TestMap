package kov.ru.testmap.di

import dagger.Component
import kov.ru.testmap.ui.view.activity.MainActivity
import kov.ru.testmap.ui.view.fragment.CitiesFragment
import kov.ru.testmap.ui.view.fragment.MapFragment
import javax.inject.Singleton

@Component(modules = [MainModule::class, MapsModule::class, CitiesModule::class])

@Singleton
interface AppComponent {
    fun injectMainActivity(mainActivity: MainActivity)
    fun injectMapFragment(mapFragment: MapFragment)
    fun injectCitiesPFragment(citiesFragment: CitiesFragment)
}