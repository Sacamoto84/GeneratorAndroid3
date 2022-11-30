package com.example.generator2.screens.scripting

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import colorLightBackground
import com.example.generator2.R
import com.example.generator2.vm.Global
import com.example.generator2.vm.StateCommandScript
import com.example.generator2.screens.scripting.ui.RegisterViewDraw
import com.example.generator2.screens.ui.ScriptTable
import javax.sql.RowSetReader


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

        Spacer(modifier = Modifier.height(8.dp))

        BottomAppBar(
            backgroundColor = colorLightBackground,
            contentColor = Color.LightGray,
            elevation = 2.dp,
            cutoutShape = CircleShape
        ) {


            //Кнопка назад
            IconButton(onClick = {navController.popBackStack()}) {
                Icon(painter = painterResource(R.drawable.back4), contentDescription = null)
            }

            // content alpha provided by BottomAppBar
            Spacer(modifier = Modifier.weight(1f))

            IconButton(onClick = { }) {
                Icon(painter = painterResource(R.drawable.play), contentDescription = null)
            }
            IconButton(onClick = { }) {
                Icon(painter = painterResource(R.drawable.pause), contentDescription = null)
            }
            IconButton(onClick = { }) {
                Icon(painter = painterResource(R.drawable.stop), contentDescription = null)
            }

            Spacer(modifier = Modifier.weight(1f))

//            IconButton(onClick = {}) {
//                Icon(painter = painterResource(R.drawable.info3), contentDescription = null)
//            }

            IconButton(onClick = {navController.navigate("scriptinfo")}) {
                Icon(painter = painterResource(R.drawable.info4), contentDescription = null)
            }

        }

    }
}








