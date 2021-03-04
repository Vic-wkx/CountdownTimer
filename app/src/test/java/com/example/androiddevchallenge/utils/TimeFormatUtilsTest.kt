package com.example.androiddevchallenge.utils

import junit.framework.TestCase
import org.junit.Test

class TimeFormatUtilsTest : TestCase() {
    @Test
    fun test() {
        println(TimeFormatUtils.formatTime(0))
        println(TimeFormatUtils.formatTime(10))
        println(TimeFormatUtils.formatTime(100))
        println(TimeFormatUtils.formatTime(1000))
        println(TimeFormatUtils.formatTime(10000))
    }
}