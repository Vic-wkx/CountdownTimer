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
package com.example.androiddevchallenge

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.view.animation.LinearInterpolator
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.androiddevchallenge.utils.TimeFormatUtils

/**
 * Created by Kevin 2021-03-04
 */
class TimeViewModel : ViewModel() {
    var status: TimerStatus by mutableStateOf(TimerStatus.NOT_START)
    var totalTime: Long by mutableStateOf(0)
    var timeLeft: Long by mutableStateOf(0)
    var valueAnim: ValueAnimator? by mutableStateOf(null)

    fun progressSweepAngle(): Float {
        return if (status == TimerStatus.NOT_START || status == TimerStatus.COMPLETE) {
            360f
        } else {
            timeLeft * 1.0f / totalTime * 360
        }
    }

    fun startButtonEnabled() = totalTime > 0

    fun startButtonDisplayString() = status.startButtonDisplayString()

    fun clickStartButton() {
        when (status) {
            TimerStatus.NOT_START, TimerStatus.COMPLETE -> start()
            TimerStatus.STARTED -> pause()
            TimerStatus.PAUSED -> resume()
        }
    }

    fun stopButtonEnabled() = totalTime > 0 && (status == TimerStatus.STARTED || status == TimerStatus.PAUSED)

    fun clickStopButton() {
        stop()
    }

    fun timeLeftValue() = TimeFormatUtils.formatTime(timeLeft)

    fun editTextValue() = if (totalTime == 0L) "" else totalTime.toString()

    fun editTextValueChanged(it: String) {
        // max length: 5
        if (it.length > 5) return
        var value = it.replace("\\D".toRegex(), "")
        if (value.startsWith("0")) value = value.substring(1)
        if (value.isBlank()) value = "0"
        timeLeft = value.toLong()
        totalTime = value.toLong()
    }

    fun hideEditText() = status == TimerStatus.STARTED || status == TimerStatus.PAUSED

    fun completeString() = if (status == TimerStatus.COMPLETE) "Complete!" else ""

    private fun start() {
        if (totalTime == 0L) return
        valueAnim?.cancel()
        valueAnim = ValueAnimator.ofInt(totalTime.toInt(), 0)
        valueAnim?.interpolator = LinearInterpolator()
        valueAnim?.duration = totalTime * 1000L
        valueAnim?.addUpdateListener {
            timeLeft = (it.animatedValue as Int).toLong()
        }
        valueAnim?.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                complete()
            }
        })
        valueAnim?.start()
        status = TimerStatus.STARTED
    }

    private fun pause() {
        valueAnim?.pause()
        status = TimerStatus.PAUSED
    }

    private fun resume() {
        valueAnim?.resume()
        status = TimerStatus.STARTED
    }

    private fun stop() {
        valueAnim?.cancel()
        timeLeft = 0
        status = TimerStatus.NOT_START
    }

    private fun complete() {
        status = TimerStatus.COMPLETE
    }
}

enum class TimerStatus {
    NOT_START, STARTED, PAUSED, COMPLETE;

    fun startButtonDisplayString() = when (this) {
        NOT_START, COMPLETE -> "Start"
        STARTED -> "Pause"
        PAUSED -> "Resume"
    }
}
