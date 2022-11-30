package com.example.generator2.mainscreen4

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.generator2.vm.Global
import kotlin.system.exitProcess

@Composable
private fun Key0(
    global: Global
) {
    TemplateButtonBottomBar(
        str = "Сохранить",
        onClick = {
            global.bottomBarRoute.value = bottomBarEnum.SAVE
        })
}

@Composable
private fun Key1(global: Global) {
    TemplateButtonBottomBar(
        str = "Загрузить",
        onClick = {global.bottomBarRoute.value = bottomBarEnum.LOAD })
}

@Composable
private fun Key2(global: Global) {
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
private fun Key4(global: Global) {
    TemplateButtonBottomBar(
        str = "Скрипт",
        onClick = { global.bottomBarRoute.value = bottomBarEnum.SCRIPT })
}

@Composable
private fun Key3(global: Global) {
    TemplateButtonBottomBar(
        str = "Настройка",
        onClick = { global.bottomBarRoute.value = bottomBarEnum.SETTING })
}

@Composable
fun BottomBarGenerator(global : Global) {
    TemplateBottomBar6Key(
        key0 = { Key0(global) },
        key1 = { Key1(global) },
        key2 = { Key2(global) },
        key3 = { Key3(global) },
        key4 = { Key4(global) },
        key5 = { Key5() },
        )
}