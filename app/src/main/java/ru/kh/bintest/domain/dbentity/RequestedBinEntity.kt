package ru.kh.bintest.domain.dbentity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = RequestedBinEntity.TABLE_NAME)
data class RequestedBinEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "bin_number") val binNumber: String,
    @ColumnInfo(name = "request_date") val requestDate: String
){
 companion object{
  const val TABLE_NAME = "requested_bins"
 }
}