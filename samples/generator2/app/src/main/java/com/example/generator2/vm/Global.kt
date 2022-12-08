package com.example.generator2.vm

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.runtime.*
import androidx.core.math.MathUtils
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.generator2.*
import com.example.generator2.audio_device.AudioDevice
import com.example.generator2.PlaybackEngine
import com.example.generator2.backup.Backup
import com.example.generator2.console.Console2
import com.example.generator2.screens.scripting.ui.ScriptKeyboard
import com.example.generator2.storage.AppFileManager
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import flipagram.assetcopylib.AssetCopier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.IOException
import javax.inject.Inject

@SuppressLint("StaticFieldLeak")
@HiltViewModel
class Global @Inject constructor(


    @ApplicationContext contextActivity: Context,

     var utils: UtilsKT,
     var liveData: vmLiveData,
     var  script: Script,
     var keyboard: ScriptKeyboard,
     var playbackEngine: PlaybackEngine,
     var audioDevice: AudioDevice,
     var backup: Backup

) : ViewModel() {

//    @Inject
//    lateinit var utils: UtilsKT

    //var contextActivity: Context? = null
    //var componentActivity: ComponentActivity? = null

    var patchDocument = ""
    var patchCarrier = "$patchDocument/Carrier/"
    var patchMod = "$patchDocument/Mod/"

    var itemlistCarrier: ArrayList<itemList> = ArrayList() //Создать список
    var itemlistAM: ArrayList<itemList> = ArrayList() //Создать список
    var itemlistFM: ArrayList<itemList> = ArrayList() //Создать список

    val onoffconfig: ConfigOnOff = ConfigOnOff()
    val onoffconfig1: ConfigOnOff = ConfigOnOff()

    val consoleLog = Console2()

    init {


        println("global init{}")

        //utils.context = contextActivity!!

        val file = contextActivity.getExternalFilesDir("") //Создать если нет папку generator2
        contextActivity.getExternalFilesDir("/Carrier")
        contextActivity.getExternalFilesDir("/Mod")
        contextActivity.getExternalFilesDir("/Script")
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

        //keyboard = ScriptKeyboard(script)
        consoleLog.println("")
        print("observe()..")
        observe()
        println("OK")

        val arrFilesCarrier: Array<String> = Utils.listFileInCarrier() //Заполняем список
        for (i in arrFilesCarrier.indices) {
            itemlistCarrier.add(itemList(patchCarrier, arrFilesCarrier[i], 0))
        }
        val arrFilesMod: Array<String> = Utils.listFileInMod() //Получение списка файлов в папке Mod
        for (i in arrFilesMod.indices) {
            itemlistAM.add(itemList(patchMod, arrFilesMod[i], 1))
            itemlistFM.add(itemList(patchMod, arrFilesMod[i], 0))
        }

        print("sendAlltoGen()..")
        sendAlltoGen()
        println("OK")
        print("unit5Load()..")
        script.unit5Load() //Загрузить тест
        println("OK")
        print("launchScriptScope()..")
        launchScriptScope() //Запуск скриптового потока
        println("OK")
    }

    fun sendAlltoGen() {

        println("global sendAlltoGen()")

        audioDevice.playbackEngine.CH_EN(0, liveData.ch1_EN.value)
        audioDevice.playbackEngine.CH_EN(1, liveData.ch2_EN.value)
        audioDevice.playbackEngine.CH_AM_EN(
            0, liveData.ch1_AM_EN.value
        )
        audioDevice.playbackEngine.CH_AM_EN(
            1, liveData.ch2_AM_EN.value
        )
        audioDevice.playbackEngine.CH_FM_EN(
            0, liveData.ch1_FM_EN.value
        )
        audioDevice.playbackEngine.CH_FM_EN(
            1, liveData.ch2_FM_EN.value
        )
        audioDevice.playbackEngine.CH_Carrier_fr(
            0, liveData.ch1_Carrier_Fr.value
        )
        audioDevice.playbackEngine.CH_Carrier_fr(
            1, liveData.ch2_Carrier_Fr.value
        )
        audioDevice.playbackEngine.CH_AM_fr(
            0, liveData.ch1_AM_Fr.value
        )
        audioDevice.playbackEngine.CH_AM_fr(
            1, liveData.ch2_AM_Fr.value
        )
        audioDevice.playbackEngine.CH_FM_Base(
            0, liveData.ch1_FM_Base.value
        )
        audioDevice.playbackEngine.CH_FM_Base(
            1, liveData.ch2_FM_Base.value
        )
        audioDevice.playbackEngine.CH_FM_Dev(
            0, liveData.ch1_FM_Dev.value
        )
        audioDevice.playbackEngine.CH_FM_Dev(
            1, liveData.ch2_FM_Dev.value
        )
        audioDevice.playbackEngine.CH_FM_fr(
            0, liveData.ch1_FM_Fr.value
        )
        audioDevice.playbackEngine.CH_FM_fr(
            1, liveData.ch2_FM_Fr.value
        )

        utils.Spinner_Send_Buffer(
            "CH0", "CR", liveData.ch1_Carrier_Filename.value
        )
        utils.Spinner_Send_Buffer(
            "CH0", "AM", liveData.ch1_AM_Filename.value
        )
        utils.Spinner_Send_Buffer(
            "CH0", "FM", liveData.ch1_FM_Filename.value
        )

        utils.Spinner_Send_Buffer(
            "CH1", "CR", liveData.ch2_Carrier_Filename.value
        )
        utils.Spinner_Send_Buffer(
            "CH1", "AM", liveData.ch2_AM_Filename.value
        )
        utils.Spinner_Send_Buffer(
            "CH1", "FM", liveData.ch2_FM_Filename.value
        )

    }

    ////////////////////////////////////////////////////////
    fun launchScriptScope() {

        println("global launchScriptScope()")

        viewModelScope.launch(Dispatchers.Default) {
            while (true) {
                script.run()
                delay(10)
            }
        }

        viewModelScope.launch(Dispatchers.Main) {

            while (true) {
                if (audioDevice.playbackEngine.getNeedAllData() == 1) {
                    audioDevice.playbackEngine.resetNeedAllData()
                    delay(200)
                    println("Global Отсылаем все данные")
                    sendAlltoGen()
                    audioDevice.getDeviceId()
                }
                delay(1000)
            }

        }

    }


    /**
     * Сохранить текущий скрипт в файл
     */

    fun saveListToScript(name: String) {
        println("global saveListToScript()")
        utils.saveListToScriptFile(script.list, name)
    }


    //    viewModelScope . launch (Dispatchers.Default) {
    //        _viewState.update { it.copy(title = "Новый заголовок") }
    //    }


    private fun observe() {

        println("observe()--------------------------------------------------------------")

        val dispatchers = Dispatchers.IO

        viewModelScope.launch(dispatchers) {
            liveData.ch1_EN.collect { ch1_EN ->
                println("collect")
                playbackEngine.CH_EN(0, ch1_EN)
            }
        }

        viewModelScope.launch(dispatchers) {

            liveData.ch2_EN.collect { ch2_EN ->
                playbackEngine.CH_EN(1, ch2_EN)
            }
        }

        viewModelScope.launch(dispatchers) {
            liveData.ch1_AM_EN.collect { ch1_AM_EN ->
                playbackEngine.CH_AM_EN(0, ch1_AM_EN)
            }
        }

        viewModelScope.launch(dispatchers) {
            liveData.ch2_AM_EN.collect { ch2_AM_EN ->
                playbackEngine.CH_AM_EN(1, ch2_AM_EN)
            }
        }

        viewModelScope.launch(dispatchers) {
            liveData.ch1_FM_EN.collect { ch1_FM_EN -> //Log.d("observeForever", "onClick")
                playbackEngine.CH_FM_EN(0, ch1_FM_EN)
            }
        }

        viewModelScope.launch(dispatchers) {
            liveData.ch2_FM_EN.collect { ch2_FM_EN -> //Log.d("observeForever", "onClick")
                playbackEngine.CH_FM_EN(1, ch2_FM_EN!!)
            }
        }

        viewModelScope.launch(dispatchers) {
            liveData.ch1_Carrier_Fr.collect { ch1_Carrier_Fr ->
                val fr = ch1_Carrier_Fr.toInt().toFloat()
                playbackEngine.CH_Carrier_fr(0, fr)
            }
        }

        viewModelScope.launch(dispatchers) {
            liveData.ch2_Carrier_Fr.collect { ch2_Carrier_Fr -> //Log.d("observeForever", "onClick")
                val fr = ch2_Carrier_Fr.toInt().toFloat()
                playbackEngine.CH_Carrier_fr(1, fr)
            }
        }

        viewModelScope.launch(dispatchers) {
            liveData.ch1_AM_Fr.collect { ch1_AM_Fr -> //Log.d("observeForever", "onClick")
                playbackEngine.CH_AM_fr(0, ch1_AM_Fr)
            }
        }

        viewModelScope.launch(dispatchers) {
            liveData.ch2_AM_Fr.collect { ch2_AM_Fr -> //Log.d("observeForever", "onClick")
                playbackEngine.CH_AM_fr(1, ch2_AM_Fr)
            }
        }

        viewModelScope.launch(dispatchers) {
            liveData.ch1_FM_Base.collect { ch1_FM_Base ->
                val fr = ch1_FM_Base.toInt().toFloat()
                playbackEngine.CH_FM_Base(0, fr)
            }
        }

        viewModelScope.launch(dispatchers) {
            liveData.ch2_FM_Base.collect { ch2_FM_Base ->
                val fr = ch2_FM_Base.toInt().toFloat()
                playbackEngine.CH_FM_Base(1, fr)
            }
        }

        viewModelScope.launch(dispatchers) {
            liveData.ch1_FM_Dev.collect { ch1_FM_Dev ->
                val fr = ch1_FM_Dev.toInt().toFloat()
                playbackEngine.CH_FM_Dev(0, fr)
            }
        }

        viewModelScope.launch(dispatchers) {
            liveData.ch2_FM_Dev.collect { ch2_FM_Dev ->
                val fr = ch2_FM_Dev.toInt().toFloat()
                playbackEngine.CH_FM_Dev(1, fr)
            }
        }

        viewModelScope.launch(dispatchers) {
            liveData.ch1_FM_Fr.collect { ch1_FM_Fr ->
                playbackEngine.CH_FM_fr(0, ch1_FM_Fr)
            }
        }

        viewModelScope.launch(dispatchers) {
            liveData.ch2_FM_Fr.collect { ch2_FM_Fr ->
                playbackEngine.CH_FM_fr(1, ch2_FM_Fr)
            }
        }

        viewModelScope.launch(dispatchers) {
            liveData.ch1_Carrier_Filename.collect { name ->
                utils.Spinner_Send_Buffer(
                    "CH0", "CR", name
                ) //Читае м отсылаем массив
            }
        }

        viewModelScope.launch(dispatchers) {
            liveData.ch2_Carrier_Filename.collect { name ->
                utils.Spinner_Send_Buffer(
                    "CH1", "CR", name
                ) //Читае м отсылаем массив
            }
        }

        viewModelScope.launch(dispatchers) {
            liveData.ch1_AM_Filename.collect { name ->
                utils.Spinner_Send_Buffer(
                    "CH0", "AM", name
                ) //Читае м отсылаем массив
            }
        }

        viewModelScope.launch(dispatchers) {
            liveData.ch2_AM_Filename.collect { name ->
                utils.Spinner_Send_Buffer(
                    "CH1", "AM", name
                ) //Читае м отсылаем массив
            }
        }

        viewModelScope.launch(dispatchers) {
            liveData.ch1_FM_Filename.collect { name ->
                utils.Spinner_Send_Buffer(
                    "CH0", "FM", name
                ) //Читае м отсылаем массив
            }
        }

        viewModelScope.launch(dispatchers) {
            liveData.ch2_FM_Filename.collect { name ->
                utils.Spinner_Send_Buffer(
                    "CH1", "FM", name
                ) //Читае м отсылаем массив
            }
        }

    }

}