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








