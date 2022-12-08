package com.example.generator2.backup

import android.content.Context
import com.example.generator2.BuildConfig
import com.example.generator2.storage.AppFileManager
import java.io.File

class Backup( val context: Context) {

    //https://github.com/arnab-kundu/Storage
    val appFileManager = AppFileManager(BuildConfig.APPLICATION_ID)

    //Сохранить /files->/cache/backup.zip
    fun createBackupZipFileToCache()
    {
        //Всю папку с данными
        val srcFolderPath = context.getExternalFilesDir("").toString()
        context.externalCacheDir?.let { deleteDir(it) }
        //Сохранить в папку кеш
        val destFolderPath = context.externalCacheDir.toString()
        appFileManager.zipFiles(srcFolderPath, "$destFolderPath/backup.zip")
    }

    //Разорхивировать /cache/backup.zip->/files
    fun unZipFileFromCache()
    {
        val zipFile= context.externalCacheDir.toString()+"/backup.zip"
        val extractLocationPath = context.getExternalFilesDir("").toString().substringBeforeLast('/')
        appFileManager.unZipFile(
            zipFilePath = zipFile,
            extractLocationPath = extractLocationPath
        )
    }

    fun deleteDir(dir: File): Boolean {
        if (dir.isDirectory) {
            for (child in dir.listFiles()) {
                val success = deleteDir(child)
                if (!success) {
                    return false
                }
            }
        }
        return dir.delete()
    }

}