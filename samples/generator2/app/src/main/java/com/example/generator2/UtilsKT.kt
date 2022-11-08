package com.example.generator2

import android.content.Context
import androidx.compose.ui.platform.LocalContext

fun Float.format(digits: Int) = "%.${digits}f".format(this)


/**
 * Получить список файлов по пути
 *
 *  @param dir Путь к сканируемой папке Mod
 *  @return Текстовый список имен файлов
 */
fun filesInDirToList(dir: String = ""): List<String> {
    ///storage/emulated/0/Android/data/com.example.generator2/files
    val pathDocuments = Global.contextActivity!!.getExternalFilesDir(dir)

    val r : MutableList<String> = mutableListOf()
    if (pathDocuments != null) {
        pathDocuments.list()?.let { r.addAll(it) }
    }
    return r
}

fun filesInDirToList(context : Context , dir: String = ""): List<String> {
    ///storage/emulated/0/Android/data/com.example.generator2/files
    val pathDocuments = context.getExternalFilesDir(dir)

    val r : MutableList<String> = mutableListOf()
    if (pathDocuments != null) {
        pathDocuments.list()?.let { r.addAll(it) }
    }
    return r
}







fun test() {

    val l = filesInDirToList("/Mod")

}
