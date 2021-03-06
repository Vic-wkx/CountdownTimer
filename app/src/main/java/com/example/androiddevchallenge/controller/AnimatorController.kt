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
package com.example.androiddevchallenge.controller

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.view.animation.LinearInterpolator
import com.example.androiddevchallenge.model.TimerViewModel
import com.example.androiddevchallenge.status.CompletedStatus
import com.example.androiddevchallenge.status.NotStartedStatus
import com.example.androiddevchallenge.status.PausedStatus
import com.example.androiddevchallenge.status.StartedStatus

/**
 * Description:
 * Control ValueAnimator
 *
 * @author Alpinist Wang
 * Date:    2021/3/4
 */
// Control how many times the pointer is updated in a second
const val SPEED = 100

class AnimatorController(private val viewModel: TimerViewModel) {

    private var valueAnimator: ValueAnimator? = null

    fun start() {
        if (viewModel.totalTime == 0L) return
        if (valueAnimator == null) {
            // Animator: totalTime -> 0
            valueAnimator = ValueAnimator.ofInt(viewModel.totalTime.toInt() * SPEED, 0)
            valueAnimator?.interpolator = LinearInterpolator()
            // Update timeLeft in ViewModel
            valueAnimator?.addUpdateListener {
                viewModel.animValue = (it.animatedValue as Int) / SPEED.toFloat()
                viewModel.timeLeft = (it.animatedValue as Int).toLong() / SPEED
            }
            valueAnimator?.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    complete()
                }
            })
        } else {
            valueAnimator?.setIntValues(viewModel.totalTime.toInt() * SPEED, 0)
        }
        // (LinearInterpolator + duration) aim to set the interval as 1 second.
        valueAnimator?.duration = viewModel.totalTime * 1000L
        valueAnimator?.start()
        viewModel.status = StartedStatus(viewModel)
    }

    fun pause() {
        valueAnimator?.pause()
        viewModel.status = PausedStatus(viewModel)
    }

    fun resume() {
        valueAnimator?.resume()
        viewModel.status = StartedStatus(viewModel)
    }

    fun stop() {
        valueAnimator?.cancel()
        viewModel.timeLeft = 0
        viewModel.status = NotStartedStatus(viewModel)
    }

    fun complete() {
        viewModel.totalTime = 0
        viewModel.status = CompletedStatus(viewModel)
    }
}
