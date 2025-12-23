package com.cts.common.code.data.repository.auth.login

import com.cts.common.code.core.APIStates
import com.cts.common.code.data.remote.response.auth.login.LoginResponse
import kotlinx.coroutines.flow.Flow

interface LoginRepository {

    suspend fun login(email: String, password: String): Flow<APIStates<LoginResponse>>
}