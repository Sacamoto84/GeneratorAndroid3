package com.example.generator2.scripting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


//Клавиатурка
class scriptKeyboard {

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


}

@Preview
@Composable
fun keyboard_preview() {

    val keyboard = scriptKeyboard()
    keyboard.Draw(
        k0 = { keyCH1() },
        k1 = { keyCR1() },
        k2 = { keyAM1() },
        k3 = { keyFM1() },
        k4 = { keyCH2() },
        k5 = { keyCR2() },
        k6 = { keyAM2() },
        k7 = { keyFM2() },
        k8 = { keyGOTO() },
        k9 = { keyIF() },
        k10 = { keyELSE() },
        k11 = { keyBackspase() },
        k12 = { keyDELAY() },
        k13 = { keyENDIF() },
        k14 = { keyLOAD() },
        k15 = { keyEnter() })
}


@Composable
fun keyCH1() {
    Button(onClick = { /*TODO*/ }) {
        Text("CH1")
    }
}

@Composable
fun keyCH2() {
    Button(onClick = { /*TODO*/ }) {
        Text("CH2")
    }
}

@Composable
fun keyCR1() {
    Button(onClick = { /*TODO*/ }) {
        Text("CR1")
    }
}

@Composable
fun keyCR2() {
    Button(onClick = { /*TODO*/ }) {
        Text("CR2")
    }
}

@Composable
fun keyAM1() {
    Button(onClick = { /*TODO*/ }) {
        Text("AM1")
    }
}

@Composable
fun keyAM2() {
    Button(onClick = { /*TODO*/ }) {
        Text("AM2")
    }
}

@Composable
fun keyFM1() {
    Button(onClick = { /*TODO*/ }) {
        Text("FM1")
    }
}

@Composable
fun keyFM2() {
    Button(onClick = { /*TODO*/ }) {
        Text("FM2")
    }
}

@Composable
fun keyBackspase() {
    Button(onClick = { /*TODO*/ }) {
        Text("<")
    }
}

@Composable
fun keyEnter() {
    Button(onClick = { /*TODO*/ }) {
        Text("E")
    }
}

@Composable
fun keyGOTO() {
    Button(onClick = { /*TODO*/ }) {
        Text("GOTO")
    }
}

@Composable
fun keyDELAY() {
    Button(onClick = { /*TODO*/ }) {
        Text("DELAY")
    }
}

@Composable
fun keyIF() {
    Button(onClick = { /*TODO*/ }) {
        Text("IF")
    }
}


@Composable
fun keyELSE() {
    Button(onClick = { /*TODO*/ }) {
        Text("ELSE")
    }
}

@Composable
fun keyENDIF() {
    Button(onClick = { /*TODO*/ }) {
        Text("ENDIF")
    }
}

@Composable
fun keyLOAD() {
    Button(onClick = { /*TODO*/ }) {
        Text("LOAD")
    }
}

