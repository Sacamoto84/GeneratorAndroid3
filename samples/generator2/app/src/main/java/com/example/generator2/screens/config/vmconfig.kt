package com.example.generator2.screens.config

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ActivityInfo
import android.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import com.example.generator2.R
import com.example.generator2.backup.Backup
import com.example.generator2.screens.firebase.Firebas
import com.yagmurerdogan.toasticlib.Toastic
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject



//Сообщения по поводу метаданных бекапа
val strMetadataError      = MutableStateFlow("")     //Текст ошибок для мета данных
val strMetadata           = MutableStateFlow("")     //Текст сообщения для мета данных
val progressMetadata      = MutableStateFlow(false)  //Текст сообщения для мета данных

@SuppressLint("StaticFieldLeak")
@HiltViewModel
class VMConfig @Inject constructor(
    @ApplicationContext
    private val context: Context,
    var backup: Backup,
    var firebase : Firebas
) : ViewModel(){


    //var LVolume by  mutableStateOf(0.55F)
    //var RVolume by  mutableStateOf(0.65F)

    fun saveINIVolume() = backup.saveINIVolume()
    fun saveINIConstrain() = backup.saveINIConstrain()

    fun toastSaveVolume()
    {
        Toastic.toastic(
            context = context,
            message = "Volume Saved",
            duration = Toastic.LENGTH_SHORT,
            type = Toastic.SUCCESS,
            //isIconAnimated = true,
            customIcon = R.drawable.info3,
            font = R.font.jetbrains,
            customBackground = R.drawable.toast_bg,
            textColor = Color.WHITE,
            //customIconAnimation = androidx.appcompat.R.anim.abc_slide_out_bottom
        ).show()
    }



    fun toastText(str : String)
    {
        Toastic.toastic(
            context = context,
            message = str,
            duration = Toastic.LENGTH_SHORT,
            type = Toastic.SUCCESS,
            //isIconAnimated = true,
            //customIcon = R.drawable.info3,
            font = R.font.jetbrains,
            customBackground = R.drawable.toast_bg,
            textColor = Color.WHITE,
            //customIconAnimation = androidx.appcompat.R.anim.abc_slide_out_bottom
        ).show()
    }



}