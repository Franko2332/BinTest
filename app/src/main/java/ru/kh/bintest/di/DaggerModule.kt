package ru.kh.bintest.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.kh.bintest.data.retrofitrepo.RetrofitRepoImpl
import ru.kh.bintest.data.retrofit.BinInfoApi
import ru.kh.bintest.data.room.AppDatabase
import ru.kh.bintest.data.room.RequestedBinDao
import ru.kh.bintest.data.roomrepo.RoomRepoImpl
import ru.kh.bintest.domain.repo.Repo
import ru.kh.bintest.domain.repo.RoomRepo
import javax.inject.Singleton

@Module
class DaggerModule(private val context: Context) {
    private val baseUrl = "https://lookup.binlist.net"
    private val dataBaseName = "requested_bins"

    @Provides
    @Singleton
    fun getRetrofit(): Retrofit = Retrofit.Builder().baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .client(OkHttpClient.Builder().apply {
            addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }.build())
        .build()

    @Provides
    @Singleton
    fun getBinApi(retrofit: Retrofit): BinInfoApi {
        return retrofit.create(BinInfoApi::class.java)
    }

    @Provides
    @Singleton
    fun getRetrofitRepoImpl(api: BinInfoApi): Repo = RetrofitRepoImpl(api)

    @Provides
    @Singleton
    fun getRequestedBinDao(): RequestedBinDao =
        Room.databaseBuilder(context, AppDatabase::class.java, dataBaseName)
            .allowMainThreadQueries()
            .build()
            .requestedBinDao()

    @Provides
    @Singleton
    fun getRoomRepoImpl(requestedBinDao: RequestedBinDao): RoomRepo =
        RoomRepoImpl(requestedBinDao)


}