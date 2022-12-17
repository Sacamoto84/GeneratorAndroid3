package com.example.benchmark

import androidx.benchmark.macro.junit4.BaselineProfileRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class BaselineProfileGenerator {

    @get:Rule
    val baselineRule = BaselineProfileRule()


    @Test
    fun generateBaseProfile()= baselineRule.collectBaselineProfile(
        packageName = "com.example.generator2"
    )
    {
        pressHome()
        startActivityAndWait()

        addElementAndScrollDown()
    }

}