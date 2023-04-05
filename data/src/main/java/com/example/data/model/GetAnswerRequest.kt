package com.example.data.model

data class GetAnswerRequest (
    val model: String,
    val prompt : String,
    val temperature: Double,
    val max_tokens:Int,
    val top_p:Int,
    val frequency_penalty:Double,
    val presence_penalty: Int
        )