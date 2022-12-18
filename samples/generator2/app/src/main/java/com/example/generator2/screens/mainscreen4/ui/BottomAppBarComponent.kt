package com.example.generator2.screens.mainscreen4.ui

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.generator2.R
import com.example.generator2.theme.colorLightBackground
import com.example.generator2.screens.mainscreen4.VMMain4
import com.example.generator2.vm.StateCommandScript
import kotlin.system.exitProcess


//Нижняя панель с кнопками
@Composable
fun M4BottomAppBarComponent(
    toggleDrawer: () -> Unit, navController: NavHostController, global: VMMain4
) {
    BottomAppBar(
        backgroundColor = colorLightBackground,
        contentColor = Color.White,
        elevation = 2.dp,
        cutoutShape = CircleShape
    ) {

        global.hub.audioDevice.getDeviceId()

        IconButton( onClick =  { navController.navigate("config") } ) {
            Icon(painter = painterResource(R.drawable.line3_2), contentDescription = null)
        }

        //Иконка устройства
        IconButton(
            onClick = toggleDrawer
        ) {
            val id  = global.hub.audioDevice.mDeviceId
            var str = "Auto select"
            global.hub.audioDevice.mDeviceAdapter.forEach {
                if (id == it.id)
                    str = it.name
            }
            val imageVector = nameToPainter(str)
            Icon(imageVector, contentDescription = null, modifier = Modifier.size(32.dp))
        }

        //Управление скриптами
        if ((global.hub.script.state == StateCommandScript.ISRUNNING) || (global.hub.script.state == StateCommandScript.ISPAUSE)) {

            //Пауза
            IconButton(onClick = {

                if (global.hub.script.state != StateCommandScript.ISPAUSE) global.hub.script.command(
                    StateCommandScript.PAUSE
                )
                else {
                    global.hub.script.state = StateCommandScript.ISRUNNING
                    global.hub.script.end = false
                }

            }) {

                if (global.hub.script.state != StateCommandScript.ISPAUSE)
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
                global.hub.script.command(StateCommandScript.START)
            }) {
                Icon(painter = painterResource(R.drawable.play), contentDescription = null)
            }
        }
        //Стоп
        IconButton(onClick = {
            global.hub.script.command(StateCommandScript.STOP)
        }) {
            Icon(painter = painterResource(R.drawable.stop), contentDescription = null)
        }






        Spacer(modifier = Modifier.weight(1f))

        IconButton(modifier = Modifier.testTag("buttonM4GoToScript"),
            onClick = { navController.navigate("script") }) {
            Icon(painter = painterResource(R.drawable.script3), contentDescription = null)
        }



        IconButton(modifier = Modifier.testTag("edit"),


            onClick = { navController.navigate("editor") }) {
            Icon(painter = painterResource(R.drawable.editor), contentDescription = null)
        }


        Spacer(modifier = Modifier.weight(0.2f))

        //        IconButton(onClick = { exitProcess(0) }) {
        //            Icon(painter = painterResource(R.drawable.close), contentDescription = null)
        //        }
        //        IconButton(onClick = { exitProcess(0) }) {
        //            Icon(painter = painterResource(R.drawable.close2), contentDescription = null)
        //        }
        //        IconButton(onClick = { exitProcess(0) }) {
        //            Icon(painter = painterResource(R.drawable.close3), contentDescription = null)
        //        }
        IconButton(onClick = { exitProcess(0) }) {
            Icon(painter = painterResource(R.drawable.close4), contentDescription = null)
        }

        //Spacer(modifier = Modifier.weight(0.1f))

    }
}