package com.example.generator2.screens.config

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.generator2.R
import com.example.generator2.screens.config.DefScreenConfig.caption
import com.example.generator2.screens.config.molecule.ConfigLoginScreen
import com.example.generator2.theme.colorLightBackground
import com.example.generator2.data.LiveConstrain
import com.example.generator2.data.LiveData
import com.example.generator2.screens.config.molecule.ConfigConstrain
import com.example.generator2.screens.config.vm.VMConfig
import com.example.generator2.screens.config.vm.progressMetadata
import com.example.generator2.screens.config.vm.strMetadata
import com.example.generator2.screens.config.vm.strMetadataError
import kotlinx.coroutines.delay


val modifierGreenButton = Modifier
    .padding(8.dp)
    .fillMaxWidth()
    .height(40.dp)

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnrememberedMutableState")
@Composable
fun ScreenConfig(
    navController: NavHostController, vm: VMConfig = hiltViewModel()
) {

    vm.firebase.auth.uid?.let { readMetaBackupFromFirebase(it) }

    //Выводится информация по бекап файлу
    var backupMessage by remember { mutableStateOf("1") }

    val strMetadata by strMetadata.collectAsState()
    val strMetadataError by strMetadataError.collectAsState()
    val progressMetadata by progressMetadata.collectAsState()

    LaunchedEffect(key1 = true, block = {
        while (true) {
            val f = vm.backup.getMetadataBackup()
            backupMessage = if (f.size == -1L) "backup.zip not found"
            else {
                "Time file creation : ${f.str}\nsize: ${f.size} byte"
            }
            //println(backupMessage)
            delay(2000)
        }
    })

    val focusManager = LocalFocusManager.current

    Scaffold(backgroundColor = colorLightBackground, bottomBar = { BottomBar(navController) } )
    {

        Column(
            Modifier
                .fillMaxSize().background(colorLightBackground).verticalScroll(rememberScrollState()).pointerInput(Unit)
                { detectTapGestures(onTap = {focusManager.clearFocus() })}
        ) {

            Divider()
            Config_header("Version 2.0.3")
            Divider()

            Divider() ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
            Divider()
            Config_header("BackUp")

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp),
                textAlign = TextAlign.Left,
                style = caption,
                text = backupMessage,
                color = Color.LightGray
            )

            Row(Modifier.fillMaxWidth()) {
                Config_Green_button(
                    modifierGreenButton.weight(1f), onClick = {
                        vm.backup.createBackupZipFileToCache()
                        val f = vm.backup.getMetadataBackup()
                        backupMessage = if (f.size == -1L) "backup.zip not found"
                        else "Time file creation : ${f.str}\nsize: ${f.size} byte"
                    }, label = "Create Local"
                )
                Config_Green_button(modifierGreenButton.weight(1f), onClick = { vm.backup.unZipFileFromCache() }, label = "UnZip Local" )
            }

            //При авторизации, есть токен
            if ((vm.firebase.uid != "") && (vm.firebase.uid != "null")) {

                //Индикатор работы
                if (progressMetadata) {
                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                } else Spacer(modifier = Modifier.height(4.dp))

                if (strMetadataError.isNotEmpty()) //Вывод информации об ошибках
                    Text(
                        text = strMetadataError,
                        maxLines = 7,
                        color = Color.Red,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp),
                        textAlign = TextAlign.Left,
                        style = caption,
                    ) //Информация о файле в облаках

                if (strMetadata.isNotEmpty()) {
                    Text(
                        text = strMetadata,
                        maxLines = 7,
                        color = Color.LightGray,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp),
                        textAlign = TextAlign.Left,
                        style = caption,
                    )
                } else {
                    Text(
                        text = " \n \n ",
                        maxLines = 7,
                        color = Color.LightGray,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp),
                        textAlign = TextAlign.Left,
                        style = caption,
                    )
                }

                Row() {
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .weight(1f)) {
                        Config_Green_button(
                            modifierGreenButton, onClick = {
                                vm.firebase.auth.uid?.let { saveBackupToFirebase(it, vm.backup) }
                            }, label = "Save to Cloud"
                        )
                        Config_Green_button(
                            modifierGreenButton, onClick = { }, label = "Read from Cloud"
                        )
                    }
                    Config_Green_button_refresh(modifierGreenButton.weight(1f), onClick = {
                        vm.firebase.auth.uid?.let { readMetaBackupFromFirebase(it) }
                    })
                }
            } ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
            Divider()
            Config_header("Authorization") //Авторизация
            ConfigLoginScreen(vm = vm)
            Divider()
            Config_header("Version 2.0.3")
            Divider()

            Divider()

            ConfigConstrain(vm)

            Row(modifier = Modifier.fillMaxWidth()) {

                val value0 = LiveData.volume0.collectAsState()
                editConfig(Modifier.weight(1f), "Volume CH0 0..1", value = value0, min = 0f, max = 1f,
                    onDone = {
                        LiveData.volume0.value = it
                        vm.toastSaveVolume()
                        vm.saveVolume()
                        })

                val value1 = LiveData.volume1.collectAsState()
                editConfig(Modifier.weight(1f), "Volume CH1 0..1", value = value1, min = 0f, max = 1f,
                    onDone = {
                        LiveData.volume1.value = it
                        vm.toastSaveVolume()
                        vm.saveVolume()
                    } )

            }

            Spacer(modifier = Modifier.height(400.dp))

        }

    }

}

@Composable
fun Config_header(str: String) {
    Text(
        text = str,
        color = Color(0xFFFFC300),
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun BottomBar(navController : NavHostController) {
    BottomAppBar(
        backgroundColor = colorLightBackground,
        contentColor = Color.White,
    ) {
        //Кнопка назад
        IconButton(modifier = Modifier.testTag("buttonM4ScriptGoBack"),
            onClick = { navController.popBackStack() }) {
            Icon(painter = painterResource(R.drawable.back4), contentDescription = null)
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}

