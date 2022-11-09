package com.example.generator2.mainscreen4


import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import colorGreen
import com.example.generator2.Global
import com.example.generator2.Global.bottomBarRoute
import com.example.generator2.scripting.StateCommandScript

@Composable
private fun Key0() {

    TemplateButtonBottomBar(str = "Шагнуть", onClick = {

    })
}

@Composable
private fun Key1() {


    if ((Global.script.state == StateCommandScript.ISRUNNING) || (Global.script.state == StateCommandScript.ISPAUSE)) {
        TemplateButtonBottomBar(str = if (Global.script.state != StateCommandScript.ISPAUSE) "Пауза" else "Продолжить",
            onClick = {
                if (Global.script.state != StateCommandScript.ISPAUSE) Global.script.command(
                    StateCommandScript.PAUSE
                )
                else {
                    Global.script.state = StateCommandScript.ISRUNNING
                    Global.script.end = false
                }
            })
    } else {

        TemplateButtonBottomBar(str = "Запуск", onClick = {
            Global.script.command(StateCommandScript.START)
        })

    }

}

@Composable
private fun Key2() {
    TemplateButtonBottomBar(str = "Стоп", onClick = {
        Global.script.command(StateCommandScript.STOP)
    })
}

@Composable
private fun Key4() {
    TemplateButtonBottomBar(
        str = "Назад", onClick = {
            bottomBarRoute.value = bottomBarEnum.HOME
        },
        backgroundColor = Color.DarkGray,
        contentColor = Color.White
    )
}

@Composable
private fun Key5() {
    TemplateButtonBottomBar(str = "Назад", onClick = {
        bottomBarRoute.value = bottomBarEnum.HOME
    })
}

@Composable
fun bottomBarScript() {
    TemplateBottomBar6Key(
        key0 = { Key1() },
        key2 = { Key0() },
        key3 = { Key2() },
        key5 = { Key4() },
    )
}