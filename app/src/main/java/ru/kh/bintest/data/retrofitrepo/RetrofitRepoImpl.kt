package ru.kh.bintest.data.retrofitrepo

import io.reactivex.rxjava3.core.Single
import ru.kh.bintest.data.retrofit.BinInfoApi
import ru.kh.bintest.domain.entity.BinEntity
import ru.kh.bintest.domain.repo.Repo
import javax.inject.Inject

class RetrofitRepoImpl @Inject constructor(private val binListApi: BinInfoApi): Repo {

    override fun getBinInfo(binNumber: String): Single<BinEntity> = binListApi.getBinInfo(binNumber)

}