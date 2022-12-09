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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import colorLightBackground
import colorLightBackground2
import com.example.generator2.vm.Global
import kotlinx.coroutines.delay
import java.util.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnrememberedMutableState")
@Composable
fun ScreenConfig(
    navController: NavHostController, global: Global
) {

    var LVolume by remember { mutableStateOf(0.55F) }
    var RVolume by remember { mutableStateOf(0.65F) }

    //Выводится информация по бекап файлу
    var backupMessage by mutableStateOf("1")

    LaunchedEffect(key1 = true, block = {

       while (true){

           val f = global.backup.getMetadataBackup()
           backupMessage = if (f.size == -1L) "backup.zip not found"
           else "backup.zip  size: ${f.size/1024}kb  Time: ${f.str}"

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
                style = MaterialTheme.typography.caption,
                text = backupMessage,
                color = Color.LightGray
            )

            Row(Modifier.fillMaxWidth()) {
                Button(modifier = Modifier.padding(8.dp).fillMaxWidth().height(40.dp).weight(1f),

                    content = {
                        Text(
                            text = "Create Local Backup",
                            color = Color.White,
                            //fontSize = 18.sp
                        )
                    },
                    onClick = {
                        global.backup.createBackupZipFileToCache()
                        val f = global.backup.getMetadataBackup()
                        backupMessage = if (f.size == -1L) "backup.zip not found"
                        else "backup.zip  size: ${f.size/1024}kb  Time: ${f.str}"
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFF4CAF50),
                        disabledBackgroundColor = Color(0xFF262726)
                    )
                )

                Button(modifier = Modifier.padding(8.dp).fillMaxWidth().height(40.dp).weight(1f),

                    content = {
                        Text(
                            text = "UnZip Local Backup",
                            color = Color.White,
                            //fontSize = 18.sp
                        )
                    },
                    onClick = {
                        global.backup.unZipFileFromCache()
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFF4CAF50),
                        disabledBackgroundColor = Color(0xFF262726)
                    )
                )

            }

            if ((global.firebase.uid != "") && (global.firebase.uid != "null"))
            {
            Row() {

                Button(modifier = Modifier.padding(8.dp).fillMaxWidth().height(40.dp).weight(1f),

                    content = {
                        Text(
                            text = "UnZip Local Backup",
                            color = Color.White, //fontSize = 18.sp
                        )
                    }, onClick = {


                    }, colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFF4CAF50),
                        disabledBackgroundColor = Color(0xFF262726)
                    )
                )

                Button(modifier = Modifier.padding(8.dp).fillMaxWidth().height(40.dp).weight(1f),

                    content = {
                        Text(
                            text = "UnZip Local Backup",
                            color = Color.White, //fontSize = 18.sp
                        )
                    }, onClick = {


                    }, colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFF4CAF50),
                        disabledBackgroundColor = Color(0xFF262726)
                    )
                )

            }

            }


            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////





            Divider()

            Text(
                text = "Authorization",
                color = Color(0xFFFFC300),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )
            //Авторизация
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