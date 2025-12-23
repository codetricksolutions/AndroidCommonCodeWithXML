package com.cts.common.code.network

import com.cts.common.code.data.remote.ApiResponse
import com.cts.common.code.data.remote.requests.auth.login.LoginRequest
import com.cts.common.code.data.remote.response.auth.login.LoginResponse

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface APIService {

    @POST("login")
    suspend fun login(
        @Body body: LoginRequest
    ): Response<ApiResponse<LoginResponse>>

}