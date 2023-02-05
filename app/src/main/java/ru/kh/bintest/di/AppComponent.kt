package ru.kh.bintest.di

import dagger.Component
import ru.kh.bintest.App
import ru.kh.bintest.ui.MainActivity
import ru.kh.bintest.ui.bininfosearch.BinInfoSearchFragment
import ru.kh.bintest.ui.requestbinhistory.RequestBinHistoryFragment
import javax.inject.Singleton

@Component(modules = [DaggerModule::class])
@Singleton
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(binInfoSearchFragment: BinInfoSearchFragment)
    fun inject(app: App)
    fun inject (historyFragment: RequestBinHistoryFragment)
    }