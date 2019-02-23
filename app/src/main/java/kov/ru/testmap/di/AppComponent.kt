package kov.ru.testmap.di

import dagger.Component
import kov.ru.testmap.ui.activity.MainActivity
import javax.inject.Singleton

@Component(modules = [MainModule::class])

@Singleton
interface AppComponent {
    fun inject(mainActivity: MainActivity)
}