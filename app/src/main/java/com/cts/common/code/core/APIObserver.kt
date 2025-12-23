package com.cts.common.code.core

import androidx.lifecycle.Observer

class APIObserver<T>(
    private val view: BaseView,
    private val onSuccess: (data: T?) -> Unit,
    private val onWaiting: (isWaiting: Boolean) -> Unit = DEFAULT_WAITING_ACTION,
    private val onUnAuth: (isUnAuth: Boolean) -> Unit = DEFAULT_UN_AUTH_ACTION,
    private val onError: (errorMessage: String) -> Unit = DEFAULT_ERROR_ACTION,
) : Observer<APIStates<T>> {

    companion object {
        private val DEFAULT_ERROR_ACTION: (errorMessage: String) -> Unit = {}
        private val DEFAULT_WAITING_ACTION: (isWaiting: Boolean) -> Unit = {}
        private val DEFAULT_UN_AUTH_ACTION: (isUnAuth: Boolean) -> Unit = {}
    }


    private fun onWaitingActionCheck(
        onWaiting: (isWaiting: Boolean) -> Unit = DEFAULT_WAITING_ACTION,
        isWaiting: Boolean
    ) {
        if (onWaiting === DEFAULT_WAITING_ACTION) {
            view.onShowLoader(isWaiting)
        } else {
            onWaiting(isWaiting)
        }
    }

    private fun onUnAuthActionCheck(
        onAuth: (isUnAuth: Boolean) -> Unit = DEFAULT_UN_AUTH_ACTION, errorMessage: String?
    ) {
        if (onAuth === DEFAULT_UN_AUTH_ACTION) {
            view.onUnAuthorized(true, errorMessage)
        } else {
            onAuth(true)
        }
    }

    private fun onErrorActionCheck(
        onError: (errorMessage: String) -> Unit = DEFAULT_ERROR_ACTION,
        errorMessage: String?
    ) {
        if (onError === DEFAULT_ERROR_ACTION) {
            view.onShowError(errorMessage)
        } else {
            if (errorMessage != null) {
                onError(errorMessage)
            }
        }
    }

    override fun onChanged(value: APIStates<T>) {
        onWaitingActionCheck(onWaiting, value.states == States.WAITING)
        if (value.states == States.ERROR) {
            onErrorActionCheck(onError, value.error)
            if (value.error?.contains("Please authenticate", true) == true) {

            } else {

            }

        } else if (value.states == States.UnAuth) {
            onUnAuthActionCheck(onUnAuth, value.error)
        } else {
            onSuccess(value.data)
        }
    }
}