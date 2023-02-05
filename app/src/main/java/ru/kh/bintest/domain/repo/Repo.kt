package ru.kh.bintest.domain.repo

import io.reactivex.rxjava3.core.Single
import ru.kh.bintest.domain.entity.BinEntity

interface Repo {
    fun getBinInfo(binNumber: String): Single<BinEntity>
}