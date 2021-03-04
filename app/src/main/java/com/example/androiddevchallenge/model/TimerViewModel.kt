/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.androiddevchallenge.status.IStatus
import com.example.androiddevchallenge.status.NotStartedStatus

/**
 * Description:
 * ViewModel of countdown timer
 *
 * @author Alpinist Wang
 * Date:    2021/3/4
 */
// Max input length limit, it's used to prevent number grows too big.
const val MAX_INPUT_LENGTH = 5

class TimerViewModel : ViewModel() {

    /**
     * Total time user set in seconds
     */
    var totalTime: Long by mutableStateOf(0)

    /**
     * Time left during countdown in seconds
     */
    var timeLeft: Long by mutableStateOf(0)

    /**
     * Update value when EditText content changed
     * @param text new content in EditText
     */
    fun updateValue(text: String) {
        // Just in case the number is too big
        if (text.length > MAX_INPUT_LENGTH) return
        // Remove non-numeric elements
        var value = text.replace("\\D".toRegex(), "")
        // Zero cannot appear in the first place
        if (value.startsWith("0")) value = value.substring(1)
        // Set a default value to prevent NumberFormatException
        if (value.isBlank()) value = "0"
        totalTime = value.toLong()
        timeLeft = value.toLong()
    }

    var animatorController = AnimatorController(this)
    var status: IStatus by mutableStateOf(NotStartedStatus(this))
}
