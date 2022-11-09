package com.example.generator2.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.generator2.Global
import com.example.generator2.scripting.StateCommandScript
import com.example.generator2.scripting.ui.ConsoleLogDraw
import com.example.generator2.scripting.ui.RegisterViewDraw
import com.example.generator2.scripting.ui.ScriptTable
import libs.modifier.recomposeHighlighter


//Основной экран для скриптов
@Composable
fun ScreenScriptCommon() {


    //Global.script.LoadScriptToConsoleView()

    Column(
        //Modifier
          //  .recomposeHighlighter()
        //.background(Color.Cyan)
    ) {

        Box(Modifier.weight(1f))
        {
            ScriptTable()
        }

        if (Global.script.state != StateCommandScript.ISEDITTING) {

            //Блок регистров
            RegisterViewDraw()
        }

    }
}








