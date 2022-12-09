package com.example.generator2.screens.firebase

import com.example.generator2.vm.Global
import com.google.firebase.storage.ktx.storageMetadata
import java.util.Date


//Сохранить бекап по пути /user/UID/backup.zip
fun saveBackupToFirebase(global: Global) { //gs://test-e538d.appspot.com/
    val storageRef = global.storage.reference //Корневая папка
    val uid = global.firebase.auth.uid
    if ((uid == "") || (uid == null)) return

    val s = storageRef.child("/user/${uid}/backup.zip")
        .putFile(global.backup.getURIBackup(), storageMetadata { contentType = "application/zip" })
        .addOnCompleteListener {
            println("backup addOnCompleteListener..ok")
        }.addOnFailureListener {
            println("backup addOnFailureListener:$it")
        }

}

//Сохранить бекап по пути /user/UID/backup.zip
fun readSizeBackupFromFirebase(global: Global) { //gs://test-e538d.appspot.com/
    val storageRef = global.storage.reference //Корневая папка

    val uid = global.firebase.auth.uid
    if ((uid == "") || (uid == null)) return

    val s = storageRef.child("/user/${uid}/backup.zip")
        .metadata
        .addOnCompleteListener {
            println("metadata addOnCompleteListener..ok")
            println("metadata sizeBytes "+it.result.sizeBytes)
            println("metadata updatedTimeMillis  "+it.result.updatedTimeMillis)
            println("metadata creationTimeMillis "+it.result.creationTimeMillis)
            println("metadata name "+it.result.name)

            println("metadata Date "+Date(it.result.updatedTimeMillis).toString())


        }.addOnFailureListener {
            println("metadata addOnFailureListener:$it")
        }

}