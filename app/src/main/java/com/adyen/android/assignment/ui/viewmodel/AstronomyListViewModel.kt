package com.adyen.android.assignment.ui.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.adyen.android.assignment.R
import com.adyen.android.assignment.data.api.EmptyBodyException
import com.adyen.android.assignment.domain.model.AstronomyPicture
import com.adyen.android.assignment.domain.repositories.AstronomyRepository
import com.adyen.android.assignment.ui.ConnectionStatusProvider
import com.adyen.android.assignment.ui.ErrorType
import com.adyen.android.assignment.ui.adapter.AdapterItem
import com.adyen.android.assignment.ui.adapter.HeaderItem
import com.squareup.moshi.JsonDataException
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.UnknownHostException

class AstronomyListViewModel(
    private val astronomyRepository: AstronomyRepository,
    private val connectionStatusProvider: ConnectionStatusProvider
) :
    BaseViewModel() {

    private var sortByDate = true

    private val _elements = MutableLiveData<List<AstronomyPicture>>()
    val displayElements: LiveData<List<AdapterItem>> = Transformations.map(_elements) {
        val baseList = listOf(HeaderItem(R.string.header_latest))
        if (it == null) return@map baseList
        val sortedList = if (sortByDate) {
            it.sortedBy { astronomyPicture -> astronomyPicture.date }
        } else {
            it.sortedBy { astronomyPicture -> astronomyPicture.title }
        }
        baseList.plus(sortedList)
    }

    private val _possibleError = MutableLiveData(ErrorType.NO_ERROR)
    val possibleError: LiveData<ErrorType> = _possibleError

    fun getPictureList() {
        isLoading.postValue(true)
        viewModelScope.launch {
            astronomyRepository.getAstronomyImageList()
                .onFailure {
                    isLoading.postValue(false)
                    when (it) {
                        is HttpException, is EmptyBodyException, is JsonDataException -> _possibleError.postValue(
                            ErrorType.API
                        )
                        is UnknownHostException -> _possibleError.postValue(ErrorType.NETWORK)
                        else -> {
                            Log.e(
                                AstronomyListViewModel::class.java.name,
                                "Error when retrieving pictures list",
                                it
                            )
                        }
                    }
                }
                .onSuccess {
                    isLoading.postValue(false)
                    _elements.postValue(it)
                }
        }
    }

    override fun onResume(owner: LifecycleOwner) {
        connectionStatusProvider.registerAdditionalCallback({
            if (_elements.value.isNullOrEmpty()) {
                getPictureList()
            }
            _possibleError.postValue(ErrorType.NO_ERROR)
        }, {
            _possibleError.postValue(ErrorType.NETWORK)
        })
    }

    fun onErrorButtonClicked(errorType: ErrorType) {
        if (errorType == ErrorType.API) {
            _possibleError.value = ErrorType.NO_ERROR
            getPictureList()
        }
    }

    fun applySorting(isSortingByDate: Boolean) {
        if (isSortingByDate != sortByDate) {
            sortByDate = isSortingByDate
            _elements.value = _elements.value
        }
    }
}