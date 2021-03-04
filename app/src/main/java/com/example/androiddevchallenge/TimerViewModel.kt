package com.example.androiddevchallenge

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.view.animation.LinearInterpolator
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.androiddevchallenge.utils.TimeFormatUtils

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
    fun stopButtonEnabled() = totalTime > 0 && (status == TimerStatus.STARTED || status == TimerStatus.PAUSED)

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

    fun startButtonDisplayString() = status.startButtonDisplayString()

    fun clickStartButton() {
        when (status) {
            TimerStatus.NOT_START, TimerStatus.COMPLETE -> start()
            TimerStatus.STARTED -> pause()
            TimerStatus.PAUSED -> resume()
        }
    }

    fun clickStopButton() {
        stop()
    }

    fun start() {
        if (totalTime == 0L) return
        valueAnim?.cancel()
        valueAnim = ValueAnimator.ofInt(totalTime.toInt(), 0)
        valueAnim?.interpolator = LinearInterpolator()
        valueAnim?.duration = totalTime * 1000L
        valueAnim?.start()
        valueAnim?.addUpdateListener {
            timeLeft = (it.animatedValue as Int).toLong()
        }
        valueAnim?.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                complete()
            }
        })
        status = TimerStatus.STARTED
    }

    fun pause() {
        valueAnim?.pause()
        status = TimerStatus.PAUSED
    }

    fun resume() {
        valueAnim?.resume()
        status = TimerStatus.STARTED
    }

    fun stop() {
        valueAnim?.cancel()
        timeLeft = 0
        status = TimerStatus.NOT_START
    }

    fun complete() {
        status = TimerStatus.COMPLETE
    }

    fun reset() {
        totalTime = 0
        timeLeft = 0
        status = TimerStatus.NOT_START
    }

    fun completeString() = if (status == TimerStatus.COMPLETE) "Complete!" else ""
    fun hideEditText() = status == TimerStatus.STARTED || status == TimerStatus.PAUSED
}

enum class TimerStatus {
    NOT_START, STARTED, PAUSED, COMPLETE;

    fun startButtonDisplayString() = when (this) {
        NOT_START, COMPLETE -> "Start"
        STARTED -> "Pause"
        PAUSED -> "Resume"
    }
}