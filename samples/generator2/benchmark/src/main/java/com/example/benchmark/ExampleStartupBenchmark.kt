package com.example.benchmark

import androidx.benchmark.macro.*
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.uiautomator.By
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * This is an example startup benchmark.
 *
 * It navigates to the device's home screen, and launches the default activity.
 *
 * Before running this benchmark:
 * 1) switch your app's active build variant in the Studio (affects Studio runs only)
 * 2) add `<profileable android:shell="true" />` to your app's manifest, within the `<application>` tag
 *
 * Run this benchmark from Studio to see startup measurements, and captured system traces
 * for investigating your app's performance.
 */
@RunWith(AndroidJUnit4::class)
class ExampleStartupBenchmark {
    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()



   @Test
   fun startUpCompilationModeNone() = startup(CompilationMode.None())

   @Test
   fun startUpCompilationModePartial() = startup(CompilationMode.Partial())






    fun startup(mode : CompilationMode) = benchmarkRule.measureRepeated(
        packageName = "com.example.generator2",
        metrics = listOf(StartupTimingMetric()),
        iterations = 5,
        startupMode = StartupMode.COLD,
        compilationMode = mode
    ) {
        pressHome()
        startActivityAndWait()
    }

    @Test
    fun script() = benchmarkRule.measureRepeated(
        packageName = "com.example.generator2",
        metrics = listOf(FrameTimingMetric()),
        iterations = 5,
        startupMode = StartupMode.COLD
    ) {
        pressHome()
        startActivityAndWait()
        addElementAndScrollDown()
    }

}

fun MacrobenchmarkScope.addElementAndScrollDown(){

    val button = device.findObject(By.text("Click"))
    //val buttonEdit = device.findObject(By.res("edit"))

    button.click()

    device.waitForIdle()



}