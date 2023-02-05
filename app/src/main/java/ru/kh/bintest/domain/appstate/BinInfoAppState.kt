package ru.kh.bintest.domain.appstate

import ru.kh.bintest.domain.entity.BinEntity

sealed class BinInfoAppState{
    object Loading: BinInfoAppState()
    data class Success(val binEntity: BinEntity): BinInfoAppState()
    object BinLenghtInvalid: BinInfoAppState()
    data class Error(val error: Throwable): BinInfoAppState()
}
