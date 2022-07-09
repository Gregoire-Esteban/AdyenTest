package com.adyen.android.assignment.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.adyen.android.assignment.R
import com.adyen.android.assignment.data.api.EmptyBodyException
import com.adyen.android.assignment.domain.model.AstronomyPicture
import com.adyen.android.assignment.domain.repositories.AstronomyRepository
import com.adyen.android.assignment.ui.ErrorType
import com.adyen.android.assignment.ui.adapter.AdapterItem
import com.adyen.android.assignment.ui.adapter.HeaderItem
import com.squareup.moshi.JsonDataException
import kotlinx.coroutines.launch
import retrofit2.HttpException

class AstronomyListViewModel(private val astronomyRepository: AstronomyRepository) :
    BaseViewModel() {

    private var sortByDate = true

    private val _elements = MutableLiveData<List<AstronomyPicture>>()
    val displayElements : LiveData<List<AdapterItem>> = Transformations.map(_elements) {
        val sortedList = if (sortByDate){
            it.sortedBy { astronomyPicture -> astronomyPicture.date  }
        } else {
            it.sortedBy { astronomyPicture ->  astronomyPicture.title }
        }
        listOf(HeaderItem(R.string.header_latest)).plus(sortedList)
    }

    private val _possibleError = MutableLiveData(ErrorType.NO_ERROR)
    val possibleError : LiveData<ErrorType> = _possibleError

    fun getPictureList() {
        isLoading.value = true
        viewModelScope.launch {
            astronomyRepository.getAstronomyImageList()
                .onFailure {
                    isLoading.postValue(false)
                    if (it is HttpException || it is EmptyBodyException || it is JsonDataException){
                        _possibleError.postValue(ErrorType.API)
                    } else {
                        Log.e(AstronomyListViewModel::class.java.name, "Error when retrieving pictures list", it)
                    }
                }
                .onSuccess {
                    isLoading.postValue(false)
                    _elements.postValue(it)
                }
        }
    }

    fun onErrorButtonClicked(errorType: ErrorType) {
        _possibleError.value = ErrorType.NO_ERROR
        if (errorType == ErrorType.API) {
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