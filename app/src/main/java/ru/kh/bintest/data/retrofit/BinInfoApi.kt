package ru.kh.bintest.data.retrofit

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import ru.kh.bintest.domain.entity.BinEntity

interface BinInfoApi {
  @GET("/{binNumber}")
  fun getBinInfo(@Path("binNumber") binNumber: String): Single<BinEntity>
}