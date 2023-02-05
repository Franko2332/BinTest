package ru.kh.bintest

import android.app.Application
import android.content.Context
import ru.kh.bintest.data.room.AppDatabase
import ru.kh.bintest.di.DaggerAppComponent
import ru.kh.bintest.di.DaggerModule
import javax.inject.Inject

class App : Application(){
    @Inject lateinit var database: AppDatabase
    val appComponent by lazy {
        DaggerAppComponent.builder().daggerModule(DaggerModule(applicationContext)).build()
    }

    override fun onCreate() {
        super.onCreate()
        appComponent.inject(this)
    }

}

val Context.app: App get() = applicationContext as App