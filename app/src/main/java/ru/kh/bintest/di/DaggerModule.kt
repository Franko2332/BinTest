package ru.kh.bintest.di

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DaggerModule {
    private val baseUrl = "https://lookup.binlist.net"

    @Provides
    @Singleton
    fun getRetrofit(): Retrofit
}