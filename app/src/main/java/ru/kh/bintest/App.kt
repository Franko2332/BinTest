package ru.kh.bintest

import android.app.Application
import android.content.Context
import ru.kh.bintest.di.DaggerAppComponent
import ru.kh.bintest.di.DaggerModule

class App : Application() {
    val appComponent by lazy {
        DaggerAppComponent.builder().daggerModule(DaggerModule(applicationContext)).build()
    }
}

val Context.app: App get() = applicationContext as App