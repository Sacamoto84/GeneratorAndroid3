package com.example.generator2.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.generator2.Global
import com.example.generator2.console.Console2
import com.example.generator2.recomposeHighlighterOneLine
import com.example.generator2.scripting.scriptCore
import libs.modifier.recomposeHighlighter
import kotlin.math.absoluteValue

val consoleLog = Console2()

//Основной экран для скриптов
@Composable
fun ScreenScriptCommon() {

    val consoleUp = Console2()

    consoleUp.println("Up")
    consoleUp.println("Up1")
    consoleUp.println("2")
    consoleUp.println("Up3")
    consoleUp.println("Up4")
    consoleUp.println("Up5")
    consoleUp.println("Up6")
    consoleUp.println("Up6")
    consoleUp.println("Up6")
    consoleUp.println("Up6")
    consoleUp.println("Up6")
    consoleUp.println("Up6")
    consoleUp.println("Up6")
    consoleUp.println("Up6")
    consoleUp.println("Up6")
    consoleUp.println("Up6")
    consoleUp.println("Up6")
    consoleUp.println("Up6")

    consoleLog.println("Down1")
    consoleLog.println("Down2")
    consoleLog.println("Down3")
    consoleLog.println("Down4")

    Global.script_pc.observeForever { pc ->
        consoleUp.SelectLine.value = pc
        println("Global.script_pc.observeForever pc= $pc")
    }

    consoleUp.isCheckedUselineVisible.value = true
    consoleLog.isCheckedUselineVisible.value = true

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

                Text(
                    "Логи",
                    color = Color.White,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                consoleLog.Draw(
                    Modifier
                        .padding(4.dp)
                )
            }
        }



        Box(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                //.background(Color.Red)
                //.border(width = 2.dp, color = Color.White, shape = RoundedCornerShape(8.dp))
                .height(100.dp)
        ){

            Column(Modifier.recomposeHighlighterOneLine()) {
                Row() {
                    Text(text = "F0[${Global.script.F2[0]}]", color = Color.White)
                    Text(text = "F1[${Global.script.F2[1]}]", color = Color.White)

                }

            }



        }



    }


}








