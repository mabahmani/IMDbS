package com.mabahmani.domain.util

fun String?.orDefault() = this ?: ""

fun Int?.orZero() = this ?: 0

fun Long?.orZero() = this ?: 0L

fun Float?.orZero() = this ?: 0f

fun Boolean?.orTrue() = this ?: true

fun Boolean?.orFalse() = this ?: false