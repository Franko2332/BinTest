package ru.kh.bintest.di

import dagger.Component
import ru.kh.bintest.ui.MainActivity
import ru.kh.bintest.ui.bininfosearch.BinInfoSearchFragment
import javax.inject.Singleton

@Component(modules = [DaggerModule::class])
@Singleton
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(binInfoSearchFragment: BinInfoSearchFragment)
    }