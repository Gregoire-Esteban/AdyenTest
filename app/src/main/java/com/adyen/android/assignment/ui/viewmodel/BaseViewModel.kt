package com.adyen.android.assignment.ui.viewmodel

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel(), DefaultLifecycleObserver {
    val isLoading = MutableLiveData(false)
}