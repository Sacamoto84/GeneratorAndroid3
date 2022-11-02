package com.example.generator2.scripting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import com.example.generator2.Global
import com.example.generator2.mainscreen4.bottomBarEnum
import java.util.*


//Экраны для нижнего меню
enum class routeKeyboardEnum {
    HOME,
    NUMDER,
    F,
    ONOFF,
    CRAMFM,
    CRAMValue,
    FMValue(),
}

//Клавиатурка
class scriptKeyboard(index: Int) {


    //Пути для отрисовки нижнего меню
    var route = mutableStateOf(routeKeyboardEnum.HOME)
    val routeStack = Stack<routeKeyboardEnum>() //Стек для отработки назад


    var text = MutableLiveData<String>("")
    private val listComand: MutableList<String> = mutableListOf<String>() //Список команд

    init {
        listComand.clear()

        //routeStack.push(routeKeyboardEnum.HOME) //Положили хоум

        //Разобрать строку на список команд
        //val listComand = Global.script.list[index].split(" ").toMutableList()
        //определяем какой экран показать
        // when (listComand.size) {
        //0 -> Пустая строка ничего нет поэтому начальный экран //TODO("Начальный экран")
        //}
    }

    fun routeTo(r: routeKeyboardEnum) {
        routeStack.push(route.value)
        route.value = r
    }


    //На кнопку назад, вытянуть из стека экран
    fun backRoute() {

        print("backRoute")
        print(routeStack)

        if (routeStack.empty()) {
            route.value = routeKeyboardEnum.HOME
            return
        }
        route.value = routeStack.pop()

    }


    @Composable
    fun core() {
        when (route.value) {
            routeKeyboardEnum.HOME -> screenHOME()
            routeKeyboardEnum.NUMDER -> screenNUMBERPAD()
            routeKeyboardEnum.F -> screenFPAD()
            routeKeyboardEnum.ONOFF -> screenONOFF()
            routeKeyboardEnum.CRAMFM -> screenCRAMFM()
            routeKeyboardEnum.CRAMValue -> screenCRAMValue()
            routeKeyboardEnum.FMValue   -> screenFMValue()
            else -> screenHOME()
        }


    }


    //show hide


    @Composable
    fun Draw(
        k0: (@Composable () -> Unit)? = null,
        k1: (@Composable () -> Unit)? = null,
        k2: (@Composable () -> Unit)? = null,
        k3: (@Composable () -> Unit)? = null,
        k4: (@Composable () -> Unit)? = null,
        k5: (@Composable () -> Unit)? = null,
        k6: (@Composable () -> Unit)? = null,
        k7: (@Composable () -> Unit)? = null,
        k8: (@Composable () -> Unit)? = null,
        k9: (@Composable () -> Unit)? = null,
        k10: (@Composable () -> Unit)? = null,
        k11: (@Composable () -> Unit)? = null,
        k12: (@Composable () -> Unit)? = null,
        k13: (@Composable () -> Unit)? = null,
        k14: (@Composable () -> Unit)? = null,
        k15: (@Composable () -> Unit)? = null,
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(horizontalArrangement = Arrangement.SpaceAround) {
                if (k0 != null) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        k0()
                    }
                }
                if (k1 != null) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        k1()
                    }
                }
                if (k2 != null) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        k2()
                    }
                }
                if (k3 != null) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        k3()
                    }
                }

            }
            Row() {
                if (k4 != null) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        k4()
                    }
                }
                if (k5 != null) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        k5()
                    }
                }
                if (k6 != null) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        k6()
                    }
                }
                if (k7 != null) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        k7()
                    }
                }
            }
            Row() {
                if (k8 != null) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        k8()
                    }
                }
                if (k9 != null) {

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        k9()
                    }
                }
                if (k10 != null) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        k10()
                    }
                }
                if (k11 != null) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        k11()
                    }
                }
            }
            Row() {
                if (k12 != null) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        k12()
                    }
                }
                if (k13 != null) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        k13()
                    }
                }
                if (k14 != null) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        k14()
                    }
                }
                if (k15 != null) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        k15()
                    }
                }
            }
        }
    }


    //label текст кнопки
    //route перейти на экран с нужным именем
    //Выполнить действие, выполняется или действие или onClick
    @Composable
    fun keyX(label: String, route: String? = null, onClick: () -> Unit) {
        Button(onClick = {

            //Переход на нужный экран
            if (route != null) {

            }
            onClick()
        }) {
            Text(label)
        }
    }


    @Composable
    fun keyEnter() {
        Button(onClick = { /*TODO*/ }) {
            Text("E")
        }
    }

    /////////////////////////////////////////////////////

    @Composable
    fun screenHOME() {
        Draw(
            k0 = { keyX("CH1", onClick = {routeTo(routeKeyboardEnum.CRAMFM)}) },
            k1 = { keyX("CR1", onClick = {routeTo(routeKeyboardEnum.CRAMValue)}) },
            k2 = { keyX("AM1", onClick = {routeTo(routeKeyboardEnum.CRAMValue)}) },
            k3 = { keyX("FM1", onClick = {routeTo(routeKeyboardEnum.FMValue)}) },
            k4 = { keyX("CH2", onClick = {routeTo(routeKeyboardEnum.CRAMFM)}) },
            k5 = { keyX("CR2", onClick = {routeTo(routeKeyboardEnum.CRAMValue)}) },
            k6 = { keyX("AM2", onClick = {routeTo(routeKeyboardEnum.CRAMValue)}) },
            k7 = { keyX("FM2", onClick = {routeTo(routeKeyboardEnum.FMValue)}) },
            k8 = { keyX("GOTO", onClick = {routeTo(routeKeyboardEnum.NUMDER)}) },
            k9 = {
                keyX("IF", onClick = {
                    routeTo(routeKeyboardEnum.F)
                })
            },
            k10 = { keyX("ELSE", onClick = {}) },
            k11 = { keyX("<", onClick = {}) },
            k12 = {
                keyX(
                    "DELAY",
                    onClick = {
                        routeTo(routeKeyboardEnum.NUMDER)
                    })
            },
            k13 = { keyX("ENDIF", onClick = {}) },
            k14 = { keyX("LOAD", onClick = {}) },
            k15 = { keyEnter() })

    }


    //Экран числовой клавиатуры
    @Composable
    fun screenNUMBERPAD() {
        Draw(
            k0 = { keyX("1", onClick = {}) },
            k1 = { keyX("2", onClick = {}) },
            k2 = { keyX("3", onClick = {}) },
            k3 = { keyX("DEL", onClick = {}) },
            k4 = { keyX("4", onClick = {}) },
            k5 = { keyX("5", onClick = {}) },
            k6 = { keyX("6", onClick = {}) },
            k7 = { },
            k8 = { keyX("7", onClick = {}) },
            k9 = { keyX("8", onClick = {}) },
            k10 = { keyX("9", onClick = {}) },
            k11 = { keyX("<", onClick = { backRoute() }) },
            k12 = { keyX(".", onClick = {}) },
            k13 = { keyX("0", onClick = {}) },
            k14 = { },
            k15 = { keyEnter() }
        )
    }

    //Экран выбора регистра
    @Composable
    fun screenFPAD() {
        Draw(
            k0 = { keyX("F1", onClick = {}) },
            k1 = { keyX("F2", onClick = {}) },
            k2 = { keyX("F3", onClick = {}) },
            k3 = { keyX("DEL", onClick = {}) },
            k4 = { keyX("F4", onClick = {}) },
            k5 = { keyX("F5", onClick = {}) },
            k6 = { keyX("F6", onClick = {}) },
            k7 = { },
            k8 = { keyX("F7", onClick = {}) },
            k9 = { keyX("F8", onClick = {}) },
            k10 = { keyX("F9", onClick = {}) },
            k11 = { keyX("<", onClick = { backRoute() }) },
            k12 = { },
            k13 = { keyX("F0", onClick = {}) },
            k14 = { },
            k15 = { keyEnter() }
        )
    }

    @Composable
    fun screenONOFF() {
        Draw(
            k0 = { keyX("ON", onClick = {}) },
            k1 = { },
            k2 = { },
            k3 = { },
            k4 = { keyX("OFF", onClick = {}) },
            k5 = { },
            k6 = { },
            k7 = { },
            k8 = { },
            k9 = { },
            k10 = { },
            k11 = { keyX("<", onClick = { backRoute() }) },
            k12 = { },
            k13 = { },
            k14 = { },
            k15 = { keyEnter() }
        )
    }

    @Composable
    fun screenCRAMFM() {
        Draw(
            k0 = { keyX("CR", onClick = { routeTo(routeKeyboardEnum.ONOFF)}) },
            k1 = { },
            k2 = { },
            k3 = { },
            k4 = { keyX("AM", onClick = {routeTo(routeKeyboardEnum.ONOFF)}) },
            k5 = { },
            k6 = { },
            k7 = { },
            k8 = { keyX("FM", onClick = {routeTo(routeKeyboardEnum.ONOFF)}) },
            k9 = { },
            k10 = { },
            k11 = { keyX("<", onClick = { backRoute() }) },
            k12 = { },
            k13 = { },
            k14 = { },
            k15 = { keyEnter() }
        )
    }


    @Composable
    fun screenFMValue() {
        Draw(
            k0 = { keyX("FR Fx", onClick = { routeTo(routeKeyboardEnum.F)}) },
            k1 = { keyX("FR xxxx.x", onClick = { routeTo(routeKeyboardEnum.NUMDER)})},
            k2 = { },
            k3 = { },
            k4 = { keyX("MOD", onClick = {}) },
            k5 = { },
            k6 = { },
            k7 = { },
            k8 = {  keyX("BASE Fx", onClick = { routeTo(routeKeyboardEnum.F)}) },
            k9 = { keyX("BASE xxxx.x", onClick = { routeTo(routeKeyboardEnum.NUMDER)})},
            k10 = { },
            k11 = { keyX("<", onClick = { backRoute() }) },
            k12 = { keyX("DEV Fx", onClick = { routeTo(routeKeyboardEnum.F)})},
            k13 = { keyX("DEV xxxx.x", onClick = { routeTo(routeKeyboardEnum.NUMDER)})},
            k14 = { },
            k15 = { keyEnter() }
        )
    }
    
    @Composable
    fun screenCRAMValue() {
        Draw(
            k0 = { keyX("FR Fx", onClick = { routeTo(routeKeyboardEnum.F)}) },
            k1 = { keyX("FR xxxx.x", onClick = { routeTo(routeKeyboardEnum.NUMDER)})},
            k2 = { },
            k3 = { },
            k4 = { keyX("MOD", onClick = {}) },
            k5 = { },
            k6 = { },
            k7 = { },
            k8 = {  },
            k9 = { },
            k10 = { },
            k11 = { keyX("<", onClick = { backRoute() }) },
            k12 = { },
            k13 = { },
            k14 = { },
            k15 = { keyEnter() }
        )
    }
    


}

@Preview
@Composable
fun keyboard_preview() {

    val keyboard = scriptKeyboard(0)
    Column() {
        keyboard.core()
        //keyboard.screenHOME()
        //keyboard.screenNUMBERPAD()
    }


}





