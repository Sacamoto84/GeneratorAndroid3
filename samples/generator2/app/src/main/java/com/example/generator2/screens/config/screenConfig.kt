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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import colorLightBackground
import colorLightBackground2
import com.example.generator2.R
import com.example.generator2.screens.firebase.readMetaBackupFromFirebase
import com.example.generator2.screens.firebase.saveBackupToFirebase
import com.example.generator2.vm.Global
import kotlinx.coroutines.delay


//Стиль для строчек с информацийе
val caption: TextStyle = TextStyle(
    fontWeight = FontWeight.Normal,
    fontSize = 14.sp,
    letterSpacing = 0.4.sp,
    fontFamily = FontFamily(Font(R.font.jetbrains))
)

val modifierGreenButton = Modifier.padding(8.dp).fillMaxWidth().height(40.dp)

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnrememberedMutableState")
@Composable
fun ScreenConfig(
    navController: NavHostController, global: Global
) {

    readMetaBackupFromFirebase(global)

    var LVolume by remember { mutableStateOf(0.55F) }
    var RVolume by remember { mutableStateOf(0.65F) }

    //Выводится информация по бекап файлу
    var backupMessage by mutableStateOf("1")

    val strMetadata by global.strMetadata.collectAsState()
    val strMetadataError by global.strMetadataError.collectAsState()
    val progressMetadata by global.progressMetadata.collectAsState()
    LaunchedEffect(key1 = true, block = {

        while (true) {

            val f = global.backup.getMetadataBackup()
            backupMessage = if (f.size == -1L) "backup.zip not found"
            else {
                "Time file creation : ${f.str}\nsize: ${f.size} byte"
            }

            delay(2000)

        }

    })



    Scaffold(backgroundColor = colorLightBackground) {


        Column(
            Modifier.fillMaxSize().background(colorLightBackground2)
                .verticalScroll(rememberScrollState())
        ) {

            Divider()
            Text(
                text = "Volume",
                color = Color(0xFFFFC300),
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                modifier = Modifier.fillMaxWidth()
            )

            Slider(value = LVolume, onValueChange = { LVolume = it })
            Slider(value = RVolume, onValueChange = { RVolume = it })

            Button(onClick = { /*TODO*/ }) {
                Text("Save")
            }

            Divider()

            Text(
                text = "Sensetive",
                color = Color(0xFFFFC300),
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = "Carrier Frequency",
                color = Color(0xFFFFC300),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Slider(value = LVolume, onValueChange = { LVolume = it })

            Text(
                text = "AM Frequency",
                color = Color(0xFFFFC300),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Slider(value = LVolume, onValueChange = { LVolume = it })

            Text(
                text = "FM Frequency",
                color = Color(0xFFFFC300),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Slider(value = LVolume, onValueChange = { LVolume = it })

            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
            Divider()
            Text(
                text = "BackUp",
                color = Color(0xFFFFC300),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )

            Text(
                modifier = Modifier.fillMaxWidth().padding(start = 8.dp),
                textAlign = TextAlign.Left,
                style = caption,
                text = backupMessage,
                color = Color.LightGray
            )

            Row(Modifier.fillMaxWidth()) {

                Green_button(
                    modifierGreenButton.weight(1f), onClick = {
                        global.backup.createBackupZipFileToCache()
                        val f = global.backup.getMetadataBackup()
                        backupMessage = if (f.size == -1L) "backup.zip not found"
                        else "Time file creation : ${f.str}\nsize: ${f.size} byte"
                    }, label = "Create Local Backup"
                )

                Green_button(
                    modifierGreenButton.weight(1f),
                    onClick = { global.backup.unZipFileFromCache() },
                    label = "UnZip Local Backup"
                )

            }


            //При авторизации, есть токен
            if ((global.firebase.uid != "") && (global.firebase.uid != "null")) {

                //Индикатор работы
                if (progressMetadata) {
                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                } else Spacer(modifier = Modifier.height(4.dp))

                if (strMetadataError.isNotEmpty())
                //Вывод информации об ошибках
                Text(
                    text = strMetadataError,
                    maxLines = 7,
                    color = Color.Red,
                    modifier = Modifier.fillMaxWidth().padding(start = 8.dp),
                    textAlign = TextAlign.Left,
                    style = caption,
                ) //Информация о файле в облаках

                if (strMetadata.isNotEmpty()){
                Text(
                    text = strMetadata,
                    maxLines = 7,
                    color = Color.LightGray,
                    modifier = Modifier.fillMaxWidth().padding(start = 8.dp),
                    textAlign = TextAlign.Left,
                    style = caption,
                )}
                else
                {
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

                        Green_button(
                            modifierGreenButton,
                            onClick = { saveBackupToFirebase(global) },
                            label = "Save to Cloud"
                        )

                        Green_button(
                            modifierGreenButton,
                            onClick = {  },
                            label = "Read from Cloud"
                        )

                    }

                    Green_button_refresh(modifierGreenButton.weight(1f), onClick = { readMetaBackupFromFirebase(global) })

                }

            }


            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////


            Divider()

            Text(
                text = "Authorization",
                color = Color(0xFFFFC300),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            ) //Авторизация
            global.firebase.LoginScreen(viewModel = global)

            Divider()
            Text(
                text = "Version 2.0.3",
                color = Color(0xFFFFC300),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Divider()
            Spacer(modifier = Modifier.height(400.dp))

        }


    }


}


@Composable
private fun Green_button_refresh(
    modifier: Modifier, onClick: () -> Unit
) {

    Button(
        modifier = modifier,
        content = {

            Icon(
                tint = Color.White,
                painter = painterResource(id = R.drawable.refresh),
                contentDescription = null,
            )

        }, onClick = onClick, colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(0xFF4CAF50), disabledBackgroundColor = Color(0xFF262726)
        )
    )

}

@Composable
private fun Green_button(
    modifier: Modifier, onClick: () -> Unit, label: String = ""
) {

    Button(
        modifier = modifier,
        content = {

            Text(
                text = label,
                color = Color.White, //fontSize = 18.sp
            )

        }, onClick = onClick, colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(0xFF4CAF50), disabledBackgroundColor = Color(0xFF262726)
        )
    )

}


