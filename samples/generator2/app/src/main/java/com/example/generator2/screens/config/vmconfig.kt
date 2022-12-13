package com.example.generator2.screens.config

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.generator2.backup.Backup
import com.example.generator2.screens.firebase.Firebas
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

//Сообщения по поводу метаданных бекапа
val strMetadataError      = MutableStateFlow("")     //Текст ошибок для мета данных
val strMetadata           = MutableStateFlow("")     //Текст сообщения для мета данных
val progressMetadata      = MutableStateFlow(false)  //Текст сообщения для мета данных

object Status {


}

@HiltViewModel
class VMConfig @Inject constructor(
    @ApplicationContext contextActivity: Context,
    var backup: Backup,
    var firebase : Firebas



) : ViewModel(){

    var LVolume by  mutableStateOf(0.55F)
    var RVolume by  mutableStateOf(0.65F)

}