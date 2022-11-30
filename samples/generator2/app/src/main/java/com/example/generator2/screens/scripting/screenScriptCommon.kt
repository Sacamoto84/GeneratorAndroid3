package com.example.generator2.screens.scripting

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.generator2.vm.Global
import com.example.generator2.vm.StateCommandScript
import com.example.generator2.screens.scripting.ui.RegisterViewDraw
import com.example.generator2.screens.ui.ScriptTable


//Основной экран для скриптов
@Composable
fun ScreenScriptCommon(navController: NavHostController, global: Global) {

    Column(
        //Modifier
          //  .recomposeHighlighter()
        //.background(Color.Cyan)
    ) {

        Box(Modifier.weight(1f))
        {
            ScriptTable(global = global)
        }

        if (global.script.state != StateCommandScript.ISEDITTING) {

            //Блок регистров
            RegisterViewDraw(global = global)
        }

    }
}








