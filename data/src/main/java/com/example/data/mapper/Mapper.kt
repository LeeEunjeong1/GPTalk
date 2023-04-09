package com.example.data.mapper

import com.example.data.model.ChattingEntity
import com.example.data.model.ResponseResult
import com.example.domain.model.Chatting
import com.example.domain.model.Choice
import com.example.domain.model.GetAnswerResponse
import com.example.domain.utils.Util

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
    fun mapperToChatting(selectChatting: List<ChattingEntity>):List<Chatting>{
        val chattingList : MutableList<Chatting> = mutableListOf()
        Util.logMessage("selectChatting :: $selectChatting")
        selectChatting.forEach{
            chattingList.add(Chatting(it.mode,it.text))
        }
        return chattingList
    }
}