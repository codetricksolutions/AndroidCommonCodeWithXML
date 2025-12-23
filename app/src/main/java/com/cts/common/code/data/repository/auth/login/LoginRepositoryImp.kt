package com.cts.common.code.data.repository.auth.login

import com.cts.common.code.core.APIStates
import com.cts.common.code.core.BaseRepository
import com.cts.common.code.data.remote.requests.auth.login.LoginRequest
import com.cts.common.code.data.remote.response.auth.login.LoginResponse
import com.cts.common.code.network.APIService
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@ViewModelScoped
class LoginRepositoryImp @Inject constructor(
    private val apiService: APIService
) : BaseRepository(), LoginRepository {

    override suspend fun login(email: String, password: String): Flow<APIStates<LoginResponse>> {
        return flow {
            emit(
                safeApiResponse(apiService.login(LoginRequest(email = email, password = password)))
            )
        }
    }
}