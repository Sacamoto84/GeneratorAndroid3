package com.example.generator2.backup

import android.content.Context
import android.net.Uri
import com.example.generator2.BuildConfig
import com.example.generator2.backup.storage.AppFileManager
import com.example.generator2.vm.LiveConstrain
import com.example.generator2.vm.LiveData
import kotlinx.coroutines.flow.update
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.*

data class metadataBackup(var size: Long, var datetime: Date, var str: String)

class Backup(val context: Context) {

    //Получить путь до Файла Бекап
    fun getPathToBackup(): String = context.externalCacheDir.toString() + "/backup.zip"

    //Получить Uri файла бекап
    fun getURIBackup(): Uri = Uri.fromFile(File(getPathToBackup()))

    fun getMetadataBackup(): metadataBackup {
        val r = metadataBackup(-1, Date(0), "")
        val f = File(getPathToBackup())
        if (f.exists()) {
            r.size = f.length() //Размер файла
            r.datetime = Date(f.lastModified()) //Время последней модификации
            r.str =
                (1900 + r.datetime.year - 2000).toString() + "/" + r.datetime.month.toString() + "/" + r.datetime.date.toString() + " " + r.datetime.hours.toString() + ":" + r.datetime.minutes.toString() + ":" + r.datetime.seconds.toString()
        }
        return r
    }

    //https://github.com/arnab-kundu/Storage
    private val appFileManager = AppFileManager(BuildConfig.APPLICATION_ID)

    //Сохранить /files->/cache/backup.zip
    fun createBackupZipFileToCache() { //Всю папку с данными
        val srcFolderPath = context.getExternalFilesDir("").toString()
        context.externalCacheDir?.let { deleteDir(it) } //Сохранить в папку кеш
        val destFolderPath = context.externalCacheDir.toString()
        appFileManager.zipFiles(srcFolderPath, "$destFolderPath/backup.zip")
    }

    //Разорхивировать /cache/backup.zip->/files
    fun unZipFileFromCache() {
        val extractLocationPath =
            context.getExternalFilesDir("").toString().substringBeforeLast('/')
        appFileManager.unZipFile(
            zipFilePath = getPathToBackup(), extractLocationPath = extractLocationPath
        )
    }

    private fun deleteDir(dir: File): Boolean {
        if (dir.isDirectory) {
            for (child in dir.listFiles()!!) {
                val success = deleteDir(child)
                if (!success) {
                    return false
                }
            }
        }
        return dir.delete()
    }

///////////////////////
    //Адресс файла текущей конфигурации
    val iniCurrentConfig = context.getExternalFilesDir("/Config").toString() + "/CurrentConfig.ini"
    val iniCurrentVolume = context.getExternalFilesDir("/Config").toString() + "/Volume.ini"
    val iniCurrentConstrain = context.getExternalFilesDir("/Config").toString() + "/Constrain.ini"
///////////////////////
    val iniVolume = Ini()
    fun readINIVolume() {
        if (!File(iniCurrentVolume).exists())
        {
            saveINIVolume()
            return
        }
        iniVolume.load(FileInputStream(iniCurrentVolume))
        val volume0 = iniVolume.getValue("current", "volume0")?.toString()
        if (volume0 != null) { LiveData.volume0.update { volume0.toFloat() } }
        val volume1 = iniVolume.getValue("current", "volume1")?.toString()
        if (volume1 != null) { LiveData.volume1.update { volume1.toFloat() } }
    }
    fun saveINIVolume() {
        iniVolume.putValue("current", "volume0", LiveData.volume0.value.toString())
        iniVolume.putValue("current", "volume1", LiveData.volume1.value.toString())
        iniVolume.store(FileOutputStream(iniCurrentVolume),"some comments at the top of the file");
    }
/////////////////////////////
    val iniConfig = Ini()
    fun readINIConfig() {
        if (!File(iniCurrentConfig).exists())
        {
            saveINIConfig()
            return
        }

        iniConfig.load(FileInputStream(iniCurrentConfig))

        val ch1EN = iniConfig.getValue("current", "ch1_EN")?.toString()
        if (ch1EN != null) {
            LiveData.ch1_EN.update { ch1EN.toBoolean() }
        }

        val ch2EN = iniConfig.getValue("current", "ch2_EN")?.toString()
        if (ch2EN != null) {
            LiveData.ch2_EN.update { ch2EN.toBoolean() }
        }

        val ch1CarrierFilename = iniConfig.getValue("current", "ch1_Carrier_Filename")?.toString()
        if (ch1CarrierFilename != null) {
            LiveData.ch1_Carrier_Filename.update { ch1CarrierFilename }
        }

        val ch2CarrierFilename = iniConfig.getValue("current", "ch2_Carrier_Filename")?.toString()
        if (ch2CarrierFilename != null) {
            LiveData.ch2_Carrier_Filename.update { ch2CarrierFilename }
        }

        val ch1CarrierFr = iniConfig.getValue("current", "ch1_Carrier_Fr")?.toString()
        if (ch1CarrierFr != null) {
            LiveData.ch1_Carrier_Fr.update { ch1CarrierFr.toFloat() }
        }

        val ch2CarrierFr = iniConfig.getValue("current", "ch2_Carrier_Fr")?.toString()
        if (ch2CarrierFr != null) {
            LiveData.ch2_Carrier_Fr.update { ch2CarrierFr.toFloat() }
        }

        val ch1AMEN = iniConfig.getValue("current", "ch1_AM_EN")?.toString()
        if (ch1AMEN != null) {
            LiveData.ch1_AM_EN.update { ch1AMEN.toBoolean() }
        }

        val ch2AMEN = iniConfig.getValue("current", "ch2_AM_EN")?.toString()
        if (ch2AMEN != null) {
            LiveData.ch2_AM_EN.update { ch2AMEN.toBoolean() }
        }

        val ch1AMFilename = iniConfig.getValue("current", "ch1_AM_Filename")?.toString()
        if (ch1AMFilename != null) {
            LiveData.ch1_AM_Filename.update { ch1AMFilename }
        }

        val ch2AMFilename = iniConfig.getValue("current", "ch2_AM_Filename")?.toString()
        if (ch2AMFilename != null) {
            LiveData.ch2_AM_Filename.update { ch2AMFilename }
        }

        val ch1AMFr = iniConfig.getValue("current", "ch1_AM_Fr")?.toString()
        if (ch1AMFr != null) {
            LiveData.ch1_AM_Fr.update { ch1AMFr.toFloat() }
        }

        val ch2AMFr = iniConfig.getValue("current", "ch2_AM_Fr")?.toString()
        if (ch2AMFr != null) {
            LiveData.ch2_AM_Fr.update { ch2AMFr.toFloat() }
        }

        val ch1FMEN = iniConfig.getValue("current", "ch1_FM_EN")?.toString()
        if (ch1FMEN != null) {
            LiveData.ch1_FM_EN.update { ch1FMEN.toBoolean() }
        }

        val ch2FMEN = iniConfig.getValue("current", "ch2_FM_EN")?.toString()
        if (ch2FMEN != null) {
            LiveData.ch2_FM_EN.update { ch2FMEN.toBoolean() }
        }

        val ch1FMFilename = iniConfig.getValue("current", "ch1_FM_Filename")?.toString()
        if (ch1FMFilename != null) {
            LiveData.ch1_AM_Filename.update { ch1FMFilename }
        }

        val ch2FMFilename = iniConfig.getValue("current", "ch2_FM_Filename")?.toString()
        if (ch2FMFilename != null) {
            LiveData.ch2_AM_Filename.update { ch2FMFilename }
        }

        val ch1FMBase = iniConfig.getValue("current", "ch1_FM_Base")?.toString()
        if (ch1FMBase != null) {
            LiveData.ch1_FM_Base.update { ch1FMBase.toFloat() }
        }

        val ch2FMBase = iniConfig.getValue("current", "ch2_FM_Base")?.toString()
        if (ch2FMBase != null) {
            LiveData.ch2_FM_Base.update { ch2FMBase.toFloat() }
        }

        val ch1FMDev = iniConfig.getValue("current", "ch1_FM_Dev")?.toString()
        if (ch1FMDev != null) {
            LiveData.ch1_FM_Dev.update { ch1FMDev.toFloat() }
        }

        val ch2FMDev = iniConfig.getValue("current", "ch2_FM_Dev")?.toString()
        if (ch2FMDev != null) {
            LiveData.ch2_FM_Dev.update { ch2FMDev.toFloat() }
        }

        val ch1FMFr = iniConfig.getValue("current", "ch1_FM_Fr")?.toString()
        if (ch1FMFr != null) {
            LiveData.ch1_FM_Fr.update { ch1FMFr.toFloat() }
        }

        val ch2FMFr = iniConfig.getValue("current", "ch2_FM_Fr")?.toString()
        if (ch2FMFr != null) {
            LiveData.ch2_FM_Fr.update { ch2FMFr.toFloat() }
        }

    }
    fun saveINIConfig() {

        iniConfig.putValue("current", "ch1_EN", LiveData.ch1_EN.value.toString())
        iniConfig.putValue("current", "ch2_EN", LiveData.ch2_EN.value.toString())

        iniConfig.putValue("current", "ch1_Carrier_Filename", LiveData.ch1_Carrier_Filename.value)
        iniConfig.putValue("current", "ch2_Carrier_Filename", LiveData.ch2_Carrier_Filename.value)

        iniConfig.putValue("current", "ch1_Carrier_Fr", LiveData.ch1_Carrier_Fr.value.toString())
        iniConfig.putValue("current", "ch2_Carrier_Fr", LiveData.ch2_Carrier_Fr.value.toString())

        iniConfig.putValue("current", "ch1_AM_EN", LiveData.ch1_AM_EN.value.toString())
        iniConfig.putValue("current", "ch2_AM_EN", LiveData.ch2_AM_EN.value.toString())

        iniConfig.putValue("current", "ch1_AM_Filename", LiveData.ch1_AM_Filename.value)
        iniConfig.putValue("current", "ch2_AM_Filename", LiveData.ch2_AM_Filename.value)

        iniConfig.putValue("current", "ch1_AM_Fr", LiveData.ch1_AM_Fr.value.toString())
        iniConfig.putValue("current", "ch2_AM_Fr", LiveData.ch2_AM_Fr.value.toString())

        iniConfig.putValue("current", "ch1_FM_EN", LiveData.ch1_FM_EN.value.toString())
        iniConfig.putValue("current", "ch2_FM_EN", LiveData.ch2_FM_EN.value.toString())

        iniConfig.putValue("current", "ch1_FM_Filename", LiveData.ch1_FM_Filename.value)
        iniConfig.putValue("current", "ch2_FM_Filename", LiveData.ch2_FM_Filename.value)

        iniConfig.putValue("current", "ch1_FM_Base", LiveData.ch1_FM_Base.value.toString())
        iniConfig.putValue("current", "ch2_FM_Base", LiveData.ch2_FM_Base.value.toString())

        iniConfig.putValue("current", "ch1_FM_Dev", LiveData.ch1_FM_Dev.value.toString())
        iniConfig.putValue("current", "ch2_FM_Dev", LiveData.ch2_FM_Dev.value.toString())

        iniConfig.putValue("current", "ch1_FM_Fr", LiveData.ch1_FM_Fr.value.toString())
        iniConfig.putValue("current", "ch2_FM_Fr", LiveData.ch2_FM_Fr.value.toString())

        iniConfig.store(FileOutputStream(iniCurrentConfig),"some comments at the top of the file");
    }
/////////////////////////////
    val iniConstrain = Ini()
    fun saveINIConstrain() {

        iniConstrain.putValue("current", "sensetingSliderCr", LiveConstrain.sensetingSliderCr.value.toString())
        iniConstrain.putValue("current", "sensetingSliderFmDev", LiveConstrain.sensetingSliderFmDev.value.toString())
        iniConstrain.putValue("current", "sensetingSliderFmBase", LiveConstrain.sensetingSliderFmBase.value.toString())
        iniConstrain.putValue("current", "sensetingSliderAmFm", LiveConstrain.sensetingSliderAmFm.value.toString())
        iniConstrain.putValue("current", "minCR", LiveConstrain.minCR.value.toString())
        iniConstrain.putValue("current", "maxCR", LiveConstrain.maxCR.value.toString())
        iniConstrain.putValue("current", "minModAmFm", LiveConstrain.minModAmFm.value.toString())
        iniConstrain.putValue("current", "maxModAmFm", LiveConstrain.maxModAmFm.value.toString())
        iniConstrain.putValue("current", "minFMBase", LiveConstrain.minFMBase.value.toString())
        iniConstrain.putValue("current", "maxFMBase", LiveConstrain.maxFMBase.value.toString())
        iniConstrain.putValue("current", "minFMDev", LiveConstrain.minFMDev.value.toString())
        iniConstrain.putValue("current", "maxFMDev", LiveConstrain.maxFMDev.value.toString())
        iniConstrain.store(FileOutputStream(iniCurrentConstrain),"some comments at the top of the file");
    }
    fun readINIConstrain() {
        if (!File(iniCurrentConstrain).exists())
        {
            saveINIConstrain()
            return
        }
        iniConstrain.load(FileInputStream(iniCurrentConstrain))

        val sensetingSliderCr = iniConstrain.getValue("current", "sensetingSliderCr")?.toString()
        if (sensetingSliderCr != null) { LiveConstrain.sensetingSliderCr.value = sensetingSliderCr.toFloat()}

        val sensetingSliderFmDev = iniConstrain.getValue("current", "sensetingSliderFmDev")?.toString()
        if (sensetingSliderFmDev != null) { LiveConstrain.sensetingSliderFmDev.value = sensetingSliderFmDev.toFloat()}

        val sensetingSliderFmBase = iniConstrain.getValue("current", "sensetingSliderFmBase")?.toString()
        if (sensetingSliderFmBase != null) { LiveConstrain.sensetingSliderFmBase.value = sensetingSliderFmBase.toFloat()}

        val sensetingSliderAmFm = iniConstrain.getValue("current", "sensetingSliderAmFm")?.toString()
        if (sensetingSliderAmFm != null) { LiveConstrain.sensetingSliderAmFm.value = sensetingSliderAmFm.toFloat()}

        val minCR = iniConstrain.getValue("current", "minCR")?.toString()
        if (minCR != null) { LiveConstrain.minCR.value = minCR.toFloat()}

        val maxCR = iniConstrain.getValue("current", "maxCR")?.toString()
        if (maxCR != null) { LiveConstrain.maxCR.value = maxCR.toFloat()}

        val minModAmFm = iniConstrain.getValue("current", "minModAmFm")?.toString()
        if (minModAmFm != null) { LiveConstrain.minModAmFm.value = minModAmFm.toFloat()}

        val maxModAmFm = iniConstrain.getValue("current", "maxModAmFm")?.toString()
        if (maxModAmFm != null) { LiveConstrain.maxModAmFm.value = maxModAmFm.toFloat()}

        val minFMBase = iniConstrain.getValue("current", "minFMBase")?.toString()
        if (minFMBase != null) { LiveConstrain.minFMBase.value = minFMBase.toFloat()}

        val maxFMBase = iniConstrain.getValue("current", "maxFMBase")?.toString()
        if (maxFMBase != null) { LiveConstrain.maxFMBase.value = maxFMBase.toFloat()}

        val minFMDev = iniConstrain.getValue("current", "minFMDev")?.toString()
        if (minFMDev != null) { LiveConstrain.minFMBase.value = minFMDev.toFloat()}

        val maxFMDev = iniConstrain.getValue("current", "maxFMDev")?.toString()
        if (maxFMDev != null) { LiveConstrain.maxFMDev.value = maxFMDev.toFloat()}

    }



}