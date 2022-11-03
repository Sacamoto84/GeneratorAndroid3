package com.example.generator2

import android.annotation.SuppressLint
import android.content.Context
import android.icu.text.AlphabeticIndex
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.runtime.*
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.generator2.mainscreen4.bottomBarEnum
import com.example.generator2.scripting.Script
import com.example.generator2.scripting.ui.ScriptKeyboard
import flipagram.assetcopylib.AssetCopier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.IOException

@SuppressLint("StaticFieldLeak")
object Global : ViewModel(){

    var contextActivity   : Context? = null
    var componentActivity : ComponentActivity? = null

    var patchDocument =  ""
    var patchCarrier  = "$patchDocument/Carrier/"
    var patchMod      = "$patchDocument/Mod/"

    var ch1_EN                 = MutableLiveData<Boolean>( true)          //: MutableState<Int> = mutableStateOf( 1)
    var ch1_Carrier_Filename   = MutableLiveData<String> ( "03_HWave2")
    var ch1_Carrier_Fr         = MutableLiveData<Float>    ( 2000.0f)     //Частота несущей

    var ch1_AM_EN              = MutableLiveData<Boolean>( false)
    var ch1_AM_Filename        = MutableLiveData<String> ( "09_Ramp")
    var ch1_AM_Fr              = MutableLiveData<Float>  ( 8.7f)
    var ch1_FM_EN              = MutableLiveData<Boolean>( false)
    var ch1_FM_Filename        = MutableLiveData<String> ( "06_CHIRP")
    var ch1_FM_Base            = MutableLiveData<Float>  ( 2500f)       //Частота базы
    var ch1_FM_Dev             = MutableLiveData<Float>  ( 1100f)       //Частота базы
    var ch1_FM_Fr              = MutableLiveData<Float>  ( 5.1f)

    var ch2_EN                 = MutableLiveData<Boolean>( false)
    var ch2_Carrier_Filename   = MutableLiveData<String> ( "03_HWave2")
    var ch2_Carrier_Fr         = MutableLiveData<Float>  ( 2000.0f) //Частота несущей
    var ch2_AM_EN              = MutableLiveData<Boolean>( false)
    var ch2_AM_Filename        = MutableLiveData<String> ( "09_Ramp")
    var ch2_AM_Fr              = MutableLiveData<Float>  ( 8.7f)
    var ch2_FM_EN              = MutableLiveData<Boolean>( false)
    var ch2_FM_Filename        = MutableLiveData<String> ( "06_CHIRP")
    var ch2_FM_Base            = MutableLiveData<Float>  ( 2500f) //Частота базы
    var ch2_FM_Dev             = MutableLiveData<Float>  ( 1100f)//Частота базы
    var ch2_FM_Fr              = MutableLiveData<Float>  ( 5.1f)

    var itemlistCarrier: ArrayList<itemList> = ArrayList() //Создать список
    var itemlistAM     : ArrayList<itemList> = ArrayList() //Создать список
    var itemlistFM     : ArrayList<itemList> = ArrayList() //Создать список

    val onoffconfig  : ConfigOnOff = ConfigOnOff()
    val onoffconfig1 : ConfigOnOff = ConfigOnOff()


    //Пути для отрисовки нижнего меню
    var bottomBarRoute = mutableStateOf( bottomBarEnum.HOME)

    fun init()
    {

        val file = contextActivity!!.getExternalFilesDir("") //Создать если нет папку generator2
        contextActivity!!.getExternalFilesDir("/Carrier")
        contextActivity!!.getExternalFilesDir("/Mod")
        patchDocument = file!!.toString()
        patchCarrier  = "$patchDocument/Carrier/"
        patchMod      = "$patchDocument/Mod/"

        Utils.patchDocument = patchDocument
        Utils.patchCarrier  = patchCarrier
        Utils.patchMod      = patchMod

        try {
            AssetCopier(contextActivity) //.withFileScanning()
                .copy("Carrier", File(patchCarrier))
        }
        catch (e: IOException) {
            Log.d("Init",e.printStackTrace().toString())
        }

        try {
            AssetCopier(contextActivity) //.withFileScanning()
                .copy("Mod", File(patchMod))
        }
        catch (e: IOException) {
            Log.d("Init",e.printStackTrace().toString())
        }

    }

    fun observe()
    {

        ch1_EN.observeForever { ch1_EN ->
            Log.d("observeForever", "onClick")
            PlaybackEngine.CH_EN(0, ch1_EN!!)
        }

        ch2_EN.observeForever { ch2_EN ->
            Log.d("observeForever", "onClick")
            PlaybackEngine.CH_EN(1, ch2_EN!!)
        }

        ch1_AM_EN.observeForever { ch1_AM_EN ->
            Log.d("observeForever", "onClick")
            PlaybackEngine.CH_AM_EN(0, ch1_AM_EN!!)
        }

        ch2_AM_EN.observeForever { ch2_AM_EN ->
            Log.d("observeForever", "onClick")
            PlaybackEngine.CH_AM_EN(1, ch2_AM_EN!!)
        }

        ch1_FM_EN.observeForever { ch1_FM_EN ->
            Log.d("observeForever", "onClick")
            PlaybackEngine.CH_FM_EN(0, ch1_FM_EN!!)
        }

        ch2_FM_EN.observeForever { ch2_FM_EN ->
            Log.d("observeForever", "onClick")
            PlaybackEngine.CH_FM_EN(1, ch2_FM_EN!!)
        }


        ch1_Carrier_Fr.observeForever { ch1_Carrier_Fr ->
            Log.d("observeForever", "onClick")
            val fr = ch1_Carrier_Fr.toInt().toFloat()
            PlaybackEngine.CH_Carrier_fr(0, fr)
        }

        ch2_Carrier_Fr.observeForever { ch2_Carrier_Fr ->
            Log.d("observeForever", "onClick")
            val fr = ch2_Carrier_Fr.toInt().toFloat()
            PlaybackEngine.CH_Carrier_fr(1, fr)
        }

        ch1_AM_Fr.observeForever { ch1_AM_Fr ->
            Log.d("observeForever", "onClick")
            PlaybackEngine.CH_AM_fr(0, ch1_AM_Fr!!)
        }

        ch2_AM_Fr.observeForever { ch2_AM_Fr ->
            Log.d("observeForever", "onClick")
            PlaybackEngine.CH_AM_fr(1, ch2_AM_Fr!!)
        }

        ch1_FM_Base.observeForever { ch1_FM_Base ->
            val fr = ch1_FM_Base.toInt().toFloat()
            Log.d("observeForever", "onClick")
            PlaybackEngine.CH_FM_Base(0, fr)
        }

        ch2_FM_Base.observeForever { ch2_FM_Base ->
            val fr = ch2_FM_Base.toInt().toFloat()
            Log.d("observeForever", "onClick")
            PlaybackEngine.CH_FM_Base(1, fr)
        }

        ch1_FM_Dev.observeForever { ch1_FM_Dev ->
            val fr = ch1_FM_Dev.toInt().toFloat()
            Log.d("observeForever", "onClick")
            PlaybackEngine.CH_FM_Dev(0, fr)
        }

        ch2_FM_Dev.observeForever { ch2_FM_Dev ->
            val fr = ch2_FM_Dev.toInt().toFloat()
            Log.d("observeForever", "onClick")
            PlaybackEngine.CH_FM_Dev(1, fr)
        }

        ch1_FM_Fr.observeForever { ch1_FM_Fr ->
            Log.d("observeForever", "onClick")
            PlaybackEngine.CH_FM_fr(0, ch1_FM_Fr!!)
        }

        ch2_FM_Fr.observeForever { ch2_FM_Fr ->
            Log.d("observeForever", "onClick")
            PlaybackEngine.CH_FM_fr(1, ch2_FM_Fr!!)
        }

        ch1_Carrier_Filename.observeForever{ name ->
            Log.d("observeForever", "onClick")
            Utils.Spinner_Send_Buffer( "CH0", "CR", name ) //Читае м отсылаем массив
        }

        ch2_Carrier_Filename.observeForever{ name ->
            Log.d("observeForever", "onClick")
            Utils.Spinner_Send_Buffer( "CH1", "CR", name ) //Читае м отсылаем массив
        }

        ch1_AM_Filename.observeForever{ name ->
            Log.d("observeForever", "onClick")
            Utils.Spinner_Send_Buffer( "CH0", "AM", name ) //Читае м отсылаем массив
        }

        ch2_AM_Filename.observeForever{ name ->
            Log.d("observeForever", "onClick")
            Utils.Spinner_Send_Buffer( "CH1", "AM", name ) //Читае м отсылаем массив
        }

        ch1_FM_Filename.observeForever{ name ->
            Log.d("observeForever", "onClick")
            Utils.Spinner_Send_Buffer( "CH0", "FM", name ) //Читае м отсылаем массив
        }

        ch2_FM_Filename.observeForever{ name ->
            Log.d("observeForever", "onClick")
            Utils.Spinner_Send_Buffer( "CH1", "FM", name ) //Читае м отсылаем массив
        }

    }

    fun sendAlltoGen()
    {
        PlaybackEngine.CH_EN(0, ch1_EN.value!!)
        PlaybackEngine.CH_EN(1, ch2_EN.value!!)
        PlaybackEngine.CH_AM_EN(0, ch1_AM_EN.value!!)
        PlaybackEngine.CH_AM_EN(1, ch2_AM_EN.value!!)
        PlaybackEngine.CH_FM_EN(0, ch1_FM_EN.value!!)
        PlaybackEngine.CH_FM_EN(1, ch2_FM_EN.value!!)
        PlaybackEngine.CH_Carrier_fr(0, ch1_Carrier_Fr.value!!)
        PlaybackEngine.CH_Carrier_fr(1, ch2_Carrier_Fr.value!!)
        PlaybackEngine.CH_AM_fr(0, ch1_AM_Fr.value!!)
        PlaybackEngine.CH_AM_fr(1, ch2_AM_Fr.value!!)
        PlaybackEngine.CH_FM_Base(0, ch1_FM_Base.value!!)
        PlaybackEngine.CH_FM_Base(1, ch2_FM_Base.value!!)
        PlaybackEngine.CH_FM_Dev(0, ch1_FM_Dev.value!!)
        PlaybackEngine.CH_FM_Dev(1, ch2_FM_Dev.value!!)
        PlaybackEngine.CH_FM_fr(0, ch1_FM_Fr.value!!)
        PlaybackEngine.CH_FM_fr(1, ch2_FM_Fr.value!!)

        Utils.Spinner_Send_Buffer("CH0","CR",   ch1_Carrier_Filename.value )
        Utils.Spinner_Send_Buffer("CH0","AM",   ch1_AM_Filename.value )
        Utils.Spinner_Send_Buffer("CH0","FM",   ch1_FM_Filename.value )

        Utils.Spinner_Send_Buffer("CH1","CR",   ch2_Carrier_Filename.value )
        Utils.Spinner_Send_Buffer("CH1","AM",   ch2_AM_Filename.value )
        Utils.Spinner_Send_Buffer("CH1","FM",   ch2_FM_Filename.value )

    }







    ////////////////////////////////////////////////////////





    val script : Script = Script()

    fun launchScriptScope() {
        viewModelScope.launch {
            scriptRun()

        }
    }

    private suspend fun scriptRun() = withContext(Dispatchers.Main)
    {
        while(true) {
            script.run()
            delay(10)
        }
    }






}