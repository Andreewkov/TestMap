package kov.ru.testmap.di

import dagger.Module
import dagger.Provides
import kov.ru.testmap.ui.presenter.MainPresenter
import javax.inject.Singleton

@Module
class MainModule {
    @Provides
    @Singleton
    fun providesPresenter(): MainPresenter = MainPresenter()
}