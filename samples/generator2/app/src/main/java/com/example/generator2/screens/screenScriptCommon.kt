package com.example.generator2.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.generator2.console.Console2

val consoleLog = Console2()

//Основной экран для скриптов
@Composable
fun ScreenScriptCommon() {

    val consoleUp = Console2()

    consoleUp.println("Up")

    consoleLog.println("Down")

    Column() {

        Box(
            modifier = Modifier
                .padding(8.dp)
                .background(Color.Green)
                .border(width = 2.dp, color = Color.White, shape = RoundedCornerShape(8.dp))
                .weight(1f)
        )
        {
            consoleUp.Draw()
        }

        Box(
            modifier = Modifier
                .padding(8.dp)
                .background(Color.Red)
                .border(width = 2.dp, color = Color.White, shape = RoundedCornerShape(8.dp))
                .weight(1f)
        )
        {
            Column() {

                    Text("Логи", color = Color.White, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)


                consoleLog.Draw(
                    Modifier
                        .padding(4.dp)
                        
                )

            }


        }


    }


}