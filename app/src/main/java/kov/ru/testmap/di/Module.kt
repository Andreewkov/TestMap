package kov.ru.testmap.di

import dagger.Module
import dagger.Provides
import kov.ru.testmap.ui.presenter.CitiesPresenter
import kov.ru.testmap.ui.presenter.MainPresenter
import kov.ru.testmap.ui.presenter.MapPresenter
import javax.inject.Singleton

@Module
class MainModule {
    @Provides
    @Singleton
    fun providesPresenter(): MainPresenter = MainPresenter()
}

@Module
class MapsModule {
    @Provides
    @Singleton
    fun providesPresenter(): MapPresenter = MapPresenter()
}

@Module
class CitiesModule {
    @Provides
    @Singleton
    fun providesPresenter(): CitiesPresenter = CitiesPresenter()
}