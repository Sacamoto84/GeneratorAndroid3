package com.example.generator2.mainscreen4

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.generator2.vm.Global

@Composable
private fun Key0(global: Global) {
    TemplateButtonBottomBar(
        str = "Загрузить текущее",
        onClick = {
            //TODO()
            global.bottomBarRoute.value = bottomBarEnum.HOME
        })
}

@Composable
private fun Key1(global: Global) {
    TemplateButtonBottomBar(
        str = "Загрузить как",
        onClick = {
            global.bottomBarRoute.value = bottomBarEnum.LOADAS
        })
}

@Composable
private fun Key5(global: Global) {
    TemplateButtonBottomBar(
        str = "Назад",
        onClick = {
            global.bottomBarRoute.value = bottomBarEnum.HOME
        })
}

@Composable
fun BottomBarLoad(global: Global) {
    TemplateBottomBar6Key(
        key0 = { Key0(global) },
        key1 = { Key1(global) },
        //key2 = { Key2() },
        //key3 = {  },
        //key4 = { },
        key5 = { Key5(global)   },
    )
}