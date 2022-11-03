package com.example.generator2.mainscreen4


import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.generator2.Global
import com.example.generator2.Global.bottomBarRoute
import com.example.generator2.scripting.StateCommandScript

@Composable
private fun Key0() {

    TemplateButtonBottomBar(
        str = "Запуск",
        onClick = {
            Global.script.command(StateCommandScript.START)
        })
}

@Composable
private fun Key1() {
    TemplateButtonBottomBar(
        str = "Пауза",
        onClick = {
            Global.script.command(StateCommandScript.PAUSE)
        })
}

@Composable
private fun Key2() {
    TemplateButtonBottomBar(
        str = "Стоп",
        onClick = {
            Global.script.command(StateCommandScript.STOP)
        })
}

@Composable
private fun Key4() {
    TemplateButtonBottomBar(
        str = "Скрипт",
        onClick = {
            bottomBarRoute.value = bottomBarEnum.HOME
        }, backgroundColor = Color.Green
    )
}

@Composable
private fun Key5() {
    TemplateButtonBottomBar(
        str = "Назад",
        onClick = {
            bottomBarRoute.value = bottomBarEnum.HOME
        })
}

@Composable
fun bottomBarScript() {
    TemplateBottomBar6Key(
        key0 = { Key0() },
        key1 = { Key1() },
        key2 = { Key2() },
        key3 = { },
        key4 = { Key4() },
        key5 = { Key5() },
    )
}