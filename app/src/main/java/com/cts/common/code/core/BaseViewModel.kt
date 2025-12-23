package com.cts.common.code.core

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class BaseViewModel : ViewModel() {

    protected fun <T> executeRequest(
        isShowWaiting: Boolean = true, liveData: MutableLiveData<APIStates<T>>,
        request: suspend () -> Flow<APIStates<T>>
    ) {
        viewModelScope.launch {
            if (isShowWaiting) {
                liveData.postValue(APIStates.waiting())
            }
            withContext(Dispatchers.IO) {
                request().collect { liveData.postValue(it) }
            }
        }
    }
}