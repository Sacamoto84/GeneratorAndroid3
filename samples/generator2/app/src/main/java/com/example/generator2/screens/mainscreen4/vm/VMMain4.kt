package com.example.generator2.screens.mainscreen4.vm

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.generator2.*
import com.example.generator2.element.Console2
import com.example.generator2.util.Utils
import com.example.generator2.di.Hub
import com.example.generator2.data.LiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import flipagram.assetcopylib.AssetCopier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException
import javax.inject.Inject

//@SuppressLint("StaticFieldLeak")
@HiltViewModel
class VMMain4 @Inject constructor(
    @ApplicationContext contextActivity: Context,
    val hub : Hub
) : ViewModel(){

    val consoleLog = Console2()

    init {

        println("global init{}")

        //utils.context = contextActivity!!

        val file = contextActivity.getExternalFilesDir("") //Создать если нет папку generator2
        contextActivity.getExternalFilesDir("/Carrier")
        contextActivity.getExternalFilesDir("/Mod")
        contextActivity.getExternalFilesDir("/Script")

        val patchDocument = file!!.toString()
        val patchCarrier = "$patchDocument/Carrier/"
        val patchMod = "$patchDocument/Mod/"

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
            LiveData.itemlistCarrier.add(itemList(patchCarrier, arrFilesCarrier[i], 0))
        }
        val arrFilesMod: Array<String> = Utils.listFileInMod() //Получение списка файлов в папке Mod
        for (i in arrFilesMod.indices) {
            LiveData.itemlistAM.add(itemList(patchMod, arrFilesMod[i], 1))
            LiveData.itemlistFM.add(itemList(patchMod, arrFilesMod[i], 0))
        }

        print("sendAlltoGen()..")
        hub.audioDevice.sendAlltoGen()
        println("OK")
        print("unit5Load()..")
        hub.script.unit5Load() //Загрузить тест
        println("OK")
        print("launchScriptScope()..")
        launchScriptScope() //Запуск скриптового потока
        println("OK")
    }



    ////////////////////////////////////////////////////////
    private fun launchScriptScope() {

        println("global launchScriptScope()")

        viewModelScope.launch(Dispatchers.Default) {
            while (true) {
                hub.script.run()
                delay(10)
            }
        }

        viewModelScope.launch(Dispatchers.Main) {

            while (true) {
                if ( hub.audioDevice.playbackEngine.getNeedAllData() == 1) {
                    hub.audioDevice.playbackEngine.resetNeedAllData()
                    delay(200)
                    println("Global Отсылаем все данные")
                    hub.audioDevice.sendAlltoGen()
                    hub.audioDevice.getDeviceId()
                }
                delay(1000)
            }
        }

    }


    
    //    viewModelScope . launch (Dispatchers.Default) {
    //        _viewState.update { it.copy(title = "Новый заголовок") }
    //    }

    private fun observe() {
        println("observe()--------------------------------------------------------------")
        val dispatchers = Dispatchers.IO

        viewModelScope.launch(dispatchers) { LiveData.volume0.collect {hub.playbackEngine.setVolume(0, it) } }

        viewModelScope.launch(dispatchers) { LiveData.volume1.collect {hub.playbackEngine.setVolume(1, it) } }
        //
        viewModelScope.launch(dispatchers) { LiveData.ch1_EN.collect {
            if (LiveData.link.value)
                LiveData.ch2_EN.value = it
            hub.playbackEngine.setEN(0, it)
        }}

        viewModelScope.launch(dispatchers) { LiveData.ch2_EN.collect {  hub.playbackEngine.setEN(1, it)}}
        //
        viewModelScope.launch(dispatchers) { LiveData.ch1_AM_EN.collect {
            if (LiveData.link.value)
                LiveData.ch2_AM_EN.value = it
            hub.playbackEngine.setAM_EN(0, it)
        } }

        viewModelScope.launch(dispatchers) { LiveData.ch2_AM_EN.collect {  hub.playbackEngine.setAM_EN(1, it) } }
        //
        viewModelScope.launch(dispatchers) { LiveData.ch1_FM_EN.collect {
            if (LiveData.link.value)
                LiveData.ch2_FM_EN.value = it
            hub.playbackEngine.setFM_EN(0, it) } }

        viewModelScope.launch(dispatchers) { LiveData.ch2_FM_EN.collect {  hub.playbackEngine.setFM_EN(1, it) } }
        //
        viewModelScope.launch(dispatchers) { LiveData.ch1_Carrier_Fr.collect {
            if (LiveData.link.value)
                LiveData.ch2_Carrier_Fr.value = it
            hub.playbackEngine.setCarrier_fr(0, it.toInt().toFloat()) } }

        viewModelScope.launch(dispatchers) { LiveData.ch2_Carrier_Fr.collect {  hub.playbackEngine.setCarrier_fr(1, it.toInt().toFloat()) } }
        //
        viewModelScope.launch(dispatchers) { LiveData.ch1_AM_Fr.collect {
            if (LiveData.link.value)
                LiveData.ch2_AM_Fr.value = it
            hub.playbackEngine.setAM_fr(0, it) } }

        viewModelScope.launch(dispatchers) { LiveData.ch2_AM_Fr.collect {  hub.playbackEngine.setAM_fr(1, it) } }
        //
        viewModelScope.launch(dispatchers) { LiveData.ch1_FM_Base.collect {
            if (LiveData.link.value)
                LiveData.ch2_FM_Base.value = it
            hub.playbackEngine.setFM_Base(0, it.toInt().toFloat()) } }

        viewModelScope.launch(dispatchers) { LiveData.ch2_FM_Base.collect { hub.playbackEngine.setFM_Base(1, it.toInt().toFloat()  ) } }
        //
        viewModelScope.launch(dispatchers) { LiveData.ch1_FM_Dev.collect {
            if (LiveData.link.value)
                LiveData.ch2_FM_Dev.value = it
            hub.playbackEngine.setFM_Dev(0, it.toInt().toFloat()) } }

        viewModelScope.launch(dispatchers) { LiveData.ch2_FM_Dev.collect {  hub.playbackEngine.setFM_Dev(1, it.toInt().toFloat()) } }
        //
        viewModelScope.launch(dispatchers) { LiveData.ch1_FM_Fr.collect {
            if (LiveData.link.value)
                LiveData.ch2_FM_Fr.value = it
            hub.playbackEngine.setFM_fr(0, it) } }
        viewModelScope.launch(dispatchers) { LiveData.ch2_FM_Fr.collect {  hub.playbackEngine.setFM_fr(1, it) } }
        //
        viewModelScope.launch(dispatchers) { LiveData.ch1_Carrier_Filename.collect {
            if (LiveData.link.value)
                LiveData.ch2_Carrier_Filename.value = it
            hub.utils.Spinner_Send_Buffer("CH0", "CR", it) } }

        viewModelScope.launch(dispatchers) { LiveData.ch2_Carrier_Filename.collect { hub.utils.Spinner_Send_Buffer("CH1", "CR", it) } }
        //
        viewModelScope.launch(dispatchers) { LiveData.ch1_AM_Filename.collect {
            if (LiveData.link.value)
                LiveData.ch2_AM_Filename.value = it
            hub.utils.Spinner_Send_Buffer("CH0", "AM", it) } }

        viewModelScope.launch(dispatchers) { LiveData.ch2_AM_Filename.collect { hub.utils.Spinner_Send_Buffer("CH1", "AM", it) } }
        //
        viewModelScope.launch(dispatchers) { LiveData.ch1_FM_Filename.collect {
            if (LiveData.link.value)
                LiveData.ch2_FM_Filename.value = it
            hub.utils.Spinner_Send_Buffer("CH0", "FM", it) } }

        viewModelScope.launch(dispatchers) { LiveData.ch2_FM_Filename.collect { hub.utils.Spinner_Send_Buffer("CH1", "FM", it) } }
        //
        viewModelScope.launch(dispatchers) { LiveData.link.collect { linkAllOn(it) } }

        //Включение моно в самом драйвере Oboe, второй канал игнорируется
        viewModelScope.launch(dispatchers) { LiveData.mono.collect {
            hub.audioDevice.playbackEngine.setMono(it)
        } }

        viewModelScope.launch(dispatchers) { LiveData.invert.collect {
            hub.audioDevice.playbackEngine.setInvertPhase(it)
        } }

        viewModelScope.launch(dispatchers) { LiveData.enL.collect {
            hub.audioDevice.playbackEngine.setEnL(it)
        } }

        viewModelScope.launch(dispatchers) { LiveData.enR.collect {
            hub.audioDevice.playbackEngine.setEnR(it)
        } }

        viewModelScope.launch(dispatchers) { LiveData.shuffle.collect {
            hub.audioDevice.playbackEngine.setShuffle(it)
        } }

    }







}





@SuppressLint("SuspiciousIndentation")
fun linkAllOn(on : Boolean)
{
  if (!on) return
    LiveData.ch2_EN.update        { LiveData.ch1_EN.value }
    LiveData.ch2_AM_EN.update     { LiveData.ch1_AM_EN.value }
    LiveData.ch2_FM_EN.update     { LiveData.ch1_FM_EN.value }
    LiveData.ch2_Carrier_Fr.update{ LiveData.ch1_Carrier_Fr.value }
    LiveData.ch2_AM_Fr.update     { LiveData.ch1_AM_Fr.value }
    LiveData.ch2_FM_Base.update   { LiveData.ch1_FM_Base.value }
    LiveData.ch2_FM_Dev.update    { LiveData.ch1_FM_Dev.value }
    LiveData.ch2_FM_Fr.update     { LiveData.ch1_FM_Fr.value }
    LiveData.ch2_Carrier_Filename.update{ LiveData.ch1_Carrier_Filename.value }
    LiveData.ch2_AM_Filename.update     { LiveData.ch1_AM_Filename.value}
    LiveData.ch2_FM_Filename.update     { LiveData.ch1_FM_Filename.value}

}