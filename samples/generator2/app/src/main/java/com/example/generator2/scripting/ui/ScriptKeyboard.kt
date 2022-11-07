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

data class RouteKeyboard(var argument: Int = 0, var route: RouteKeyboardEnum)


//Клавиатурка
//class ScriptKeyboard(private val list: SnapshotStateList<String>) {
class ScriptKeyboard(private val s: Script) {

    private var selectIndex = s.pc.value
    private val list = s.list


    private var route = mutableStateOf(RouteKeyboard(0, RouteKeyboardEnum.HOME))
    private val routeStack = Stack<RouteKeyboard>() //Стек для отработки назад

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

        //if ((index >= 0) && (index <= listCommand.lastIndex))
        listCommand[index] = text
    }

    /*
*╭─────────────────────────────────────╮
*│    Удалить запись по индексу        │
*╰─────────────────────────────────────╯
*/
    private fun listCommandRemoveToIndex(index: Int) {
        if ((index >= 0) && (index <= listCommand.lastIndex)) listCommand.removeAt(index)
    }

    private fun routeTo(r: RouteKeyboard) {
        routeStack.push(route.value)
        route.value = r
    }

    //На кнопку назад, вытянуть из стека экран
    private fun backRoute() {

        if (routeStack.empty()) {
            route.value = RouteKeyboard(0, RouteKeyboardEnum.HOME)
            return
        }

        listCommandRemoveToIndex(route.value.argument)
        list[selectIndex] = listCommandToText()

        route.value = routeStack.pop()

        listCommandRemoveToIndex(route.value.argument)
        list[selectIndex] = listCommandToText()

    }

    @Composable
    fun Core() {

        textToListCommand(list[selectIndex])
        selectIndex = s.pc.value
        Log.i("script", "Keyboard Core() PC:${s.pc.value}")

        when (route.value.route) {
            RouteKeyboardEnum.HOME       -> ScreenHOME(route.value.argument)
            RouteKeyboardEnum.NUMBER     -> ScreenNUMBERPAD(route.value.argument)
            RouteKeyboardEnum.F          -> ScreenFPAD(route.value.argument)
            RouteKeyboardEnum.ONOFF      -> ScreenONOFF(route.value.argument)
            RouteKeyboardEnum.CRAMFM     -> ScreenCRAMFM(route.value.argument)
            RouteKeyboardEnum.CRAMValue  -> ScreenCRAMValue(route.value.argument)
            RouteKeyboardEnum.FMValue    -> ScreenFMValue(route.value.argument)
            RouteKeyboardEnum.FPADFM     -> ScreenFPADFM(route.value.argument)
            RouteKeyboardEnum.Comparison -> ScreenComparison(route.value.argument)
            RouteKeyboardEnum.IFValue    -> ScreenIFValue(route.value.argument) //else -> ScreenHOME()
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
    fun ScreenHOME(arg: Int) {
        Draw(k0 = {
            KeyX("CH1", onClick = {
                listCommand.clear()
                listCommandAddToIndex(0, "CH1")
                list[selectIndex] = listCommandToText()
                routeTo(RouteKeyboard(1, RouteKeyboardEnum.CRAMFM))
            })
        }, k1 = {
            KeyX("CR1", onClick = {
                listCommand.clear()
                listCommandAddToIndex(0, "CR1")
                list[selectIndex] = listCommandToText()
                routeTo(RouteKeyboard(1, RouteKeyboardEnum.CRAMValue))
            })
        }, k2 = {
            KeyX("AM1", onClick = {
                listCommand.clear()
                listCommandAddToIndex(0, "AM1")
                list[selectIndex] = listCommandToText()
                routeTo(RouteKeyboard(1, RouteKeyboardEnum.CRAMValue))
            })
        }, k3 = {
            KeyX("FM1", onClick = {
                listCommand.clear()
                listCommandAddToIndex(0, "FM1")
                list[selectIndex] = listCommandToText()
                routeTo(RouteKeyboard(1, RouteKeyboardEnum.FMValue))
            })
        }, k4 = {
            KeyX("CH2", onClick = {
                listCommand.clear()
                listCommandAddToIndex(0, "CH2")
                list[selectIndex] = listCommandToText()
                routeTo(RouteKeyboard(1, RouteKeyboardEnum.CRAMFM))
            })
        }, k5 = {
            KeyX("CR2", onClick = {
                listCommand.clear()
                listCommandAddToIndex(0, "CR2")
                list[selectIndex] = listCommandToText()
                routeTo(RouteKeyboard(1, RouteKeyboardEnum.CRAMValue))
            })
        }, k6 = {
            KeyX("AM2", onClick = {
                listCommand.clear()
                listCommandAddToIndex(0, "AM2")
                list[selectIndex] = listCommandToText()
                routeTo(RouteKeyboard(1, RouteKeyboardEnum.CRAMValue))
            })
        }, k7 = {
            KeyX("FM2", onClick = {
                listCommand.clear()
                listCommandAddToIndex(0, "FM2")
                list[selectIndex] = listCommandToText()
                routeTo(RouteKeyboard(1, RouteKeyboardEnum.FMValue))
            })
        },

            k8 = {
                KeyX("GOTO", onClick = {
                    listCommand.clear()
                    listCommandAddToIndex(0, "GOTO")
                    list[selectIndex] = listCommandToText()
                    routeTo(RouteKeyboard(1, RouteKeyboardEnum.NUMBER))
                })
            },

            k9 = {
                KeyX("IF", onClick = {
                    listCommand.clear()
                    listCommandAddToIndex(0, "IF")
                    list[selectIndex] = listCommandToText()
                    routeTo(RouteKeyboard(1, RouteKeyboardEnum.FPADFM))
                })
            },

            k10 = { KeyX("ELSE", onClick = { list[selectIndex] = "ELSE" }) }, k11 = {
                KeyX("PLUS", onClick = {


                })
            }, k12 = {
                KeyX("DELAY", onClick = {
                    listCommand.clear()
                    listCommandAddToIndex(0, "DELAY")
                    list[selectIndex] = listCommandToText()
                    routeTo(RouteKeyboard(1, RouteKeyboardEnum.NUMBER))
                })
            }, k13 = { KeyX("ENDIF", onClick = { list[selectIndex] = "ENDIF" }) }, k14 = {
                KeyX("LOAD", onClick = {


                })
            }, k15 = {
                KeyX("MINUS", onClick = {

                })
            })

    }


    //Экран числовой клавиатуры
    @Composable
    fun ScreenNUMBERPAD(arg: Int) {

        while (listCommand.lastIndex < arg) {
            listCommand.add("")
        }

        var s: String = listCommand[arg]

        list[selectIndex] = listCommandToText()

        Draw(k0 = {
            KeyX("1", onClick = {
                s += "1"
                listCommandAddToIndex(arg, s)
                list[selectIndex] = listCommandToText()
            })


        }, k1 = {
            KeyX("2", onClick = {
                s += "2"
                listCommandAddToIndex(arg, s)
                list[selectIndex] = listCommandToText()
            })
        }, k2 = {
            KeyX("3", onClick = {
                s += "3"
                listCommandAddToIndex(arg, s)
                list[selectIndex] = listCommandToText()
            })
        }, k3 = {
            KeyX("DEL", onClick = {
                s = s.dropLast(1)
                listCommandAddToIndex(arg, s)
                list[selectIndex] = listCommandToText()
            })
        }, k4 = {
            KeyX("4", onClick = {
                s += "4"
                listCommandAddToIndex(arg, s)
                list[selectIndex] = listCommandToText()
            })
        }, k5 = {
            KeyX("5", onClick = {
                s += "5"
                listCommandAddToIndex(arg, s)
                list[selectIndex] = listCommandToText()
            })
        }, k6 = {
            KeyX("6", onClick = {
                s += "6"
                listCommandAddToIndex(arg, s)
                list[selectIndex] = listCommandToText()
            })
        }, k7 = { KeyBack() }, k8 = {
            KeyX("7", onClick = {
                s += "7"
                listCommandAddToIndex(arg, s)
                list[selectIndex] = listCommandToText()
            })
        }, k9 = {
            KeyX("8", onClick = {
                s += "8"
                listCommandAddToIndex(arg, s)
                list[selectIndex] = listCommandToText()
            })
        }, k10 = {
            KeyX("9", onClick = {
                s += "9"
                listCommandAddToIndex(arg, s)
                list[selectIndex] = listCommandToText()
            })
        }, k11 = { }, k12 = {
            KeyX(".", onClick = {
                s += "."
                listCommandAddToIndex(arg, s)
                list[selectIndex] = listCommandToText()
            })
        }, k13 = {
            KeyX("0", onClick = {
                s += "0"
                listCommandAddToIndex(arg, s)
                list[selectIndex] = listCommandToText()
            })
        }, k14 = { }, k15 = { KeyEnter() })
    }

    //Экран выбора регистра
    @Composable
    fun ScreenFPAD(arg: Int) {


        Draw(k0 = {
            KeyX("F1", onClick = {

                listCommandAddToIndex(arg, "F1")
                list[selectIndex] = listCommandToText()
                routeTo(RouteKeyboard(0, RouteKeyboardEnum.HOME))
                routeStack.clear()

            })
        }, k1 = {
            KeyX("F2", onClick = {

                listCommandAddToIndex(arg, "F2")
                list[selectIndex] = listCommandToText()
                routeTo(RouteKeyboard(0, RouteKeyboardEnum.HOME))
                routeStack.clear()

            })
        }, k2 = {
            KeyX("F3", onClick = {

                listCommandAddToIndex(arg, "F3")
                list[selectIndex] = listCommandToText()
                routeTo(RouteKeyboard(0, RouteKeyboardEnum.HOME))
                routeStack.clear()
            })
        }, k3 = {
            KeyBack()
        }, k4 = {
            KeyX("F4", onClick = {

                listCommandAddToIndex(arg, "F4")
                list[selectIndex] = listCommandToText()
                routeTo(RouteKeyboard(0, RouteKeyboardEnum.HOME))
                routeStack.clear()

            })
        }, k5 = {
            KeyX("F5", onClick = {

                listCommandAddToIndex(arg, "F5")
                list[selectIndex] = listCommandToText()
                routeTo(RouteKeyboard(0, RouteKeyboardEnum.HOME))
                routeStack.clear()

            })
        }, k6 = {
            KeyX("F6", onClick = {

                listCommandAddToIndex(arg, "F6")
                list[selectIndex] = listCommandToText()
                routeTo(RouteKeyboard(0, RouteKeyboardEnum.HOME))
                routeStack.clear()

            })
        }, k7 = { }, k8 = {
            KeyX("F7", onClick = {

                listCommandAddToIndex(arg, "F7")
                list[selectIndex] = listCommandToText()
                routeTo(RouteKeyboard(0, RouteKeyboardEnum.HOME))
                routeStack.clear()

            })
        }, k9 = {
            KeyX("F8", onClick = {

                listCommandAddToIndex(arg, "F8")
                list[selectIndex] = listCommandToText()
                routeTo(RouteKeyboard(0, RouteKeyboardEnum.HOME))
                routeStack.clear()

            })
        }, k10 = {
            KeyX("F9", onClick = {

                listCommandAddToIndex(arg, "F9")
                list[selectIndex] = listCommandToText()
                routeTo(RouteKeyboard(0, RouteKeyboardEnum.HOME))
                routeStack.clear()

            })
        }, k11 = { }, k12 = { }, k13 = {
            KeyX("F0", onClick = {

                listCommandAddToIndex(arg, "F0")
                list[selectIndex] = listCommandToText()
                routeTo(RouteKeyboard(0, RouteKeyboardEnum.HOME))
                routeStack.clear()

            })
        }, k14 = { }, k15 = { })
    }

    //Экран выбора регистра
    @Composable
    fun ScreenFPADFM(arg: Int) {
        Draw(k0 = {
            KeyX("F1", onClick = {
                listCommandRemoveToIndex(arg)
                listCommandAddToIndex(arg, "F1")
                list[selectIndex] = listCommandToText()
                routeTo(RouteKeyboard(arg + 1, RouteKeyboardEnum.Comparison))
            })
        }, k1 = {
            KeyX("F2", onClick = {
                listCommandRemoveToIndex(arg)
                listCommandAddToIndex(arg, "F2")
                list[selectIndex] = listCommandToText()
                routeTo(RouteKeyboard(arg + 1, RouteKeyboardEnum.Comparison))
            })
        }, k2 = {
            KeyX("F3", onClick = {
                listCommandRemoveToIndex(arg)
                listCommandAddToIndex(arg, "F3")
                list[selectIndex] = listCommandToText()
                routeTo(RouteKeyboard(arg + 1, RouteKeyboardEnum.Comparison))
            })
        }, k3 = {

            KeyBack()

        }, k4 = {
            KeyX("F4", onClick = {
                listCommandRemoveToIndex(arg)
                listCommandAddToIndex(arg, "F4")
                list[selectIndex] = listCommandToText()
                routeTo(RouteKeyboard(arg + 1, RouteKeyboardEnum.Comparison))
            })
        }, k5 = {
            KeyX("F5", onClick = {
                listCommandRemoveToIndex(arg)
                listCommandAddToIndex(arg, "F5")
                list[selectIndex] = listCommandToText()
                routeTo(RouteKeyboard(arg + 1, RouteKeyboardEnum.Comparison))
            })
        }, k6 = {
            KeyX("F6", onClick = {
                listCommandRemoveToIndex(arg)
                listCommandAddToIndex(arg, "F6")
                list[selectIndex] = listCommandToText()
                routeTo(RouteKeyboard(arg + 1, RouteKeyboardEnum.Comparison))
            })
        }, k7 = { }, k8 = {
            KeyX("F7", onClick = {
                listCommandRemoveToIndex(arg)
                listCommandAddToIndex(arg, "F7")
                list[selectIndex] = listCommandToText()
                routeTo(RouteKeyboard(arg + 1, RouteKeyboardEnum.Comparison))
            })
        }, k9 = {
            KeyX("F8", onClick = {
                listCommandRemoveToIndex(arg)
                listCommandAddToIndex(arg, "F8")
                list[selectIndex] = listCommandToText()
                routeTo(RouteKeyboard(arg + 1, RouteKeyboardEnum.Comparison))
            })
        }, k10 = {
            KeyX("F9", onClick = {
                listCommandRemoveToIndex(arg)
                listCommandAddToIndex(arg, "F9")
                list[selectIndex] = listCommandToText()
                routeTo(RouteKeyboard(arg + 1, RouteKeyboardEnum.Comparison))
            })
        }, k11 = { }, k12 = { }, k13 = {
            KeyX("F0", onClick = {
                listCommandRemoveToIndex(arg)
                listCommandAddToIndex(arg, "F0")
                list[selectIndex] = listCommandToText()
                routeTo(RouteKeyboard(arg + 1, RouteKeyboardEnum.Comparison))
            })
        }, k14 = { }, k15 = { })
    }

    @Composable
    fun ScreenComparison(arg: Int) {
        Draw(k0 = {
            KeyX("<", onClick = {
                listCommandRemoveToIndex(arg)
                listCommandAddToIndex(arg, "<")
                list[selectIndex] = listCommandToText()
                routeTo(RouteKeyboard(arg + 1, RouteKeyboardEnum.IFValue))
            })
        },
            k1 = {
                KeyX(">", onClick = {
                    listCommandRemoveToIndex(arg)
                    listCommandAddToIndex(arg, ">")
                    list[selectIndex] = listCommandToText()
                    routeTo(RouteKeyboard(arg + 1, RouteKeyboardEnum.IFValue))
                })
            },
            k2 = { },
            k3 = { KeyBack() },
            k4 = {
                KeyX("<=", onClick = {
                    listCommandRemoveToIndex(arg)
                    listCommandAddToIndex(arg, "<=")
                    list[selectIndex] = listCommandToText()
                    routeTo(RouteKeyboard(arg + 1, RouteKeyboardEnum.IFValue))
                })
            },
            k5 = {
                KeyX(">=", onClick = {
                    listCommandRemoveToIndex(arg)
                    listCommandAddToIndex(arg, ">=")
                    list[selectIndex] = listCommandToText()
                    routeTo(RouteKeyboard(arg + 1, RouteKeyboardEnum.IFValue))
                })
            },
            k6 = { },
            k7 = { },
            k8 = {
                KeyX("==", onClick = {
                    listCommandRemoveToIndex(arg)
                    listCommandAddToIndex(arg, "==")
                    list[selectIndex] = listCommandToText()
                    routeTo(RouteKeyboard(arg + 1, RouteKeyboardEnum.IFValue))
                })
            },
            k9 = {
                KeyX("!=", onClick = {
                    listCommandRemoveToIndex(arg)
                    listCommandAddToIndex(arg, "!=")
                    list[selectIndex] = listCommandToText()
                    routeTo(RouteKeyboard(arg + 1, RouteKeyboardEnum.IFValue))
                })
            },
            k10 = { },
            k11 = { KeyBlank() },
            k12 = { },
            k13 = { },
            k14 = { },
            k15 = { KeyBlank() })

    }

    @Composable
    fun ScreenONOFF(arg: Int) {
        Draw(k0 = {
            KeyX("ON", onClick = {
                listCommandAddToIndex(arg, "ON")
                list[selectIndex] = listCommandToText()
                routeTo(RouteKeyboard(0, RouteKeyboardEnum.HOME))
                routeStack.clear()
            })
        },
            k1 = {
                KeyX("OFF", onClick = {
                    listCommandAddToIndex(arg, "OFF")
                    list[selectIndex] = listCommandToText()
                    routeTo(RouteKeyboard(0, RouteKeyboardEnum.HOME))
                    routeStack.clear()
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
    fun ScreenCRAMFM(arg: Int) {
        Draw(k0 = {
            KeyX("CR", onClick = {
                listCommandAddToIndex(arg, "CR")
                list[selectIndex] = listCommandToText()
                routeTo(RouteKeyboard(arg + 1, RouteKeyboardEnum.ONOFF))
            })
        },
            k1 = {
                KeyX("AM", onClick = {
                    listCommandAddToIndex(arg, "AM")
                    list[selectIndex] = listCommandToText()
                    routeTo(RouteKeyboard(arg + 1, RouteKeyboardEnum.ONOFF))
                })
            },
            k2 = {
                KeyX("FM", onClick = {
                    listCommandAddToIndex(arg, "FM")
                    list[selectIndex] = listCommandToText()
                    routeTo(RouteKeyboard(arg + 1, RouteKeyboardEnum.ONOFF))
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
    fun ScreenFMValue(arg: Int) {
        Draw(k0 = {
            KeyX("FR Fx", onClick = {

                listCommandAddToIndex(arg, "FR")
                list[selectIndex] = listCommandToText()
                routeTo(RouteKeyboard(arg + 1, RouteKeyboardEnum.F))

            })
        },
            k1 = {
                KeyX("FR xx", onClick = {
                    listCommandAddToIndex(arg, "FR")
                    list[selectIndex] = listCommandToText()
                    routeTo(RouteKeyboard(arg + 1, RouteKeyboardEnum.NUMBER))
                })
            },
            k2 = { },
            k3 = { KeyBack() },
            k4 = { KeyX("MOD", onClick = {}) },
            k5 = { },
            k6 = { },
            k7 = { },
            k8 = {
                KeyX("BASE Fx", onClick = {
                    listCommandAddToIndex(arg, "BASE")
                    list[selectIndex] = listCommandToText()
                    routeTo(RouteKeyboard(arg + 1, RouteKeyboardEnum.F))
                })
            },
            k9 = {
                KeyX("BASE xx", onClick = {
                    listCommandAddToIndex(arg, "BASE")
                    list[selectIndex] = listCommandToText()
                    routeTo(RouteKeyboard(arg + 1, RouteKeyboardEnum.NUMBER))
                })
            },
            k10 = { },
            k11 = { },
            k12 = {
                KeyX("DEV Fx", onClick = {
                    listCommandAddToIndex(arg, "DEV")
                    list[selectIndex] = listCommandToText()
                    routeTo(RouteKeyboard(arg + 1, RouteKeyboardEnum.F))
                })
            },
            k13 = {
                KeyX("DEV xx", onClick = {
                    listCommandAddToIndex(arg, "DEV")
                    list[selectIndex] = listCommandToText()
                    routeTo(RouteKeyboard(arg + 1, RouteKeyboardEnum.NUMBER))
                })
            },
            k14 = { },
            k15 = { })
    }

    @Composable
    fun ScreenIFValue(arg: Int) {
        Draw(k0 = {
            KeyX("Fx", onClick = { routeTo(RouteKeyboard(arg, RouteKeyboardEnum.F)) })
        },
            k1 = {
                KeyX("xxxx.x", onClick = {
                    routeTo(
                        RouteKeyboard(
                            arg, RouteKeyboardEnum.NUMBER
                        )
                    )
                })
            },
            k2 = { },
            k3 = { KeyBack() },
            k4 = {

            },
            k5 = { },
            k6 = { },
            k7 = { KeyBlank() },
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
    fun ScreenCRAMValue(arg: Int) {
        Draw(k0 = {
            KeyX("FR Fx", onClick = {
                listCommandRemoveToIndex(arg)
                listCommandAddToIndex(arg, "FR")
                list[selectIndex] = listCommandToText()
                routeTo(RouteKeyboard(arg + 1, RouteKeyboardEnum.F))
            })
        },
            k1 = {
                KeyX("FR xx.x", onClick = {
                    listCommandRemoveToIndex(arg)
                    listCommandAddToIndex(arg, "FR")
                    list[selectIndex] = listCommandToText()
                    routeTo(RouteKeyboard(arg + 1, RouteKeyboardEnum.NUMBER))
                })
            },
            k2 = { },
            k3 = { },
            k4 = {
                KeyX("MOD", onClick = {
                    listCommandAddToIndex(arg, "MOD")
                    list[selectIndex] = listCommandToText()
                })
            },
            k5 = { },
            k6 = { },
            k7 = { },
            k8 = { },
            k9 = { },
            k10 = { },
            k11 = { KeyBack() },
            k12 = { },
            k13 = { },
            k14 = { },
            k15 = { KeyEnter() })
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Composable
    fun KeyEnter() {
        KeyX("DONE", onClick = {
            routeTo(RouteKeyboard(0, RouteKeyboardEnum.HOME))
            routeStack.clear()
        })
    }

    @Composable
    fun KeyBack() {

        KeyX("<-", onClick = {
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





