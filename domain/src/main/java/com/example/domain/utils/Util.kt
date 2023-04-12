package com.example.domain.utils


import android.util.Log
import com.example.domain.BuildConfig

object Util {
    private const val TAG = "log_msg"
    fun logMessage(msg:String){
        if(BuildConfig.DEBUG){
            Log.d(TAG, msg)
        }
    }
}