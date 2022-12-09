package com.example.generator2.screens.config

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import colorLightBackground
import colorLightBackground2
import com.example.generator2.vm.Global
import com.google.android.gms.auth.api.identity.BeginSignInRequest

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ScreenConfig(
    navController: NavHostController, global: Global
) {

    var LVolume by remember { mutableStateOf(0.55F) }
    var RVolume by remember { mutableStateOf(0.65F) }



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



            Divider()

            Text(
                text = "Authorization",
                color = Color(0xFFFFC300),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )

            global.firebase.LoginScreen(viewModel = global)

            Divider()
            Text(
                text = "Version 2.0.3",
                color = Color(0xFFFFC300),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )


        }


    }


}