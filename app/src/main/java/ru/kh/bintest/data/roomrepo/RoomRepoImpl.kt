package ru.kh.bintest.data.roomrepo

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

import ru.kh.bintest.data.room.RequestedBinDao
import ru.kh.bintest.domain.dbentity.RequestedBinEntity
import ru.kh.bintest.domain.repo.RoomRepo
import javax.inject.Inject

class RoomRepoImpl @Inject constructor(private val requestedBinDao: RequestedBinDao) : RoomRepo {
    override fun add(entity: RequestedBinEntity): Completable = requestedBinDao.insert(entity)

    override fun delete(entity: RequestedBinEntity): Completable = requestedBinDao.delete(entity)

    override fun getAll(): Single<List<RequestedBinEntity>> = requestedBinDao.getAll()

}