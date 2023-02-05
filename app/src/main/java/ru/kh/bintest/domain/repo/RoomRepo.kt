package ru.kh.bintest.domain.repo

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.kh.bintest.domain.dbentity.RequestedBinEntity

interface RoomRepo {
    fun add(entity: RequestedBinEntity): Completable
    fun delete(entity: RequestedBinEntity): Completable
    fun getAll(): Single<List<RequestedBinEntity>>
}