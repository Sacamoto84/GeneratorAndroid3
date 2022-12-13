package com.example.generator2.screens.config

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import colorLightBackground
import colorLightBackground2
import com.example.generator2.R
import com.example.generator2.screens.firebase.ConfigLoginScreen
import kotlinx.coroutines.delay


//Стиль для строчек с информацийе
val caption: TextStyle = TextStyle(
    fontWeight = FontWeight.Normal,
    fontSize = 14.sp,
    letterSpacing = 0.4.sp,
    fontFamily = FontFamily(Font(R.font.jetbrains))
)

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
            Config_header("Volume")

            Slider(value = vm.LVolume, onValueChange = { vm.LVolume = it })
            Slider(value = vm.RVolume, onValueChange = { vm.RVolume = it })




            Divider()
            Config_header("Sensitive")
            Slider(value = vm.LVolume, onValueChange = { vm.LVolume = it })
            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
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
            }
            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
            Divider()
            Config_header("Authorization")
            //Авторизация
            ConfigLoginScreen(viewModel = vm)
            Divider()
            Config_header("Version 2.0.3")
            Divider()
            Spacer(modifier = Modifier.height(400.dp))


        }


    }


}

@Composable
fun Config_header(str: String)
{
    Text(
        text = str,
        color = Color(0xFFFFC300),
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth()
    )

}

