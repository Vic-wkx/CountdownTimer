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
package com.example.androiddevchallenge.status

import com.example.androiddevchallenge.model.TimerViewModel

/**
 * Description:
 * Not Started
 *
 * @author Alpinist Wang
 * Date:    2021/3/5
 */
class NotStartedStatus(private val viewModel: TimerViewModel) : IStatus {

    override fun startButtonDisplayString() = "Start"

    override fun clickStartButton() = viewModel.animatorController.start()

    override fun stopButtonEnabled() = false

    override fun clickStopButton() {}

    override fun showEditText() = true

    override fun progressSweepAngle() = if (viewModel.totalTime > 0) 360f else 0f

    override fun completeString() = ""
}
