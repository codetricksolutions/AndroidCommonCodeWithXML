package com.cts.common.code.core

import com.cts.common.code.data.remote.ApiResponse
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class BaseRepository {

    fun <T> safeApiResponse(data: Response<ApiResponse<T>>): APIStates<T> {
        return try {

            return if (data.code() in 200..299) {
                val envelope = data.body()
                if (envelope != null) {

                    if (envelope.status == true) {
                        APIStates.success(envelope.data)
                    } else {
                        APIStates.error(errorMessage = envelope.message ?: "Something went wrong")
                    }
                } else {
                    APIStates.error(errorMessage = data.message() ?: "Something went wrong")
                }
            } else if (data.code() == 401) {
                APIStates.unAuth(null, "Your session is expired. Please authenticate again")
            } else {
                val envelope = data.errorBody()
                val errorMessage: String = getErrorMessage(envelope)
                APIStates.error(errorMessage = errorMessage)
            }

        } catch (e: UnknownHostException) {
            println("Response isFail: ${data.code()}, ${data.message()}")
            APIStates.error(errorMessage = "Network error. Please check your internet connection.")
        } catch (e: IOException) {
            APIStates.error(errorMessage = "Network error.${e.localizedMessage}")
        } catch (e: HttpException) {
            APIStates.error(errorMessage = "HTTP error: ${e.message()}")
        } catch (e: Exception) {
            APIStates.error(errorMessage = "Unexpected error occurred. Please try again later.")
        } catch (e: ConnectException) {
            APIStates.error(errorMessage = "Unable to connect to the server. Please try again later.")
        } catch (e: SocketTimeoutException) {
            APIStates.error(errorMessage = "Connection timed out. Please try again later.")
        }
    }

    fun onlyMessageSafeApiResponse(data: Response<ApiResponse<Any>>): APIStates<String> {
        return try {

            return if (data.code() in 200..299) {
                val envelope = data.body()
                if (envelope != null) {

                    if (envelope.status == true) {
                        APIStates.success(envelope.message)
                    } else {
                        APIStates.error(errorMessage = envelope.message ?: "Something went wrong")
                    }
                } else {
                    APIStates.error(errorMessage = data.message() ?: "Something went wrong")
                }
            } else if (data.code() == 401) {
                APIStates.unAuth(null, "Your session is expired. Please authenticate again")
            } else {
                val envelope = data.errorBody()
                val errorMessage: String = getErrorMessage(envelope)
                APIStates.error(errorMessage = errorMessage)
            }

        } catch (e: UnknownHostException) {
            println("Response isFail: ${data.code()}, ${data.message()}")
            APIStates.error(errorMessage = "Network error. Please check your internet connection.")
        } catch (e: IOException) {
            APIStates.error(errorMessage = "Network error.${e.localizedMessage}")
        } catch (e: HttpException) {
            APIStates.error(errorMessage = "HTTP error: ${e.message()}")
        } catch (e: Exception) {
            APIStates.error(errorMessage = "Unexpected error occurred. Please try again later.")
        } catch (e: ConnectException) {
            APIStates.error(errorMessage = "Unable to connect to the server. Please try again later.")
        } catch (e: SocketTimeoutException) {
            APIStates.error(errorMessage = "Connection timed out. Please try again later.")
        }
    }

    private fun getErrorMessage(envelope: ResponseBody?): String {
        var errorMessage = "Something went wrong"
        if (envelope != null) {
            val errorBody = JSONObject(envelope.string())
            if (errorBody.has("message")) {
                errorMessage = errorBody.getString("message")
            }
        }
        return errorMessage
    }

}