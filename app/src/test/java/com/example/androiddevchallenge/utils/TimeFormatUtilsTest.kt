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
package com.example.androiddevchallenge.utils

import junit.framework.TestCase
import org.junit.Assert
import org.junit.Test

/**
 * Description:
 * Test TimeFormatUtils
 *
 * @author Alpinist Wang
 * Date:    2021/3/5
 */
class TimeFormatUtilsTest : TestCase() {
    @Test
    fun test() {
        Assert.assertEquals("00:00:00", TimeFormatUtils.formatTime(0))
        Assert.assertEquals("00:00:30", TimeFormatUtils.formatTime(30))
        Assert.assertEquals("00:01:00", TimeFormatUtils.formatTime(60))
        Assert.assertEquals("00:10:30", TimeFormatUtils.formatTime(630))
        Assert.assertEquals("01:40:00", TimeFormatUtils.formatTime(6000))
    }
}
