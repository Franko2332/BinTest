package ru.kh.bintest.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.kh.bintest.domain.dbentity.RequestedBinEntity


@Database(entities = [RequestedBinEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun requestedBinDao(): RequestedBinDao
}