package com.example.generator2.backup

import android.content.Context
import com.example.generator2.data.LiveConstrain
import com.example.generator2.data.LiveData
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import java.io.File

data class DataJsonVolume(
    @SerializedName("volume0")
    var volume0: Float = 0f,
    @SerializedName("volume1")
    var volume1: Float = 0f
)

data class DataJsonConstrain(
  //  @SerializedName("sensetingSliderCr")
    var sensetingSliderCr: Float = 0f,

  //  @SerializedName("sensetingSliderFmDev")
    var sensetingSliderFmDev: Float = 0f,

  //  @SerializedName("sensetingSliderFmBase")
    var sensetingSliderFmBase: Float = 0f,

  //  @SerializedName("sensetingSliderAmFm")
    var sensetingSliderAmFm: Float = 0f,

  //  @SerializedName("minCR")
    var minCR: Float = 0f,

   // @SerializedName("maxCR")
    var maxCR: Float = 0f,

 //  @SerializedName("minModAmFm")
    var minModAmFm: Float = 0f,

  //  @SerializedName("maxModAmFm")
    var maxModAmFm: Float = 0f,

  //  @SerializedName("minFMBase")
    var minFMBase: Float = 0f,

  //  @SerializedName("maxFMBase")
    var maxFMBase: Float = 0f,

  // @SerializedName("minFMDev")
    var minFMDev: Float = 0f,

   // @SerializedName("maxFMDev")
    var maxFMDev: Float = 0f,
)

data class DataJsonConfig(

   // @SerializedName("ch1_EN")
    var ch1_EN: Boolean = false,
   // @SerializedName("ch1_Carrier_Filename")
    var ch1_Carrier_Filename: String = "Sine",
   // @SerializedName("ch1_Carrier_Fr")
    var ch1_Carrier_Fr: Float = 400.0f,
   // @SerializedName("ch1_AM_EN")
    var ch1_AM_EN: Boolean = false,
   // @SerializedName("ch1_AM_Filename")
    var ch1_AM_Filename: String = "09_Ramp",
   // @SerializedName("ch1_AM_Fr")
    var ch1_AM_Fr: Float = 8.7f,
   // @SerializedName("ch1_FM_EN")
    var ch1_FM_EN: Boolean = false,
   // @SerializedName("ch1_FM_Filename")
    var ch1_FM_Filename: String = "06_CHIRP",
   // @SerializedName("ch1_FM_Base")
    var ch1_FM_Base: Float = 2500f,
   // @SerializedName("ch1_FM_Dev")
    var ch1_FM_Dev: Float = 1100f,
   // @SerializedName("ch1_FM_Fr")
    var ch1_FM_Fr: Float = 5.1f,

   // @SerializedName("ch2_EN")
    var ch2_EN: Boolean = false,
   // @SerializedName("ch2_Carrier_Filename")
    var ch2_Carrier_Filename: String = "Sine",
   // @SerializedName("ch2_Carrier_Fr")
    var ch2_Carrier_Fr: Float = 2000.0f,
   // @SerializedName("ch2_AM_EN")
    var ch2_AM_EN: Boolean = false,
   // @SerializedName("ch2_AM_Filename")
    var ch2_AM_Filename: String = "09_Ramp",
   // @SerializedName("ch2_AM_Fr")
    var ch2_AM_Fr: Float = 8.7f,
   // @SerializedName("ch2_FM_EN")
    var ch2_FM_EN: Boolean = false,
   // @SerializedName("ch2_FM_Filename")
    var ch2_FM_Filename: String = "06_CHIRP",
   // @SerializedName("ch2_FM_Base")
    var ch2_FM_Base: Float = 2500f,
   // @SerializedName("ch2_FM_Dev")
    var ch2_FM_Dev: Float = 1100f,
   // @SerializedName("ch2_FM_Fr")
    var ch2_FM_Fr: Float = 5.1f,

    //@SerializedName("mono")
    var mono: Boolean = false,
    //@SerializedName("invert")
    var invert: Boolean = false,

    //@SerializedName("shuffle")
    var shuffle: Boolean = false,

    //@SerializedName("enL")
    var enL: Boolean = true,

    //@SerializedName("enR")
    var enR: Boolean = true,

    )


class Json(val context: Context) {
    //Адресс файла текущей конфигурации
    val iniCurrentConfig = context.getExternalFilesDir("/Config").toString() + "/CurrentConfig.json"
    val iniCurrentVolume = context.getExternalFilesDir("/Config").toString() + "/Volume.json"
    val iniCurrentConstrain = context.getExternalFilesDir("/Config").toString() + "/Constrain.json"

    /////////////////////////
    fun readJsonVolume() {
        print("readJsonVolume..")
        if (!File(iniCurrentVolume).exists()) {
            saveJsonVolume()
            return
        }
        val s = File(iniCurrentVolume).readText()
        val dataJsonVolume = Gson().fromJson(s, DataJsonVolume::class.java)
        LiveData.volume0.value = dataJsonVolume.volume0
        LiveData.volume1.value = dataJsonVolume.volume1
        println("ok")
    }

    fun saveJsonVolume() {
        print("saveJsonVolume..")
        val dataJsonVolume = DataJsonVolume(LiveData.volume0.value, LiveData.volume0.value)
        val jsonString = Gson().toJson(dataJsonVolume, DataJsonVolume::class.java )  // json string
        File(iniCurrentVolume).writeText(jsonString)
        println("ok")
    }
    /////////////////////////

    fun saveJsonConstrain() {

        print("saveJsonConstrain..")
        val dataJsonConstrain = DataJsonConstrain(
            sensetingSliderCr = LiveConstrain.sensetingSliderCr.value,
            sensetingSliderFmDev = LiveConstrain.sensetingSliderFmDev.value,
            sensetingSliderFmBase = LiveConstrain.sensetingSliderFmBase.value,
            sensetingSliderAmFm = LiveConstrain.sensetingSliderAmFm.value,
            minCR = LiveConstrain.minCR.value,
            maxCR = LiveConstrain.maxCR.value,
            minModAmFm = LiveConstrain.minModAmFm.value,
            maxModAmFm = LiveConstrain.maxModAmFm.value,
            minFMBase = LiveConstrain.minFMBase.value,
            maxFMBase = LiveConstrain.maxFMBase.value,
            minFMDev = LiveConstrain.minFMDev.value,
            maxFMDev = LiveConstrain.maxFMDev.value,
        )

        val jsonString = Gson().toJson(dataJsonConstrain)  // json string
        File(iniCurrentConstrain).writeText(jsonString)
        println("ok")
    }

    fun readJsonConstrain() {
        print("readJsonConstrain..")
        if (!File(iniCurrentConstrain).exists()) {
            saveJsonConstrain()
            return
        }

        val s = File(iniCurrentConstrain).readText()
        val dataJsonVolume = Gson().fromJson(s, DataJsonConstrain::class.java)

        LiveConstrain.sensetingSliderCr.value = dataJsonVolume.sensetingSliderCr
        LiveConstrain.sensetingSliderFmDev.value = dataJsonVolume.sensetingSliderFmDev
        LiveConstrain.sensetingSliderFmBase.value = dataJsonVolume.sensetingSliderFmBase
        LiveConstrain.sensetingSliderAmFm.value = dataJsonVolume.sensetingSliderAmFm
        LiveConstrain.minCR.value = dataJsonVolume.minCR
        LiveConstrain.maxCR.value = dataJsonVolume.maxCR
        LiveConstrain.minModAmFm.value = dataJsonVolume.minModAmFm
        LiveConstrain.maxModAmFm.value = dataJsonVolume.maxModAmFm
        LiveConstrain.minFMBase.value = dataJsonVolume.minFMBase
        LiveConstrain.maxFMBase.value = dataJsonVolume.maxFMBase
        LiveConstrain.minFMBase.value = dataJsonVolume.minFMDev
        LiveConstrain.maxFMDev.value = dataJsonVolume.maxFMDev

        println("ok")
    }
    /////////////////////////

    fun saveJsonConfig() {

        print("saveJsonConfig..")

        val dataJsonConstrain = DataJsonConfig(

            ch1_EN = LiveData.ch1_EN.value,
            ch1_Carrier_Filename = LiveData.ch1_Carrier_Filename.value,
            ch1_Carrier_Fr = LiveData.ch1_Carrier_Fr.value,
            ch1_AM_EN = LiveData.ch1_AM_EN.value,
            ch1_AM_Filename = LiveData.ch1_AM_Filename.value,
            ch1_AM_Fr = LiveData.ch1_AM_Fr.value,
            ch1_FM_EN = LiveData.ch1_FM_EN.value,
            ch1_FM_Filename = LiveData.ch1_FM_Filename.value,
            ch1_FM_Base = LiveData.ch1_FM_Base.value,
            ch1_FM_Dev = LiveData.ch1_FM_Dev.value,
            ch1_FM_Fr = LiveData.ch1_FM_Fr.value,

            ch2_EN = LiveData.ch2_EN.value,
            ch2_Carrier_Filename = LiveData.ch2_Carrier_Filename.value,
            ch2_Carrier_Fr = LiveData.ch2_Carrier_Fr.value,
            ch2_AM_EN = LiveData.ch2_AM_EN.value,
            ch2_AM_Filename = LiveData.ch2_AM_Filename.value,
            ch2_AM_Fr = LiveData.ch2_AM_Fr.value,
            ch2_FM_EN = LiveData.ch2_FM_EN.value,
            ch2_FM_Filename = LiveData.ch2_FM_Filename.value,
            ch2_FM_Base = LiveData.ch2_FM_Base.value,
            ch2_FM_Dev = LiveData.ch2_FM_Dev.value,
            ch2_FM_Fr = LiveData.ch2_FM_Fr.value,

            mono = LiveData.mono.value,
            invert = LiveData.invert.value,
            shuffle = LiveData.shuffle.value,
            enL = LiveData.enL.value,
            enR = LiveData.enR.value,
        )
        val jsonString = Gson().toJson(dataJsonConstrain)  // json string
        File(iniCurrentConfig).writeText(jsonString)

        println("ok")
    }

    fun readJsonConfig() {

        print("readJsonConfig..")

        if (!File(iniCurrentConfig).exists()) {
            saveJsonConfig()
            return
        }

        val s = File(iniCurrentConfig).readText()
        val dataJsonVolume = Gson().fromJson(s, DataJsonConfig::class.java)

        LiveData.ch1_EN.value = dataJsonVolume.ch1_EN
        LiveData.ch1_Carrier_Filename.value = dataJsonVolume.ch1_Carrier_Filename
        LiveData.ch1_Carrier_Fr.value = dataJsonVolume.ch1_Carrier_Fr
        LiveData.ch1_AM_EN.value = dataJsonVolume.ch1_AM_EN
        LiveData.ch1_AM_Filename.value = dataJsonVolume.ch1_AM_Filename
        LiveData.ch1_AM_Fr.value = dataJsonVolume.ch1_AM_Fr
        LiveData.ch1_FM_EN.value = dataJsonVolume.ch1_FM_EN
        LiveData.ch1_FM_Filename.value = dataJsonVolume.ch1_FM_Filename
        LiveData.ch1_FM_Base.value = dataJsonVolume.ch1_FM_Base
        LiveData.ch1_FM_Dev.value = dataJsonVolume.ch1_FM_Dev
        LiveData.ch1_FM_Fr.value = dataJsonVolume.ch1_FM_Fr

        LiveData.ch2_EN.value = dataJsonVolume.ch2_EN
        LiveData.ch2_Carrier_Filename.value = dataJsonVolume.ch2_Carrier_Filename
        LiveData.ch2_Carrier_Fr.value = dataJsonVolume.ch2_Carrier_Fr
        LiveData.ch2_AM_EN.value = dataJsonVolume.ch2_AM_EN
        LiveData.ch2_AM_Filename.value = dataJsonVolume.ch2_AM_Filename
        LiveData.ch2_AM_Fr.value = dataJsonVolume.ch2_AM_Fr
        LiveData.ch2_FM_EN.value = dataJsonVolume.ch2_FM_EN
        LiveData.ch2_FM_Filename.value = dataJsonVolume.ch2_FM_Filename
        LiveData.ch2_FM_Base.value = dataJsonVolume.ch2_FM_Base
        LiveData.ch2_FM_Dev.value = dataJsonVolume.ch2_FM_Dev
        LiveData.ch2_FM_Fr.value = dataJsonVolume.ch2_FM_Fr

        LiveData.mono.value = dataJsonVolume.mono
        LiveData.invert.value = dataJsonVolume.invert
        LiveData.shuffle.value = dataJsonVolume.shuffle

        LiveData.enL.value = dataJsonVolume.enL
        LiveData.enR.value = dataJsonVolume.enR

        println("ok")
    }
}












