package com.example.gptalk.ui

import androidx.lifecycle.ViewModel
import com.example.data.utils.PrefUtil
import com.example.domain.usecase.LocalResetChattingUseCase
import com.example.domain.utils.ErrorType
import com.example.domain.utils.RemoteErrorEmitter
import com.example.domain.utils.Util
import com.example.gptalk.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val prefUtil: PrefUtil,
    private val localResetChattingUseCase: LocalResetChattingUseCase
) : ViewModel(), RemoteErrorEmitter {
    private val mutableErrorMessage = SingleLiveEvent<String>()
    val mutableErrorType = SingleLiveEvent<ErrorType>()
    val chatResetResult= SingleLiveEvent<Boolean>()

    override fun onError(errorType: ErrorType) {
        Util.logMessage("onError :: $errorType")
        mutableErrorType.postValue(errorType)
    }

    override fun onError(msg: String) {
        Util.logMessage("onError :: $msg")
        mutableErrorMessage.postValue(msg)
    }

    // 채팅 리셋
    fun chatReset(){
        CoroutineScope(Dispatchers.IO).launch{
            val res = localResetChattingUseCase.excute(this@SettingViewModel )
            Util.logMessage("res :: $res")
            chatResetResult.postValue(res)
        }
    }
}