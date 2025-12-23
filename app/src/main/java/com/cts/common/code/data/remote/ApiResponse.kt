package com.cts.common.code.data.remote

data class ApiResponse<T>(
    val status: Boolean? = null,
    val message: String? = null,
    val data: T? = null
)