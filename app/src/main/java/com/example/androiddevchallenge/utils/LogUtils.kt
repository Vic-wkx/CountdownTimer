package com.example.androiddevchallenge.utils

import android.os.Looper
import android.util.Log

/**
 * Created by Kevin 2021-12-13
 */
private const val DEFAULT_TAG = "UNKNOWN" // Default tag if parsing tag failed, because null or empty tag cannot be printed to log console.

object LogUtils {

    fun v(content: String, stackOffset: Int = 0) = log(Log.VERBOSE, content, stackOffset)
    fun d(content: String, stackOffset: Int = 0) = log(Log.DEBUG, content, stackOffset)
    fun i(content: String, stackOffset: Int = 0) = log(Log.INFO, content, stackOffset)
    fun w(content: String, stackOffset: Int = 0) = log(Log.WARN, content, stackOffset)
    fun e(content: String, stackOffset: Int = 0) = log(Log.ERROR, content, stackOffset)

    private fun log(type: Int, content: String, stackOffset: Int = 0) {
        val depth = 3 + stackOffset
        val trace = Throwable().stackTrace.takeIf { it.size > depth }?.get(depth)
        val printContent = "$content ${parseThread()}, ${parseLocation(trace)}"
        val tag = parseTag(trace)
        // Print to console
        Log.println(type, tag, printContent)
    }

    /**
     * find thread info of this log
     */
    private fun parseThread(): String {
        return "Thread-Name: ${Thread.currentThread().name}, isMain: ${Looper.getMainLooper() == Looper.myLooper()}"
    }

    /**
     * find code location of this log
     */
    private fun parseLocation(trace: StackTraceElement?): String {
        trace ?: return ""
        if (trace.methodName.isNullOrEmpty() || trace.fileName.isNullOrEmpty() || trace.lineNumber <= 0) return ""
        return "Location: ${trace.methodName}(${trace.fileName}:${trace.lineNumber})"
    }

    /**
     * find Class name of this log as tag
     */
    private fun parseTag(trace: StackTraceElement?): String {
        trace ?: return DEFAULT_TAG
        return trace.fileName?.split(".")?.firstOrNull() ?: DEFAULT_TAG
    }
}