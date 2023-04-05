package com.example.data.api

import android.service.autofill.UserData
import com.example.data.model.ResponseResult
import com.example.data.model.GetAnswerRequest
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST


interface ApiInterface {
    @POST("v1/completions")
    suspend fun getAnswer(
        @Header("Authorization") key: String,
        @Body request:GetAnswerRequest
//        @Body model: String,
//        @Body prompt: String,
//        @Body temperature: Double,
//        @Body max_tokens: Int,
//        @Body top_p: Int,
//        @Body frequency_penalty: Double,
//        @Body presence_penalty: Int,
    ): ResponseResult
}