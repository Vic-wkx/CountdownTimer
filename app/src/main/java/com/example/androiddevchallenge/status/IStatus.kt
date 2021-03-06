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

/**
 * Description:
 * Status interface
 *
 * @author Alpinist Wang
 * Date:    2021/3/4
 */
interface IStatus {
    /**
     * The content string displayed in Start Button.
     * include: Start, Pause, Resume.
     */
    fun startButtonDisplayString(): String

    /**
     * The behaviour when click Start Button.
     */
    fun clickStartButton()

    /**
     * Stop Button enable status
     */
    fun stopButtonEnabled(): Boolean

    /**
     * The behaviour when click Stop Button.
     */
    fun clickStopButton()

    /**
     * Show or hide EditText
     */
    fun showEditText(): Boolean

    /**
     * Sweep angle of progress circle
     */
    fun progressSweepAngle(): Float

    /**
     * Completed string
     */
    fun completedString(): String
}
