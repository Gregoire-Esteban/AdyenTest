package com.adyen.android.assignment.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.adyen.android.assignment.data.api.EmptyBodyException
import com.adyen.android.assignment.domain.repositories.AstronomyRepository
import com.adyen.android.assignment.ui.ErrorType
import com.adyen.android.assignment.ui.adapter.AdapterItem
import com.squareup.moshi.JsonDataException
import kotlinx.coroutines.launch
import retrofit2.HttpException

class AstronomyListViewModel(private val astronomyRepository: AstronomyRepository) :
    BaseViewModel() {

    private var sortByDate = true

    private val _elements = MutableLiveData<List<AdapterItem>>()
    val elements : LiveData<List<AdapterItem>> = _elements

    private val _possibleError = MutableLiveData(ErrorType.NO_ERROR)
    val possibleError : LiveData<ErrorType> = _possibleError

    fun getPictureList(sortByDate : Boolean = this.sortByDate) {
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
                    _possibleError.postValue(ErrorType.API)
//                    isLoading.postValue(false)
//                    val sortedList = it.sortedBy { astronomyPicture ->  astronomyPicture.date }
//                    _elements.postValue(listOf(HeaderItem(R.string.header_latest)).plus(sortedList))
                }
        }
    }

    fun onErrorButtonClicked(errorType: ErrorType) {
        _possibleError.value = ErrorType.NO_ERROR
        if (errorType == ErrorType.API) {
            getPictureList(sortByDate)
        }
    }
}