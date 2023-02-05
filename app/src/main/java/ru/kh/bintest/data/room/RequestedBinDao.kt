package ru.kh.bintest.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.kh.bintest.domain.dbentity.RequestedBinEntity

@Dao
interface RequestedBinDao {
    @Query("SELECT * FROM "+RequestedBinEntity.TABLE_NAME)
    fun getAll(): Single<List<RequestedBinEntity>>

    @Insert
    fun insert(entity: RequestedBinEntity): Completable

    @Delete
    fun delete(entity: RequestedBinEntity): Completable
}