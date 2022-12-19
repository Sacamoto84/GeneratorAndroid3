package com.example.generator2.backup

import android.content.Context
import com.example.generator2.vm.LiveData
import com.google.gson.Gson
import java.io.File

data class DataJsonVolume(var volume0: Float = 0f, var volume1: Float = 0f)


class Json(val context: Context) {
    //Адресс файла текущей конфигурации
    val iniCurrentConfig = context.getExternalFilesDir("/Config").toString() + "/CurrentConfig.json"
    val iniCurrentVolume = context.getExternalFilesDir("/Config").toString() + "/Volume.json"
    val iniCurrentConstrain = context.getExternalFilesDir("/Config").toString() + "/Constrain.json"

    /////////////////////////
    fun readJsonVolume() {
        if (!File(iniCurrentVolume).exists()) {
            saveJsonVolume()
            return
        }
        val s = File(iniCurrentVolume).readText()
        val dataJsonVolume = Gson().fromJson(s, DataJsonVolume::class.java)
        LiveData.volume0.value = dataJsonVolume.volume0
        LiveData.volume1.value = dataJsonVolume.volume1
    }

    fun saveJsonVolume() {
        val dataJsonVolume = DataJsonVolume(LiveData.volume0.value, LiveData.volume0.value)
        val jsonString = Gson().toJson(dataJsonVolume)  // json string
        File(iniCurrentVolume).writeText(jsonString)
    }
    /////////////////////////

}