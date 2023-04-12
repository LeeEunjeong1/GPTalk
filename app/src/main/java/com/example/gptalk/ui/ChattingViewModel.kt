package com.example.gptalk.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.data.utils.PrefUtil
import com.example.domain.model.GetAnswerRequest
import com.example.domain.usecase.LocalGetChattingUseCase
import com.example.domain.usecase.LocalSetChattingUseCase
import com.example.domain.usecase.RequestGetAnswerUseCase
import com.example.domain.utils.ErrorType
import com.example.domain.utils.RemoteErrorEmitter
import com.example.domain.utils.Util
import com.example.gptalk.model.Chatting
import com.example.gptalk.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChattingViewModel @Inject constructor(
    private val requestGetAnswerUseCase: RequestGetAnswerUseCase,
    private val localSetChattingUseCase: LocalSetChattingUseCase,
    private val localGetChattingUseCase: LocalGetChattingUseCase,
    private val prefUtil: PrefUtil
) : ViewModel(), RemoteErrorEmitter {
    var chatList = MutableLiveData<ArrayList<Chatting>>()
    private val mutableErrorMessage = SingleLiveEvent<String>()
    val mutableErrorType = SingleLiveEvent<ErrorType>()

    override fun onError(errorType: ErrorType) {
        Util.logMessage("onError :: $errorType")
        mutableErrorType.postValue(errorType)
    }

    override fun onError(msg: String) {
        Util.logMessage("onError :: $msg")
        mutableErrorMessage.postValue(msg)
    }
    init{
        localGetChatting()
    }

    // 질문 제출 - gpt api
    fun submit(text: String) {
        CoroutineScope(Dispatchers.IO).launch {
            // temperature, frequencyPenalty 저장값 불러오기
            val temperature = if (prefUtil.getString("TEMPERATURE") == "") {
                1.0
            } else {
                prefUtil.getString("TEMPERATURE").toDouble()
            }
            val frequencyPenalty = if (prefUtil.getString("FREQUENCY_PENALTY") == "") {
                0.0
            } else {
                prefUtil.getString("FREQUENCY_PENALTY").toDouble()
            }
            localSetChatting("Q",text)
            // 질문 요청
            val response = requestGetAnswerUseCase.excute(
                    this@ChattingViewModel,
                    GetAnswerRequest(
                        text,
                        temperature,
                        frequencyPenalty
                    )
                )
                response?.let {
                    Util.logMessage("it :: $it")
                    localSetChatting("A",it.choices.text)
                    setChatting(Chatting("A", it.choices.text.trim()))
                }
        }
    }

    // 채팅 화면 연결
    fun setChatting(chatting: Chatting) {
        val temp = arrayListOf<Chatting>().apply {
            chatList.value?.let { data -> addAll(data) }
            add(chatting)
        }
        chatList.postValue(temp)
    }

    // 로컬 채팅 질문 저장
    private fun localSetChatting(mode:String, text:String){
        CoroutineScope(Dispatchers.IO).launch {

            localSetChattingUseCase.excute(
                this@ChattingViewModel,
                com.example.domain.model.Chatting(mode,text)
            )
        }
    }

    // 로컬 채팅 값 불러 오기
    fun localGetChatting(){
        CoroutineScope(Dispatchers.IO).launch{
            val res = localGetChattingUseCase.excute(this@ChattingViewModel)
            Util.logMessage("res_local :: $res")
            val temp = arrayListOf<Chatting>()
            res.forEach{
                temp.add(Chatting(it.mode,it.text))
            }
            chatList.postValue(temp)
        }
    }

}