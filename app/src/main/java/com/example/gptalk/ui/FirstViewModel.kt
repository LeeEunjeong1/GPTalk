package com.example.gptalk.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.GetAnswerRequest
import com.example.domain.usecase.RequestGetAnswerUseCase
import com.example.domain.utils.ErrorType
import com.example.domain.utils.RemoteErrorEmitter
import com.example.domain.utils.Util
import com.example.gptalk.model.Chatting
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FirstViewModel @Inject constructor(
    private val requestGetAnswerUseCase: RequestGetAnswerUseCase
) : ViewModel(), RemoteErrorEmitter {
    var chatList = MutableLiveData<ArrayList<Chatting>>()

    override fun onError(errorType: ErrorType) {
        Util.logMessage("onError :: $errorType")
    }

    override fun onError(msg: String) {
        Util.logMessage("onError :: $msg")
    }

    // 질문 제출 - gpt api
    fun submit(text:String) {
        viewModelScope.launch {
            val response = requestGetAnswerUseCase.excute(this@FirstViewModel,
            GetAnswerRequest(
                text,
                1.0,
                1.0
            )
                )

            response?.let{
                Util.logMessage("it :: $it")
                setChatting(Chatting("A",it.choices.text))
            }
        }
    }

    fun setChatting(chatting:Chatting){
        val temp = arrayListOf<Chatting>().apply {
            chatList.value?.let { data -> addAll(data) }
            add(chatting)
        }
        chatList.postValue(temp)
    }

}