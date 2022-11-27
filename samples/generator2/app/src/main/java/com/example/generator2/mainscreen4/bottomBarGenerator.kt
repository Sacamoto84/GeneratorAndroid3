package com.example.generator2.mainscreen4

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.generator2.Global
import kotlin.system.exitProcess

@Composable
private fun Key0(
    global: Global = viewModel()
) {
    TemplateButtonBottomBar(
        str = "Сохранить",
        onClick = {
            global.bottomBarRoute.value = bottomBarEnum.SAVE
        })
}

@Composable
private fun Key1(global: Global = viewModel()) {
    TemplateButtonBottomBar(
        str = "Загрузить",
        onClick = {global.bottomBarRoute.value = bottomBarEnum.LOAD })
}

@Composable
private fun Key2(global: Global = viewModel()) {
    TemplateButtonBottomBar(
        str = "Редактор",
        onClick = { global.bottomBarRoute.value = bottomBarEnum.EDITOR })
}

@Composable
private fun Key5() {
    TemplateButtonBottomBar(
        str = "Выход",
        onClick = { exitProcess(0) })
}

@Composable
private fun Key4(global: Global = viewModel()) {
    TemplateButtonBottomBar(
        str = "Скрипт",
        onClick = { global.bottomBarRoute.value = bottomBarEnum.SCRIPT })
}

@Composable
private fun Key3(global: Global = viewModel()) {
    TemplateButtonBottomBar(
        str = "Настройка",
        onClick = { global.bottomBarRoute.value = bottomBarEnum.SETTING })
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