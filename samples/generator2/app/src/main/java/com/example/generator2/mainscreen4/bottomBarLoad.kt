package com.example.generator2.mainscreen4

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.generator2.vm.Global

@Composable
private fun Key0(global: Global = viewModel()) {
    TemplateButtonBottomBar(
        str = "Загрузить текущее",
        onClick = {
            //TODO()
            global.bottomBarRoute.value = bottomBarEnum.HOME
        })
}

@Composable
private fun Key1(global: Global = viewModel()) {
    TemplateButtonBottomBar(
        str = "Загрузить как",
        onClick = {
            global.bottomBarRoute.value = bottomBarEnum.LOADAS
        })
}

@Composable
private fun Key5(global: Global = viewModel()) {
    TemplateButtonBottomBar(
        str = "Назад",
        onClick = {
            global.bottomBarRoute.value = bottomBarEnum.HOME
        })
}

@Composable
fun BottomBarLoad() {
    TemplateBottomBar6Key(
        key0 = { Key0() },
        key1 = { Key1() },
        //key2 = { Key2() },
        //key3 = {  },
        //key4 = { },
        key5 = { Key5()   },
    )
}