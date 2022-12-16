package com.example.generator2.vm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object LiveConstrain {

    //Чувствительность слайдера
    var sensetingSliderCr =  mutableStateOf( 0.2f)
    var sensetingSliderFmDev =  mutableStateOf( 0.2f)
    var sensetingSliderFmBase =  mutableStateOf( 0.2f)
    var sensetingSliderAmFm =  mutableStateOf( 0.01f)

    var minCR =  mutableStateOf( 600f )
    var maxCR =  mutableStateOf(  4000f)

    var minModAmFm =  mutableStateOf(  0.1f)
    var maxModAmFm =  mutableStateOf(  100.0f)
    var minFMBase =  mutableStateOf(  1000f)
    var maxFMBase =  mutableStateOf(  3000f)
    var minFMDev =  mutableStateOf(  1f)
    var maxFMDev =  mutableStateOf(  2500f)

    var rangeSliderCr     = minCR.value..maxCR.value
    var rangeSliderAmFm   = minModAmFm.value..maxModAmFm.value
    var rangeSliderFmBase = minFMBase.value..maxFMBase.value
    var rangeSliderFmDev  = minFMDev.value..maxFMDev.value

}