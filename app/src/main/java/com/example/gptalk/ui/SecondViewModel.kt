package com.example.gptalk.ui

import androidx.lifecycle.ViewModel
import com.example.data.utils.PrefUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SecondViewModel @Inject constructor(
    private val prefUtil: PrefUtil
) : ViewModel() {

}