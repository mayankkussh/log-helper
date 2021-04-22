package com.mayankkusshl.loghelper

import android.text.TextUtils
import android.util.Log

object LogHelper {

    /***
     * Creates a log tag in a predefined manner to introduce uniformity in printed logs
     */
    fun createLogTag(parentLogTag: String?, myLogTag: String): LogTag {
        return if (parentLogTag != null) {
            return LogTag(parentLogTag, myLogTag)
        } else {
            Log.d(
                myLogTag,
                "No parent tag, you should always provide parent tag for better logging"
            )
            LogTag(currentTag = myLogTag)
        }
    }

    /***
     * Logs the value in log cat
     * $logTag logtag with which logs should be printed
     * $value the value to be printed in logs
     * $e Exception to be printed if there is any
     * $showInProduction True to show this particular log in production
     */

    fun log(
        logTag: LogTag,
        value: String,
        e: Throwable? = null,
        showInProduction: Boolean = false
    ) {
        Log.d(logTag.tag, value, e)
        //TODO Add conditions based on your Build Configs
    }

    /***
     * Logs the execution time of the particular function passed in the lambda
     */
    fun <T> logFunctionTime(func: () -> T, tag: LogTag? = null, showStackTrace: Boolean = false): T {
        val trace = if (showStackTrace) {
            Thread.currentThread().stackTrace.slice(3..6)
        } else {
            emptyList()
        }
        val startTime = System.nanoTime()
        val value = func.invoke()
        val totalTime = System.nanoTime() - startTime
        log(
            tag
                ?: LogTag(
                    LogHelper::class.java.simpleName,
                    currentTag = "ExecutionTime"
                ),
            "$totalTime ns , " + "${totalTime / 1000000} ms, ${TextUtils.join("\n", trace)}"
        )
        return value
    }

    /***
     * Print large log values in a pretty manner without trimming
     */
    fun largeLog(tag: LogTag, content: String) {
        if (content.length > 4000) {
            log(tag, content.substring(0, 4000))
            largeLog(tag, content.substring(4000))
        } else {
            log(tag, content)
        }
    }

}