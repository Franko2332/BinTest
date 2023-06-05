package ru.kh.bintest.ui.bininfosearch

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.*
import androidx.savedstate.SavedStateRegistryOwner
import io.reactivex.rxjava3.kotlin.subscribeBy
import ru.kh.bintest.domain.appstate.BinInfoAppState
import ru.kh.bintest.domain.dbentity.RequestedBinEntity
import ru.kh.bintest.domain.repo.Repo
import ru.kh.bintest.domain.repo.RoomRepo
import java.text.SimpleDateFormat
import java.util.*


class BinInfoViewModel(private val repo: Repo, private val roomRepo: RoomRepo) : ViewModel() {
    private val binInfoAppStateLiveData = MutableLiveData<BinInfoAppState>()
    val _binInfoAppStateLiveData: LiveData<BinInfoAppState> get() = binInfoAppStateLiveData
    private var editTextStr = ""


    fun getData(binNumber: String) {
        if (editTextStr.length < BIN_MIN_LENGTH) binInfoAppStateLiveData.postValue(BinInfoAppState.BinLenghtInvalid)
        else {
            binInfoAppStateLiveData.postValue(BinInfoAppState.Loading)
            roomRepo.add(getRequestedBinEntity(binNumber)).subscribeBy(
                onError = { Log.e("ROOM_ADD_ERROR", it.message.toString()) },
                onComplete = { Log.e("ROOM_ADD", "COMPLETE") })
            repo.getBinInfo(binNumber).subscribeBy(
                onError = {
                    binInfoAppStateLiveData.postValue(BinInfoAppState.Error(it))
                },
                onSuccess = {
                    binInfoAppStateLiveData.postValue(BinInfoAppState.Success(it))
                }
            )
        }
    }

    private fun getRequestedBinEntity(bin: String): RequestedBinEntity {
        val time = Calendar.getInstance().time
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale("ru"))
        val entity = RequestedBinEntity(0, bin, formatter.format(time).toString())
        Log.e("BIN ENTITY", entity.toString())
        return entity
    }

    fun onTextChanged(
        s: CharSequence, start: Int, before: Int,
        count: Int
    ) {
        editTextStr = s.toString()
    }

    companion object {
        private const val BIN_MIN_LENGTH = 6


    }
}

object BinInfoViewModelFactory {
    fun provideFactory(
        repo: Repo, roomRepo: RoomRepo, owner: SavedStateRegistryOwner,
        defaultArgs: Bundle? = null
    ): AbstractSavedStateViewModelFactory =
        object : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(
                key: String,
                modelClass: Class<T>,
                handle: SavedStateHandle
            ): T {
                return BinInfoViewModel(repo, roomRepo) as T
            }
        }
}



