package com.example.generator2.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.generator2.Global
import libs.modifier.recomposeHighlighter


//Основной экран для скриптов
@Composable
fun ScreenScriptCommon() {


    Global.script.LoadScriptToConsoleView()

    Column(Modifier.recomposeHighlighter()) {

        Global.script.ConsoleViewDraw(Modifier.weight(1f))

        //Консоль Логов
        Global.script.ConsoleLogDraw(Modifier.weight(1f))

        Global.script.RegisterViewDraw()
    }
}








