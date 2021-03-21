package com.your.time.app.expansions

fun String.extractDigits(): Int {
    val builder = StringBuilder()
    (0 until this.length)
            .map { this[it] }
            .filter { Character.isDigit(it) }
            .forEach { builder.append(it) }
    return builder.toString().toInt()
}