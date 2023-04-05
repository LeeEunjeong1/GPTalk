package com.example.domain.model

data class GetAnswerRequest(
    val prompt: String,
    val temperature: Double,
    val frequency_penalty: Double
)