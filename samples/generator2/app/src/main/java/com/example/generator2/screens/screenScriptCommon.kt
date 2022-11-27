package com.example.generator2.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.generator2.Global
import com.example.generator2.scripting.StateCommandScript
import com.example.generator2.scripting.ui.RegisterViewDraw
import com.example.generator2.scripting.ui.ScriptTable


//Основной экран для скриптов
@Composable
fun ScreenScriptCommon(global: Global = viewModel()) {


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

        if (global.script.state != StateCommandScript.ISEDITTING) {

            //Блок регистров
            RegisterViewDraw()
        }

    }
}








