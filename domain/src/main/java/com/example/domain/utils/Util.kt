package com.example.domain.utils


import android.util.Log

object Util {
    private const val TAG = "log_msg"
    fun logMessage(msg:String){
        Log.d(TAG, msg)
    }
    fun logMessage(tag:String, msg:String){
        Log.d("${TAG}_$tag", msg)
    }
}