package com.example.data.mapper

import com.example.data.model.ResponseResult
import com.example.domain.model.Choice
import com.example.domain.model.GetAnswerResponse

object Mapper {
    fun mapperToAnswer(responseResult:ResponseResult): GetAnswerResponse {
        return GetAnswerResponse(
          id = responseResult.id,
          created = responseResult.created,
          choices = Choice(
              responseResult.choices[0].text,
              responseResult.choices[0].index,
              responseResult.choices[0].logprobs,
              responseResult.choices[0].finish_reason
          )
      )
    }
}