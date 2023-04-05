package com.example.data.model

import com.google.gson.annotations.SerializedName

data class ResponseResult(
    val id: String,
    @SerializedName("object")
    val obj: String,
    val created: String,
    val model: String,
    val choices: List<Choice>,
    val usage: Usage
)

data class Choice(
    val text: String,
    val index: Int,
    val logprobs: String?,
    val finish_reason: String
)

data class Usage(
    val prompt_tokens: Int,
    val completion_tokens: Int,
    val total_tokens: Int
)