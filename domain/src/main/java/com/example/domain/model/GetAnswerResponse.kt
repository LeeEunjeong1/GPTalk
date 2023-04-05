package com.example.domain.model

data class GetAnswerResponse(
    val id: String,
    val created: String,
    val choices: Choice
)

data class Choice(
    val text: String,
    val index: Int,
    val logprobs: String?,
    val finish_reason: String
)
