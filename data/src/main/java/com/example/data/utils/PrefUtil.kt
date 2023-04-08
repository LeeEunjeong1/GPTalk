package com.example.data.utils

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject


class PrefUtil @Inject constructor(
    private val context: Context
) {
    private val PREF_KEY = "pref_data_key"

    //디폴트 키 저장소
    fun getPref(): SharedPreferences {
        return context.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE)
    }

    fun getEditor(): SharedPreferences.Editor {
        return context.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE).edit()
    }

    //디폴트 get
    fun getString(key: String): String {
        return getPref().getString(key, "") ?: ""
    }

    //디폴트 단건 set
    fun setString(key: String, value: String) {
        val editor: SharedPreferences.Editor = getEditor().putString(key, value)
        editor.commit()
    }

}
