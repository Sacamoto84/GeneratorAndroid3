package com.example.generator2.mainscreen4

import androidx.compose.runtime.Composable
import com.example.generator2.Global.bottomBarRoute
import kotlin.system.exitProcess

@Composable
private fun Key0() {
    TemplateButtonBottomBar(
        str = "Сохранить",
        onClick = {
            bottomBarRoute.value = bottomBarEnum.SAVE
        })
}

@Composable
private fun Key1() {
    TemplateButtonBottomBar(
        str = "Загрузить",
        onClick = {bottomBarRoute.value = bottomBarEnum.LOAD })
}

@Composable
private fun Key2() {
    TemplateButtonBottomBar(
        str = "Редактор",
        onClick = {  })
}

@Composable
private fun Key5() {
    TemplateButtonBottomBar(
        str = "Выход",
        onClick = { exitProcess(0) })
}

@Composable
private fun Key4() {
    TemplateButtonBottomBar(
        str = "Скрипт",
        onClick = { bottomBarRoute.value = bottomBarEnum.SCRIPT })
}

@Composable
private fun Key3() {
    TemplateButtonBottomBar(
        str = "Настройка",
        onClick = { bottomBarRoute.value = bottomBarEnum.SETTING })
}

@Composable
fun BottomBarGenerator() {
    TemplateBottomBar6Key(
        key0 = { Key0() },
        key1 = { Key1() },
        key2 = { Key2() },
        key3 = { Key3() },
        key4 = { Key4() },
        key5 = { Key5() },
        )
}