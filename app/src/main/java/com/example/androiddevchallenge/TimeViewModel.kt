package com.example.androiddevchallenge

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class TimeViewModel : ViewModel() {
    var totalTime: String by mutableStateOf("100")
    var timeLeft: String by mutableStateOf("100")
}