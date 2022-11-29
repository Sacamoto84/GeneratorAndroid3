package com.example.generator2.vm

import androidx.lifecycle.MutableLiveData

class vmLiveData {

        var ch1_EN = MutableLiveData<Boolean>(true)
        var ch1_Carrier_Filename = MutableLiveData<String>("03_HWave2")
        var ch1_Carrier_Fr = MutableLiveData<Float>(2000.0f)     //Частота несущей
        var ch1_AM_EN = MutableLiveData<Boolean>(false)
        var ch1_AM_Filename = MutableLiveData<String>("09_Ramp")
        var ch1_AM_Fr = MutableLiveData<Float>(8.7f)
        var ch1_FM_EN = MutableLiveData<Boolean>(false)
        var ch1_FM_Filename = MutableLiveData<String>("06_CHIRP")
        var ch1_FM_Base = MutableLiveData<Float>(2500f)       //Частота базы
        var ch1_FM_Dev = MutableLiveData<Float>(1100f)       //Частота базы
        var ch1_FM_Fr = MutableLiveData<Float>(5.1f)

        var ch2_EN = MutableLiveData<Boolean>(false)
        var ch2_Carrier_Filename = MutableLiveData<String>("03_HWave2")
        var ch2_Carrier_Fr = MutableLiveData<Float>(2000.0f) //Частота несущей
        var ch2_AM_EN = MutableLiveData<Boolean>(false)
        var ch2_AM_Filename = MutableLiveData<String>("09_Ramp")
        var ch2_AM_Fr = MutableLiveData<Float>(8.7f)
        var ch2_FM_EN = MutableLiveData<Boolean>(false)
        var ch2_FM_Filename = MutableLiveData<String>("06_CHIRP")
        var ch2_FM_Base = MutableLiveData<Float>(2500f) //Частота базы
        var ch2_FM_Dev = MutableLiveData<Float>(1100f) //Частота базы
        var ch2_FM_Fr = MutableLiveData<Float>(5.1f)

    }






