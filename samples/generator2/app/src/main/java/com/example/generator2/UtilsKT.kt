package com.example.generator2

fun Float.format(digits: Int) = "%.${digits}f".format(this)
