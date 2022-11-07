package com.example.generator2.scripting.ui

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import colorDarkBackground
import com.example.generator2.mainscreen4.TemplateButtonBottomBar
import com.example.generator2.scripting.Script
import com.example.generator2.ui.theme.NoRippleTheme
import com.example.generator2.ui.wiget.UImodifier.noRippleClickable
import java.util.*

//Экраны для нижнего меню
enum class RouteKeyboardEnum {
    HOME, NUMBER, F, ONOFF, CRAMFM, CRAMValue, FMValue, FPADFM, Comparison, IFValue
}

//Клавиатурка
//class ScriptKeyboard(private val list: SnapshotStateList<String>) {
class ScriptKeyboard(private val s: Script) {

    private var selectIndex = s.pc.value
    private val list = s.list

    private var route = mutableStateOf(RouteKeyboardEnum.HOME)
    private val routeStack = Stack<RouteKeyboardEnum>() //Стек для отработки назад

    //var text = mutableStateOf("")

    private var listCommand: MutableList<String> = mutableListOf() //Список команд

    init { //textToListCommand(list[selectIndex])
        Log.i("script", "ScriptKeyboard()")
    }

    /*
     *╭─────────────────────────────────────╮
     *│    Преобразование текста в список   │
     *╰─────────────────────────────────────╯
     */
    private fun textToListCommand(str: String) {
        listCommand.clear()
        listCommand = str.split(" ").toMutableList()
    }


    /*
   *╭─────────────────────────────────────╮
   *│                                     │
   *╰─────────────────────────────────────╯
   */
    private fun listCommandToText(): String {
        return listCommand.joinToString(" ")
    } /////////////////////////////////////////////////////////////////

    /*
    *╭─────────────────────────────────────╮
    *│    Добавить в список по индексу     │
    *╰─────────────────────────────────────╯
    */
    private fun listCommandAddToIndex(index: Int, text: String) {

        if (listCommand.isEmpty()) listCommand.add("!")

        while (listCommand.lastIndex < index) {
            listCommand.add("?")
        }

        if ((index >= 0) && (index <= listCommand.lastIndex)) listCommand[index] = text

    }


    private fun routeTo(r: RouteKeyboardEnum) {
        routeStack.push(route.value)
        route.value = r
    }


    //На кнопку назад, вытянуть из стека экран
    private fun backRoute() {
        if (routeStack.empty()) {
            route.value = RouteKeyboardEnum.HOME
            return
        }
        route.value = routeStack.pop()
    }

    @Composable
    fun Core() {

        textToListCommand(list[selectIndex])
        selectIndex = s.pc.value
        Log.i("script", "Keyboard Core() PC:${s.pc.value}")

        when (route.value) {
            RouteKeyboardEnum.HOME       -> ScreenHOME()
            RouteKeyboardEnum.NUMBER     -> ScreenNUMBERPAD()
            RouteKeyboardEnum.F          -> ScreenFPAD()
            RouteKeyboardEnum.ONOFF      -> ScreenONOFF()
            RouteKeyboardEnum.CRAMFM     -> ScreenCRAMFM()
            RouteKeyboardEnum.CRAMValue  -> ScreenCRAMValue()
            RouteKeyboardEnum.FMValue    -> ScreenFMValue()
            RouteKeyboardEnum.FPADFM     -> ScreenFPADFM()
            RouteKeyboardEnum.Comparison -> ScreenComparison()
            RouteKeyboardEnum.IFValue    -> ScreenIFValue() //else -> ScreenHOME()
        }
    }

    //show hide


    //label текст кнопки
    //route перейти на экран с нужным именем
    //Выполнить действие, выполняется или действие или onClick
    @Composable
    fun KeyX(label: String, onClick: () -> Unit) {

        TemplateButtonBottomBar(str = label, onClick = {
            onClick()
        })
    }


    /////////////////////////////////////////////////////

    @Composable
    fun ScreenHOME() {
        Draw(k0 = {
            KeyX("CH1", onClick = {
                listCommand.clear()
                listCommandAddToIndex(0, "CH1" )
                routeTo(RouteKeyboardEnum.CRAMFM)
                list[selectIndex] = listCommandToText()
            })
        },
            k1 = {
                KeyX("CR1", onClick = {
                    listCommandAddToIndex(0, "CR1")
                    list[selectIndex] = listCommandToText()
                    routeTo(RouteKeyboardEnum.CRAMValue)
                })
            },
            k2 = {
                KeyX("AM1", onClick = {
                    listCommandAddToIndex(0, "AM1")
                    list[selectIndex] = listCommandToText()
                    routeTo(RouteKeyboardEnum.CRAMValue)
                })
            },
            k3 = {
                KeyX("FM1", onClick = {
                    listCommandAddToIndex(0, "FM1")
                    list[selectIndex] = listCommandToText()
                    routeTo(RouteKeyboardEnum.FMValue)
                })
            },
            k4 = {
                KeyX("CH2", onClick = {
                    listCommand.clear()
                    listCommandAddToIndex(0, "CH2")
                    list[selectIndex] = listCommandToText()
                    routeTo(RouteKeyboardEnum.CRAMFM)
                })
            },
            k5 = {
                KeyX("CR2", onClick = {

                    listCommandAddToIndex(0, "CR2")
                    list[selectIndex] = listCommandToText()
                    routeTo(RouteKeyboardEnum.CRAMValue)

                })
            },
            k6 = {
                KeyX("AM2", onClick = {
                    listCommandAddToIndex(0, "AM2")
                    list[selectIndex] = listCommandToText()
                    routeTo(RouteKeyboardEnum.CRAMValue)
                })
            },
            k7 = {
                KeyX("FM2", onClick = {
                    listCommandAddToIndex(
                        0, "FM2"
                    )
                    list[selectIndex] = listCommandToText()
                    routeTo(RouteKeyboardEnum.FMValue)
                })
            },

            k8 = {
                KeyX("GOTO", onClick = {
                    listCommandAddToIndex(0, "GOTO")
                    list[selectIndex] = listCommandToText()
                    routeTo(RouteKeyboardEnum.NUMBER)
                })
            },

            k9 = {
                KeyX("IF", onClick = {
                    listCommandAddToIndex(0, "IF")
                    list[selectIndex] = listCommandToText()
                    routeTo(RouteKeyboardEnum.FPADFM)
                })
            },

            k10 = { KeyX("ELSE", onClick = { list[selectIndex] = "ELSE" }) },
            k11 = {
                KeyX("CLR", onClick = {
                    listCommand.clear()
                    list[selectIndex] = listCommandToText()
                })
            },
            k12 = {
                KeyX("DELAY", onClick = {
                    listCommandAddToIndex(0, "DELAY")
                    list[selectIndex] = listCommandToText()
                    routeTo(RouteKeyboardEnum.NUMBER)
                })
            },
            k13 = { KeyX("ENDIF", onClick = { list[selectIndex] = "ENDIF" }) },
            k14 = { KeyX("LOAD", onClick = {}) },
            k15 = { KeyX("?", onClick = { }) })

    }

    //Экран числовой клавиатуры
    @Composable
    fun ScreenNUMBERPAD() {
        Draw(k0 = { KeyX("1", onClick = {}) },
            k1 = { KeyX("2", onClick = {}) },
            k2 = { KeyX("3", onClick = {}) },
            k3 = { KeyX("DEL", onClick = {}) },
            k4 = { KeyX("4", onClick = {}) },
            k5 = { KeyX("5", onClick = {}) },
            k6 = { KeyX("6", onClick = {}) },
            k7 = { },
            k8 = { KeyX("7", onClick = {}) },
            k9 = { KeyX("8", onClick = {}) },
            k10 = { KeyX("9", onClick = {}) },
            k11 = { KeyX("<", onClick = { backRoute() }) },
            k12 = { KeyX(".", onClick = {}) },
            k13 = { KeyX("0", onClick = {}) },
            k14 = { },
            k15 = { KeyEnter() })
    }

    //Экран выбора регистра
    @Composable
    fun ScreenFPAD() {
        Draw(k0 = { KeyX("F1", onClick = {

        }) },
            k1 = { KeyX("F2", onClick = {}) },
            k2 = { KeyX("F3", onClick = {}) },
            k3 = { KeyX("DEL", onClick = {}) },
            k4 = { KeyX("F4", onClick = {}) },
            k5 = { KeyX("F5", onClick = {}) },
            k6 = { KeyX("F6", onClick = {}) },
            k7 = { },
            k8 = { KeyX("F7", onClick = {}) },
            k9 = { KeyX("F8", onClick = {}) },
            k10 = { KeyX("F9", onClick = {}) },
            k11 = { KeyBack()  },
            k12 = { },
            k13 = { KeyX("F0", onClick = {}) },
            k14 = { },
            k15 = { KeyEnter() })
    }

    //Экран выбора регистра
    @Composable
    fun ScreenFPADFM() {
        Draw(k0 = { KeyX("F1", onClick = { routeTo(RouteKeyboardEnum.Comparison) }) },
            k1 = { KeyX("F2", onClick = { routeTo(RouteKeyboardEnum.Comparison) }) },
            k2 = { KeyX("F3", onClick = { routeTo(RouteKeyboardEnum.Comparison) }) },
            k3 = { },
            k4 = { KeyX("F4", onClick = { routeTo(RouteKeyboardEnum.Comparison) }) },
            k5 = { KeyX("F5", onClick = { routeTo(RouteKeyboardEnum.Comparison) }) },
            k6 = { KeyX("F6", onClick = { routeTo(RouteKeyboardEnum.Comparison) }) },
            k7 = { },
            k8 = { KeyX("F7", onClick = { routeTo(RouteKeyboardEnum.Comparison) }) },
            k9 = { KeyX("F8", onClick = { routeTo(RouteKeyboardEnum.Comparison) }) },
            k10 = { KeyX("F9", onClick = { routeTo(RouteKeyboardEnum.Comparison) }) },
            k11 = { KeyX("<", onClick = { backRoute() }) },
            k12 = { },
            k13 = { KeyX("F0", onClick = { routeTo(RouteKeyboardEnum.Comparison) }) },
            k14 = { },
            k15 = { KeyEnter() })
    }

    @Composable
    fun ScreenComparison() {
        Draw(k0 = { KeyX("<", onClick = { routeTo(RouteKeyboardEnum.IFValue) }) },
            k1 = { KeyX(">", onClick = { routeTo(RouteKeyboardEnum.IFValue) }) },
            k2 = { },
            k3 = { },
            k4 = { KeyX("<=", onClick = { routeTo(RouteKeyboardEnum.IFValue) }) },
            k5 = { KeyX(">=", onClick = { routeTo(RouteKeyboardEnum.IFValue) }) },
            k6 = { },
            k7 = { },
            k8 = { KeyX("==", onClick = { routeTo(RouteKeyboardEnum.IFValue) }) },
            k9 = { KeyX("!=", onClick = { routeTo(RouteKeyboardEnum.IFValue) }) },
            k10 = { },
            k11 = { KeyX("<", onClick = { backRoute() }) },
            k12 = { },
            k13 = { },
            k14 = { },
            k15 = { KeyEnter() })

    }

    @Composable
    fun ScreenONOFF() {
        Draw(k0 = {
            KeyX("ON", onClick = {
                listCommandAddToIndex(2, "ON")
                list[selectIndex] = listCommandToText()
                routeTo(RouteKeyboardEnum.HOME)
            })
        },
            k1 = {
                KeyX("OFF", onClick = {
                    listCommandAddToIndex(2, "OFF")
                    list[selectIndex] = listCommandToText()
                    routeTo(RouteKeyboardEnum.HOME)
                })
            },
            k2 = { },
            k3 = { KeyBack() },
            k4 = { KeyBlank() },
            k5 = { },
            k6 = { },
            k7 = { },
            k8 = { },
            k9 = { },
            k10 = { },
            k11 = { KeyBlank() },
            k12 = { },
            k13 = { },
            k14 = { },
            k15 = { KeyBlank() })
    }

    @Composable
    fun ScreenCRAMFM() {
        Draw(k0 = {
            KeyX("CR", onClick = {

                listCommandAddToIndex(
                    1, "CR"
                )
                list[selectIndex] = listCommandToText()
                routeTo(RouteKeyboardEnum.ONOFF)

            })
        },
            k1 = {
                KeyX("AM", onClick = {

                    listCommandAddToIndex(
                        1, "AM"
                    )
                    list[selectIndex] = listCommandToText()
                    routeTo(RouteKeyboardEnum.ONOFF)

                })
            },
            k2 = {
                KeyX("FM", onClick = {

                    listCommandAddToIndex(
                        1, "FM"
                    )
                    routeTo(RouteKeyboardEnum.ONOFF)

                })
            },
            k3 = { KeyBack() },
            k4 = {

            },
            k5 = { KeyBlank() },
            k6 = { },
            k7 = {},
            k8 = {

            },
            k9 = { },
            k10 = { },
            k11 = { KeyBlank() },
            k12 = { },
            k13 = { },
            k14 = { },
            k15 = { KeyBlank() })
    }

    @Composable
    fun ScreenFMValue() {
        Draw(k0 = { KeyX("FR Fx", onClick = { routeTo(RouteKeyboardEnum.F) }) },
            k1 = { KeyX("FR xx", onClick = { routeTo(RouteKeyboardEnum.NUMBER) }) },
            k2 = { },
            k3 = { },
            k4 = { KeyX("MOD", onClick = {}) },
            k5 = { },
            k6 = { },
            k7 = { },
            k8 = { KeyX("BASE Fx", onClick = { routeTo(RouteKeyboardEnum.F) }) },
            k9 = { KeyX("BASE xx", onClick = { routeTo(RouteKeyboardEnum.NUMBER) }) },
            k10 = { },
            k11 = { KeyX("<", onClick = { backRoute() }) },
            k12 = { KeyX("DEV Fx", onClick = { routeTo(RouteKeyboardEnum.F) }) },
            k13 = { KeyX("DEV xx", onClick = { routeTo(RouteKeyboardEnum.NUMBER) }) },
            k14 = { },
            k15 = { KeyEnter() })
    }

    @Composable
    fun ScreenIFValue() {
        Draw(k0 = { KeyX("Fx", onClick = { routeTo(RouteKeyboardEnum.F) }) },
            k1 = { },
            k2 = { },
            k3 = { },
            k4 = { KeyX("xxxx.x", onClick = { routeTo(RouteKeyboardEnum.NUMBER) }) },
            k5 = { },
            k6 = { },
            k7 = { },
            k8 = { },
            k9 = { },
            k10 = { },
            k11 = { KeyX("<", onClick = { backRoute() }) },
            k12 = { },
            k13 = { },
            k14 = { },
            k15 = { KeyEnter() })
    }

    @Composable
    fun ScreenCRAMValue() {
        Draw(k0 = { KeyX("FR Fx", onClick = { routeTo(RouteKeyboardEnum.F) }) },
            k1 = { KeyX("FR xx.x", onClick = { routeTo(RouteKeyboardEnum.NUMBER) }) },
            k2 = { },
            k3 = { },
            k4 = { KeyX("MOD", onClick = {}) },
            k5 = { },
            k6 = { },
            k7 = { },
            k8 = { },
            k9 = { },
            k10 = { },
            k11 = { KeyX("<", onClick = { backRoute() }) },
            k12 = { },
            k13 = { },
            k14 = { },
            k15 = { KeyEnter() })
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Composable
    fun KeyEnter() {
        KeyX("DONE", onClick = { routeTo(RouteKeyboardEnum.HOME) })
    }

    @Composable
    fun KeyBack() {

        KeyX("<-", onClick = {
            listCommand.removeAt(listCommand.lastIndex)
            list[selectIndex] = listCommandToText()
            backRoute()
        })

    }

    @Composable
    fun KeyBlank() {
        CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
            OutlinedButton(
                onClick = {},
                modifier = Modifier.fillMaxWidth().padding(start = 8.dp, end = 4.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    backgroundColor = Color.Transparent, contentColor = Color.White
                ),
                border = BorderStroke(1.dp, Color.Transparent),
                contentPadding = PaddingValues(2.dp)
            ) { //Text("str")
            }
        }
    }

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
        Column(
            modifier = Modifier.fillMaxWidth().background(colorDarkBackground)
        ) {
            Row(horizontalArrangement = Arrangement.SpaceAround) {

                if (k0 != null) Box(
                    modifier = Modifier.fillMaxWidth().weight(1f)
                ) {
                    k0()
                }

                if (k1 != null) Box(
                    modifier = Modifier.fillMaxWidth().weight(1f)
                ) {
                    k1()
                }

                if (k2 != null) {
                    Box(
                        modifier = Modifier.fillMaxWidth().weight(1f)
                    ) {
                        k2()
                    }
                }
                if (k3 != null) {
                    Box(
                        modifier = Modifier.fillMaxWidth().weight(1f)
                    ) {
                        k3()
                    }
                }

            }

            Row() {

                if (k4 != null) {
                    Box(
                        modifier = Modifier.fillMaxWidth().weight(1f)
                    ) {
                        k4()
                    }
                }

                if (k5 != null) {
                    Box(
                        modifier = Modifier.fillMaxWidth().weight(1f)
                    ) {
                        k5()
                    }
                }

                if (k6 != null) {
                    Box(
                        modifier = Modifier.fillMaxWidth().weight(1f)
                    ) {
                        k6()
                    }
                }

                if (k7 != null) {
                    Box(
                        modifier = Modifier.fillMaxWidth().weight(1f)
                    ) {
                        k7()
                    }
                }

            }

            Row() {
                if (k8 != null) {
                    Box(
                        modifier = Modifier.fillMaxWidth().weight(1f)
                    ) {
                        k8()
                    }
                }
                if (k9 != null) {
                    Box(
                        modifier = Modifier.fillMaxWidth().weight(1f)
                    ) {
                        k9()
                    }
                }
                if (k10 != null) {
                    Box(
                        modifier = Modifier.fillMaxWidth().weight(1f)
                    ) {
                        k10()
                    }
                }
                if (k11 != null) {
                    Box(
                        modifier = Modifier.fillMaxWidth().weight(1f)
                    ) {
                        k11()
                    }
                }
            }

            Row() {
                if (k12 != null) {
                    Box(
                        modifier = Modifier.fillMaxWidth().weight(1f)
                    ) {
                        k12()
                    }
                }
                if (k13 != null) {
                    Box(
                        modifier = Modifier.fillMaxWidth().weight(1f)
                    ) {
                        k13()
                    }
                }
                if (k14 != null) {
                    Box(
                        modifier = Modifier.fillMaxWidth().weight(1f)
                    ) {
                        k14()
                    }
                }
                if (k15 != null) {
                    Box(
                        modifier = Modifier.fillMaxWidth().weight(1f)
                    ) {
                        k15()
                    }
                }
            }
        }
    }

}

@Preview
@Composable
fun KeyboardPreview() { //val keyboard = ScriptKeyboard(0)
    //keyboard.Core()
}





