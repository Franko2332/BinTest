package ru.kh.bintest.ui.requestbinhistory

import android.os.Bundle
import androidx.lifecycle.*
import androidx.savedstate.SavedStateRegistryOwner
import io.reactivex.rxjava3.kotlin.subscribeBy
import ru.kh.bintest.domain.appstate.BinHistoryAppState
import ru.kh.bintest.domain.appstate.BinInfoAppState
import ru.kh.bintest.domain.repo.Repo
import ru.kh.bintest.domain.repo.RoomRepo
import ru.kh.bintest.ui.bininfosearch.BinInfoViewModel

class BinsHistoryViewModel(private val repo: RoomRepo) : ViewModel() {
    private val liveData = MutableLiveData<BinHistoryAppState>()
    val _liveData: LiveData<BinHistoryAppState> get() = liveData

    fun getData() {
        liveData.postValue(BinHistoryAppState.Loading)
        repo.getAll().subscribeBy(
            onError = {liveData.postValue(BinHistoryAppState.Error(it))},
            onSuccess = {liveData.postValue(BinHistoryAppState.Success(it))})

    }

    fun deleteHistory(){
        liveData.postValue(BinHistoryAppState.Loading)
        repo.deleteAll().subscribeBy(
            onError = {liveData.postValue(BinHistoryAppState.Error(it))},
            onComplete = {liveData.postValue(BinHistoryAppState.DeleteAll)})
    }

    companion object {
        fun provideFactory(
            roomRepo: RoomRepo, owner: SavedStateRegistryOwner,
            defaultArgs: Bundle? = null
        ): AbstractSavedStateViewModelFactory =
            object : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel?> create(
                    key: String,
                    modelClass: Class<T>,
                    handle: SavedStateHandle
                ): T {
                    return BinsHistoryViewModel(roomRepo) as T
                }
            }
    }

}