package com.cts.common.code.core

interface BaseView {

    fun onShowLoader(show: Boolean)

    fun onShowError(errorMessage: String?)

    fun onUnAuthorized(unAuth: Boolean, errorMessage: String?)
}