package com.example.generator2.mainscreen4

import androidx.compose.runtime.Composable
import com.example.generator2.Global
import com.example.generator2.Global.bottomBarRoute
import com.example.generator2.mainscreen4.TemplateBottomBar6Key
import com.example.generator2.mainscreen4.TemplateButtonBottomBar
import com.example.generator2.mainscreen4.bottomBarEnum

@Composable
private fun Key0() {
    TemplateButtonBottomBar(
        str = "Загрузить текущее",
        onClick = {
            //TODO()
            bottomBarRoute.value = bottomBarEnum.HOME
        })
}

@Composable
private fun Key1() {
    TemplateButtonBottomBar(
        str = "Загрузить как",
        onClick = {
            bottomBarRoute.value = bottomBarEnum.LOADAS
        })
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