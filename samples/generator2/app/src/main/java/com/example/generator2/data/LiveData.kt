package com.example.generator2.data

import com.example.generator2.itemList
import kotlinx.coroutines.flow.MutableStateFlow

object LiveData {

        var ch1_EN          = MutableStateFlow<Boolean>(false)
        var ch1_Carrier_Filename = MutableStateFlow<String>("Sine")
        var ch1_Carrier_Fr  = MutableStateFlow<Float>(400.0f)     //Частота несущей
        var ch1_AM_EN       = MutableStateFlow<Boolean>(false)
        var ch1_AM_Filename = MutableStateFlow<String>("09_Ramp")
        var ch1_AM_Fr       = MutableStateFlow<Float>(8.7f)
        var ch1_FM_EN       = MutableStateFlow<Boolean>(false)
        var ch1_FM_Filename = MutableStateFlow<String>("06_CHIRP")
        var ch1_FM_Base     = MutableStateFlow<Float>(2500f)       //Частота базы
        var ch1_FM_Dev      = MutableStateFlow<Float>(1100f)       //Частота базы
        var ch1_FM_Fr       = MutableStateFlow<Float>(5.1f)

        var ch2_EN          = MutableStateFlow<Boolean>(false)
        var ch2_Carrier_Filename = MutableStateFlow<String>("Sine")
        var ch2_Carrier_Fr  = MutableStateFlow<Float>(2000.0f) //Частота несущей
        var ch2_AM_EN       = MutableStateFlow<Boolean>(false)
        var ch2_AM_Filename = MutableStateFlow<String>("09_Ramp")
        var ch2_AM_Fr       = MutableStateFlow<Float>(8.7f)
        var ch2_FM_EN       = MutableStateFlow<Boolean>(false)
        var ch2_FM_Filename = MutableStateFlow<String>("06_CHIRP")
        var ch2_FM_Base     = MutableStateFlow<Float>(2500f) //Частота базы
        var ch2_FM_Dev      = MutableStateFlow<Float>(1100f) //Частота базы
        var ch2_FM_Fr       = MutableStateFlow<Float>(5.1f)

        var volume0 = MutableStateFlow<Float>(0.9f)
        var volume1 = MutableStateFlow<Float>(0.9f)


        var itemlistCarrier: ArrayList<itemList> = ArrayList() //Создать список
        var itemlistAM: ArrayList<itemList> = ArrayList() //Создать список
        var itemlistFM: ArrayList<itemList> = ArrayList() //Создать список

        var mono       = MutableStateFlow<Boolean>(false) //Режим повторения настроек второго канала с первым
        var invert     = MutableStateFlow<Boolean>(false) //Инверсия сигнала во втором канале, только при моно

        var shuffle    = MutableStateFlow<Boolean>(false) //меняем левый и правый канал в стерео режиме

        var enL  = MutableStateFlow<Boolean>(true)
        var enR  = MutableStateFlow<Boolean>(true)

        var link       = MutableStateFlow<Boolean>(false) //Режим повторения настроек второго канала с первым

        var maxVolume0 = MutableStateFlow<Float>(0.9f)
        var maxVolume1 = MutableStateFlow<Float>(0.9f)

        var currentVolume0 = MutableStateFlow<Float>(1.0f)
        var currentVolume1 = MutableStateFlow<Float>(1.0f)

}






