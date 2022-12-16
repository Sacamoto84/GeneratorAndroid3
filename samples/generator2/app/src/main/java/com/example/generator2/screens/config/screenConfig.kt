package com.example.generator2.screens.config

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.generator2.screens.config.ui.Config_Green_button
import com.example.generator2.screens.config.ui.Config_Green_button_refresh
import com.example.generator2.screens.firebase.ConfigLoginScreen
import com.example.generator2.theme.colorLightBackground
import com.example.generator2.theme.colorLightBackground2
import com.example.generator2.vm.LiveData
import kotlinx.coroutines.delay


val modifierGreenButton = Modifier.padding(8.dp).fillMaxWidth().height(40.dp)

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

    val value0 by LiveData.volume0.collectAsState()
    val value1 by LiveData.volume1.collectAsState()

    LaunchedEffect(key1 = true, block = {
        while (true) {
            val f = vm.backup.getMetadataBackup()
            backupMessage = if (f.size == -1L) "backup.zip not found"
            else {
                "Time file creation : ${f.str}\nsize: ${f.size} byte"
            }
            println(backupMessage)
            delay(2000)
        }
    })

    Scaffold(backgroundColor = colorLightBackground) {

        Column(
            Modifier.fillMaxSize().background(colorLightBackground2)
                .verticalScroll(rememberScrollState())
        ) {


            Divider()

            Row(modifier = Modifier.fillMaxWidth()) {

                OutlinedTextField(
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = Color.White,
                        focusedBorderColor = Color.LightGray,
                        focusedLabelColor = Color.White
                    ),
                    modifier = Modifier.fillMaxWidth().weight(1f).padding(8.dp),
                    value = value0.toString(),
                    label = {
                        Text(text = "Volume CH0 0..1", fontSize = 16.sp)
                    },
                    onValueChange = {
                        LiveData.volume0.value = it.toFloat()
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done, keyboardType = KeyboardType.Text
                    ),
                    textStyle = TextStyle(fontSize = 18.sp)
                )

                OutlinedTextField(
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = Color.White,
                        focusedBorderColor = Color.LightGray,
                        focusedLabelColor = Color.White
                    ),
                    modifier = Modifier.fillMaxWidth().weight(1f).padding(8.dp),
                    value = value1.toString(),
                    label = {
                        Text(text = "Volume CH1 0..1", fontSize = 16.sp)
                    },
                    onValueChange = {
                        LiveData.volume1.value = it.toFloat()
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done, keyboardType = KeyboardType.Text
                    ),
                    textStyle = TextStyle(fontSize = 18.sp)
                )

            }

            Button(modifier = Modifier.padding(start = 8.dp, bottom = 2.dp, end = 8.dp)
                .fillMaxWidth(1f),

                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF4CAF50), disabledBackgroundColor = Color(0xFF262726)
                ),

                onClick = {
                    vm.toastSaveVolume() //Сохранить громкоcть
                    vm.saveINIVolume()
                }) {
                Text("Save Volume", color = Color.White)
            }










            Divider() ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
            Divider()
            Config_header("BackUp")

            Text(
                modifier = Modifier.fillMaxWidth().padding(start = 8.dp),
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
                    }, label = "Create Local Backup"
                )
                Config_Green_button(
                    modifierGreenButton.weight(1f),
                    onClick = { vm.backup.unZipFileFromCache() },
                    label = "UnZip Local Backup"
                )
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
                        modifier = Modifier.fillMaxWidth().padding(start = 8.dp),
                        textAlign = TextAlign.Left,
                        style = caption,
                    ) //Информация о файле в облаках

                if (strMetadata.isNotEmpty()) {
                    Text(
                        text = strMetadata,
                        maxLines = 7,
                        color = Color.LightGray,
                        modifier = Modifier.fillMaxWidth().padding(start = 8.dp),
                        textAlign = TextAlign.Left,
                        style = caption,
                    )
                } else {
                    Text(
                        text = " \n \n ",
                        maxLines = 7,
                        color = Color.LightGray,
                        modifier = Modifier.fillMaxWidth().padding(start = 8.dp),
                        textAlign = TextAlign.Left,
                        style = caption,
                    )
                }

                Row() {
                    Column(Modifier.fillMaxWidth().weight(1f)) {
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
            ConfigLoginScreen(viewModel = vm)
            Divider()
            Config_header("Version 2.0.3")
            Divider()
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

