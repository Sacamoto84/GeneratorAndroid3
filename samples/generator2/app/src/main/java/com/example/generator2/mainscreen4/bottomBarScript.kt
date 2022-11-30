package com.example.generator2.mainscreen4


import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.generator2.vm.Global
import com.example.generator2.vm.StateCommandScript

@Composable
private fun Key0() {

    TemplateButtonBottomBar(str = "Шагнуть", onClick = {

    })
}

@Composable
private fun Key1(global: Global) {


    if ((global.script.state == StateCommandScript.ISRUNNING) || (global.script.state == StateCommandScript.ISPAUSE)) {
        TemplateButtonBottomBar(str = if (global.script.state != StateCommandScript.ISPAUSE) "Пауза" else "Продолжить",
            onClick = {
                if (global.script.state != StateCommandScript.ISPAUSE) global.script.command(
                    StateCommandScript.PAUSE
                )
                else {
                    global.script.state = StateCommandScript.ISRUNNING
                    global.script.end = false
                }
            })
    } else {

        TemplateButtonBottomBar(str = "Запуск", onClick = {
            global.script.command(StateCommandScript.START)
        })

    }

}

@Composable
private fun Key2(global: Global) {
    TemplateButtonBottomBar(str = "Стоп", onClick = {
        global.script.command(StateCommandScript.STOP)
    })
}

@Composable
private fun Key4(global: Global ) {
    TemplateButtonBottomBar(
        str = "Назад", onClick = {
            global.bottomBarRoute.value = bottomBarEnum.HOME
        },
        backgroundColor = Color.DarkGray,
        contentColor = Color.White
    )
}

@Composable
private fun Key5(global: Global ) {
    TemplateButtonBottomBar(str = "Назад", onClick = {
        global.bottomBarRoute.value = bottomBarEnum.HOME
    })
}

@Composable
fun bottomBarScript(global : Global) {
    TemplateBottomBar6Key(
        key0 = { Key1(global) },
        key2 = { Key0() },
        key3 = { Key2(global) },
        key5 = { Key4(global) },
    )
}