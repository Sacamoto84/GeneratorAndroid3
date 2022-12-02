package com.example.generator2.screens.scripting

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import colorLightBackground
import com.example.generator2.R
import com.example.generator2.vm.Global
import com.example.generator2.vm.StateCommandScript
import com.example.generator2.screens.scripting.ui.RegisterViewDraw
import com.example.generator2.screens.ui.ScriptTable


//Основной экран для скриптов
@Composable
fun ScreenScriptCommon(navController: NavHostController, global: Global) {
    println("-3")
    Column( //Modifier
        //  .recomposeHighlighter()
        //.background(Color.Cyan)
    ) {

        println("-2")
        Box(Modifier.weight(1f)) {

            println("-1")
            ScriptTable(global = global)
        }

        //Блок регистров
        if (global.script.state != StateCommandScript.ISEDITTING) {
            Spacer(modifier = Modifier.height(8.dp))

            RegisterViewDraw(global = global)
        }
        Spacer(modifier = Modifier.height(8.dp))

        BottomAppBar(
            backgroundColor = colorLightBackground,
            contentColor = Color.White,
        ) {


            //Кнопка назад
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(painter = painterResource(R.drawable.back4), contentDescription = null)
            }



            Spacer(modifier = Modifier.weight(1f))


            if ((global.script.state == StateCommandScript.ISRUNNING) || (global.script.state == StateCommandScript.ISPAUSE)) {

                //Пауза
                IconButton(onClick = {

                    if (global.script.state != StateCommandScript.ISPAUSE) global.script.command(
                        StateCommandScript.PAUSE
                    )
                    else {
                        global.script.state = StateCommandScript.ISRUNNING
                        global.script.end = false
                    }

                }) {

                    if (global.script.state != StateCommandScript.ISPAUSE)
                        Icon(
                        painter = painterResource(
                            R.drawable.pause
                        ), contentDescription = null
                    )
                    else
                        Icon(
                            painter = painterResource(
                                R.drawable.play
                            ), contentDescription = null
                        )

                }
            } else {
                //Старт
                IconButton(onClick = {
                    global.script.command(StateCommandScript.START)
                }) {
                    Icon(painter = painterResource(R.drawable.play), contentDescription = null)
                }
            }


            Spacer(modifier = Modifier.weight(0.1f))

            //Стоп
            IconButton(onClick = {
                global.script.command(StateCommandScript.STOP)
            }) {
                Icon(painter = painterResource(R.drawable.stop), contentDescription = null)
            }

            Spacer(modifier = Modifier.weight(1f))

            IconButton(onClick = { navController.navigate("scriptinfo") }) {
                Icon(painter = painterResource(R.drawable.info4), contentDescription = null)
            }


        }

    }
}








