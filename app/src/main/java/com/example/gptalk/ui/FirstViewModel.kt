package com.example.gptalk.ui

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.GetAnswerRequest
import com.example.domain.usecase.RequestGetAnswerUseCase
import com.example.domain.utils.ErrorType
import com.example.domain.utils.RemoteErrorEmitter
import com.example.domain.utils.Util
import com.example.gptalk.utils.ApiClient
import com.example.gptalk.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Collections.addAll
import javax.inject.Inject

@HiltViewModel
class FirstViewModel @Inject constructor(
    private val requestGetAnswerUseCase: RequestGetAnswerUseCase
) : ViewModel(), RemoteErrorEmitter {
    var chatList = MutableLiveData<ArrayList<String>>()

    override fun onError(errorType: ErrorType) {
        Util.logMessage("onError :: $errorType")
    }

    override fun onError(msg: String) {
        Util.logMessage("onError :: $msg")
    }

    fun submit() {
        viewModelScope.launch {
            val response = requestGetAnswerUseCase.excute(this@FirstViewModel,
            GetAnswerRequest(
                "hello",
                1.0,
                1.0
            )
                )
            response?.let{
                Util.logMessage("it :: $it")
            }
        }
    }

}