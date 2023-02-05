package ru.kh.bintest.domain.appstate

import ru.kh.bintest.domain.dbentity.RequestedBinEntity

sealed class BinHistoryAppState{
    object Loading: BinHistoryAppState()
    object DeleteAll: BinHistoryAppState()
    data class Success(val data: List<RequestedBinEntity>): BinHistoryAppState()
    data class Error(val error: Throwable): BinHistoryAppState()
}
