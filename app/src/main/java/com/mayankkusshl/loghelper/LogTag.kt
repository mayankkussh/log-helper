package com.mayankkusshl.loghelper

data class LogTag(private val parentLogTag: String? = "", private val currentTag: String) {
    val tag = "$parentLogTag|$currentTag"
}