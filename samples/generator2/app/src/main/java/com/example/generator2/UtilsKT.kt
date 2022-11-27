package com.example.generator2

import android.content.Context
import java.io.File
import java.io.IOException

fun Float.format(digits: Int) = "%.${digits}f".format(this)

public class UtilsKT () {

    public lateinit var context: Context

    /**
     * Получить список файлов по пути
     *
     *  @param dir Путь к сканируемой папке Mod
     *  @return Текстовый список имен файлов
     */
    fun filesInDirToList( //context: Context,
        dir: String = ""
    ): List<String> { ///storage/emulated/0/Android/data/com.example.generator2/files
        val pathDocuments = context.getExternalFilesDir(dir)

        val r: MutableList<String> = mutableListOf()
        if (pathDocuments != null) {
            pathDocuments.list()?.let { r.addAll(it) }
        }
        return r
    }


    /**
     * Сохранить list String в файл /Script/name.sk
     */
    fun saveListToScriptFile(
        list: List<String>, name: String
    ) { ///storage/emulated/0/Android/data/com.example.generator2/files
        //val pathDocuments =
        //    Global.contextActivity!!.getExternalFilesDir("/Script")!!.absolutePath.toString() + "/${name}.sk"

        val pathDocuments =
            context.getExternalFilesDir("/Script")!!.absolutePath.toString() + "/${name}.sk"

        var str = "" //val m = list.toMutableList()
        val m = list.toMutableList()
        m[0] = name
        for (i in m.indices) {
            str += "$i:${m[i]}\n"
        }
        File(pathDocuments).writeText(str)
    }


    /**
     *  Прочитать файл скрипта и записать его в список
     */
    fun readScriptFileToList(name: String): List<String> { ///storage/emulated/0/Android/data/com.example.generator2/files
        val pathDocuments =
            context.getExternalFilesDir("/Script")!!.absolutePath.toString() + "/${name}.sk"

        val list = File(pathDocuments).readText().split("\n").toMutableList()

        if (list.last() == "") list.removeLast()

        for (i in list.indices) {
            val l = list[i].split(":")
            list[i] = l[1]
        }

        return list.toList()
    }

    fun deleteScriptFile(name: String) {
        val pathDocuments =
            context.getExternalFilesDir("/Script")!!.absolutePath.toString() + "/${name}.sk"
        File(pathDocuments).delete()
    }

    fun renameScriptFile(nameSource: String, nameDescination: String) {
        try {
            val l = readScriptFileToList(nameSource)
            deleteScriptFile(nameSource)
            saveListToScriptFile(l, nameDescination)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }


}
