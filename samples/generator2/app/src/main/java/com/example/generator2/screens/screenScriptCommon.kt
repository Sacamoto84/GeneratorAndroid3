package com.example.generator2.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.generator2.Global
import libs.modifier.recomposeHighlighter


//Основной экран для скриптов
@Composable
fun ScreenScriptCommon() {


    Global.script.LoadScriptToConsoleView()

    Column(
        Modifier
            .recomposeHighlighter()
        //.background(Color.Cyan)
    ) {

        Box(Modifier.weight(1f))
        {
            Global.script.ScriptTable(index = 2)
        }


        //Консоль Логов
        Global.script.ConsoleLogDraw(Modifier.weight(0.1f))
        //Блок регистров
        Global.script.RegisterViewDraw()
    }
}








