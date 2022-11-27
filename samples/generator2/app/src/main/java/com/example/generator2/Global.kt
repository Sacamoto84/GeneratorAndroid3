package com.example.generator2

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.generator2.*
import com.example.generator2.console.Console2
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
class Global() : ViewModel() {

    var liveData: vmLiveData = vmLiveData()
    var script: Script = Script(liveData)
    var utils: UtilsKT = UtilsKT()

    var contextActivity: Context? = null
    var componentActivity: ComponentActivity? = null

    var patchDocument = ""
    var patchCarrier = "$patchDocument/Carrier/"
    var patchMod = "$patchDocument/Mod/"

    var itemlistCarrier: ArrayList<itemList> = ArrayList() //Создать список
    var itemlistAM: ArrayList<itemList> = ArrayList() //Создать список
    var itemlistFM: ArrayList<itemList> = ArrayList() //Создать список

    val onoffconfig: ConfigOnOff = ConfigOnOff()
    val onoffconfig1: ConfigOnOff = ConfigOnOff()

    //Пути для отрисовки нижнего меню
    var bottomBarRoute = mutableStateOf(bottomBarEnum.HOME)

    //lateinit var script : Script

    //val script = Script()

    lateinit var keyboard: ScriptKeyboard
    val consoleLog = Console2()

    fun init() {

        utils.context = contextActivity!!

        val file = contextActivity!!.getExternalFilesDir("") //Создать если нет папку generator2
        contextActivity!!.getExternalFilesDir("/Carrier")
        contextActivity!!.getExternalFilesDir("/Mod")
        contextActivity!!.getExternalFilesDir("/Script")
        patchDocument = file!!.toString()
        patchCarrier = "$patchDocument/Carrier/"
        patchMod = "$patchDocument/Mod/"

        Utils.patchDocument = patchDocument
        Utils.patchCarrier = patchCarrier
        Utils.patchMod = patchMod

        try {
            AssetCopier(contextActivity) //.withFileScanning()
                .copy("Carrier", File(patchCarrier))
        } catch (e: IOException) {
            Log.d("Init", e.printStackTrace().toString())
        }

        try {
            AssetCopier(contextActivity) //.withFileScanning()
                .copy("Mod", File(patchMod))
        } catch (e: IOException) {
            Log.d("Init", e.printStackTrace().toString())
        }

        keyboard = ScriptKeyboard(script)
        consoleLog.println("")
    }

    fun observe() {

        liveData.ch1_EN.observeForever { ch1_EN ->
            Log.d("observeForever", "onClick")
            PlaybackEngine.CH_EN(0, ch1_EN!!)
        }

        liveData.ch2_EN.observeForever { ch2_EN ->
            Log.d("observeForever", "onClick")
            PlaybackEngine.CH_EN(1, ch2_EN!!)
        }

        liveData.ch1_AM_EN.observeForever { ch1_AM_EN ->
            Log.d("observeForever", "onClick")
            PlaybackEngine.CH_AM_EN(0, ch1_AM_EN!!)
        }

        liveData.ch2_AM_EN.observeForever { ch2_AM_EN ->
            Log.d("observeForever", "onClick")
            PlaybackEngine.CH_AM_EN(1, ch2_AM_EN!!)
        }

        liveData.ch1_FM_EN.observeForever { ch1_FM_EN ->
            Log.d("observeForever", "onClick")
            PlaybackEngine.CH_FM_EN(0, ch1_FM_EN!!)
        }

        liveData.ch2_FM_EN.observeForever { ch2_FM_EN ->
            Log.d("observeForever", "onClick")
            PlaybackEngine.CH_FM_EN(1, ch2_FM_EN!!)
        }


        liveData.ch1_Carrier_Fr.observeForever { ch1_Carrier_Fr ->
            Log.d("observeForever", "onClick")
            val fr = ch1_Carrier_Fr.toInt().toFloat()
            PlaybackEngine.CH_Carrier_fr(0, fr)
        }

        liveData.ch2_Carrier_Fr.observeForever { ch2_Carrier_Fr ->
            Log.d("observeForever", "onClick")
            val fr = ch2_Carrier_Fr.toInt().toFloat()
            PlaybackEngine.CH_Carrier_fr(1, fr)
        }

        liveData.ch1_AM_Fr.observeForever { ch1_AM_Fr ->
            Log.d("observeForever", "onClick")
            PlaybackEngine.CH_AM_fr(0, ch1_AM_Fr!!)
        }

        liveData.ch2_AM_Fr.observeForever { ch2_AM_Fr ->
            Log.d("observeForever", "onClick")
            PlaybackEngine.CH_AM_fr(1, ch2_AM_Fr!!)
        }

        liveData.ch1_FM_Base.observeForever { ch1_FM_Base ->
            val fr = ch1_FM_Base.toInt().toFloat()
            Log.d("observeForever", "onClick")
            PlaybackEngine.CH_FM_Base(0, fr)
        }

        liveData.ch2_FM_Base.observeForever { ch2_FM_Base ->
            val fr = ch2_FM_Base.toInt().toFloat()
            Log.d("observeForever", "onClick")
            PlaybackEngine.CH_FM_Base(1, fr)
        }

        liveData.ch1_FM_Dev.observeForever { ch1_FM_Dev ->
            val fr = ch1_FM_Dev.toInt().toFloat()
            Log.d("observeForever", "onClick")
            PlaybackEngine.CH_FM_Dev(0, fr)
        }

        liveData.ch2_FM_Dev.observeForever { ch2_FM_Dev ->
            val fr = ch2_FM_Dev.toInt().toFloat()
            Log.d("observeForever", "onClick")
            PlaybackEngine.CH_FM_Dev(1, fr)
        }

        liveData.ch1_FM_Fr.observeForever { ch1_FM_Fr ->
            Log.d("observeForever", "onClick")
            PlaybackEngine.CH_FM_fr(0, ch1_FM_Fr!!)
        }

        liveData.ch2_FM_Fr.observeForever { ch2_FM_Fr ->
            Log.d("observeForever", "onClick")
            PlaybackEngine.CH_FM_fr(1, ch2_FM_Fr!!)
        }

        liveData.ch1_Carrier_Filename.observeForever { name ->
            Log.d("observeForever", "onClick")
            Utils.Spinner_Send_Buffer(
                "CH0", "CR", name
            ) //Читае м отсылаем массив
        }

        liveData.ch2_Carrier_Filename.observeForever { name ->
            Log.d("observeForever", "onClick")
            Utils.Spinner_Send_Buffer(
                "CH1", "CR", name
            ) //Читае м отсылаем массив
        }

        liveData.ch1_AM_Filename.observeForever { name ->
            Log.d("observeForever", "onClick")
            Utils.Spinner_Send_Buffer(
                "CH0", "AM", name
            ) //Читае м отсылаем массив
        }

        liveData.ch2_AM_Filename.observeForever { name ->
            Log.d("observeForever", "onClick")
            Utils.Spinner_Send_Buffer(
                "CH1", "AM", name
            ) //Читае м отсылаем массив
        }

        liveData.ch1_FM_Filename.observeForever { name ->
            Log.d("observeForever", "onClick")
            Utils.Spinner_Send_Buffer(
                "CH0", "FM", name
            ) //Читае м отсылаем массив
        }

        liveData.ch2_FM_Filename.observeForever { name ->
            Log.d("observeForever", "onClick")
            Utils.Spinner_Send_Buffer(
                "CH1", "FM", name
            ) //Читае м отсылаем массив
        }

    }

    fun sendAlltoGen() {
        PlaybackEngine.CH_EN(0, liveData.ch1_EN.value!!)
        PlaybackEngine.CH_EN(1, liveData.ch2_EN.value!!)
        PlaybackEngine.CH_AM_EN(
            0, liveData.ch1_AM_EN.value!!
        )
        PlaybackEngine.CH_AM_EN(
            1, liveData.ch2_AM_EN.value!!
        )
        PlaybackEngine.CH_FM_EN(
            0, liveData.ch1_FM_EN.value!!
        )
        PlaybackEngine.CH_FM_EN(
            1, liveData.ch2_FM_EN.value!!
        )
        PlaybackEngine.CH_Carrier_fr(
            0, liveData.ch1_Carrier_Fr.value!!
        )
        PlaybackEngine.CH_Carrier_fr(
            1, liveData.ch2_Carrier_Fr.value!!
        )
        PlaybackEngine.CH_AM_fr(
            0, liveData.ch1_AM_Fr.value!!
        )
        PlaybackEngine.CH_AM_fr(
            1, liveData.ch2_AM_Fr.value!!
        )
        PlaybackEngine.CH_FM_Base(
            0, liveData.ch1_FM_Base.value!!
        )
        PlaybackEngine.CH_FM_Base(
            1, liveData.ch2_FM_Base.value!!
        )
        PlaybackEngine.CH_FM_Dev(
            0, liveData.ch1_FM_Dev.value!!
        )
        PlaybackEngine.CH_FM_Dev(
            1, liveData.ch2_FM_Dev.value!!
        )
        PlaybackEngine.CH_FM_fr(
            0, liveData.ch1_FM_Fr.value!!
        )
        PlaybackEngine.CH_FM_fr(
            1, liveData.ch2_FM_Fr.value!!
        )

        Utils.Spinner_Send_Buffer(
            "CH0", "CR", liveData.ch1_Carrier_Filename.value
        )
        Utils.Spinner_Send_Buffer(
            "CH0", "AM", liveData.ch1_AM_Filename.value
        )
        Utils.Spinner_Send_Buffer(
            "CH0", "FM", liveData.ch1_FM_Filename.value
        )

        Utils.Spinner_Send_Buffer(
            "CH1", "CR", liveData.ch2_Carrier_Filename.value
        )
        Utils.Spinner_Send_Buffer(
            "CH1", "AM", liveData.ch2_AM_Filename.value
        )
        Utils.Spinner_Send_Buffer(
            "CH1", "FM", liveData.ch2_FM_Filename.value
        )

    }


    ////////////////////////////////////////////////////////


    fun launchScriptScope() {
        viewModelScope.launch {
            scriptRun()
        }
    }


    private suspend fun scriptRun() = withContext(Dispatchers.Default) {
        while (true) {
            script.run()
            delay(10)
        }
    }



    /**
     * Сохранить текущий скрипт в файл
     */

    fun saveListToScript(name: String) {
        utils.saveListToScriptFile(script.list, name)
    }


}