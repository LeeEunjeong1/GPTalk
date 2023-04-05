package com.example.data.utils

import android.util.Log
import com.example.data.model.ResponseResult
import com.example.domain.utils.ErrorType
import com.example.domain.utils.RemoteErrorEmitter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

abstract class BaseRepository {

    companion object {
        private const val MESSAGE_KEY = "message"
        private const val ERROR_KEY = "error"
    }

    suspend inline fun safeApiCallData(emitter: RemoteErrorEmitter, crossinline callFunction: suspend () -> ResponseResult): ResponseResult? {
        return try{
            val myObject = withContext(Dispatchers.IO){ callFunction.invoke() }
            com.example.domain.utils.Util.logMessage("myObject $myObject")
            myObject
        }catch (e: Exception){
            withContext(Dispatchers.Main){
                e.printStackTrace()
                Log.e("BaseRemoteRepo", "Call error: ${e.localizedMessage}", e.cause)
                when(e){
                    is HttpException -> {
                        val body = e.response()?.errorBody()
                        emitter.onError(getErrorMessage(body))
                    }
                    is SocketTimeoutException -> emitter.onError(ErrorType.TIMEOUT)
                    is IOException -> emitter.onError(ErrorType.NETWORK)
                    else -> emitter.onError(ErrorType.UNKNOWN)
                }
            }
            null
        }
    }
    fun getErrorMessage(responseBody: ResponseBody?): String {
        return try {
            val jsonObject = JSONObject(responseBody!!.string())
            when {
                jsonObject.has(MESSAGE_KEY) -> jsonObject.getString(MESSAGE_KEY)
                jsonObject.has(ERROR_KEY) -> jsonObject.getString(ERROR_KEY)
                else -> "Something wrong happened"
            }
        } catch (e: Exception) {
            "Something wrong happened"
        }
    }
}