package com.cts.common.code.core

data class APIStates<out T>(val states: States, val data: T?, val error: String?) {
    companion object {
        fun <T> waiting(data: T? = null): APIStates<T> {
            return APIStates(States.WAITING, data, null)
        }

        fun <T> unAuth(data: T? = null, errorMessage: String? = null): APIStates<T> {
            return APIStates(States.UnAuth, data, null)
        }

        fun <T> success(data: T? = null): APIStates<T> {
            return APIStates(States.SUCCESS, data, null)
        }

        fun <T> error(data: T? = null, errorMessage: String? = null): APIStates<T> {
            return APIStates(States.ERROR, data, errorMessage)
        }
    }
}
