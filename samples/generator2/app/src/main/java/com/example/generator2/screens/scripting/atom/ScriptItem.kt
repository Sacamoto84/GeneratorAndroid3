package com.example.generator2.screens.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.generator2.R
import java.text.NumberFormat
import java.text.ParsePosition

data class PairTextAndColor(
    var text: String,
    var colorText: Color,
    var colorBg: Color,
    var bold: Boolean = false,
    var italic: Boolean = false,
    var underline: Boolean = false,
    var flash: Boolean = false,
    var textSize: TextUnit = 16.sp
)

class ScriptItem {

    private var pairList = mutableStateListOf<PairTextAndColor>()

    @Composable
    fun Draw(str: () -> String, index: () -> Int, select: () -> Boolean) {

        println("Draw")

        convertStringToPairTextAndColor(str(), index())

        Box(
            modifier = Modifier.fillMaxWidth().background(if (select()) Color.Blue else Color.Transparent)
        )
        {
            val s = pairList.size
            Row() {

                for (i in 0 until s) {
                    Box(
                        modifier = Modifier.padding(1.dp).height(24.dp)
                            .clip(shape = RoundedCornerShape(8.dp))
                            .background(pairList[i].colorBg)

                    ) {


                        Text(
                            modifier = Modifier.padding(start = 4.dp, top = 2.dp, end = 4.dp)
                                .offset(x = 0.dp, y = (-2).dp),
                            text = pairList[i].text,
                            color = pairList[i].colorText,
                            textDecoration = if (pairList[i].underline) TextDecoration.Underline else null,
                            fontWeight = if (pairList[i].bold) FontWeight.Bold else null,
                            fontStyle = if (pairList[i].italic) FontStyle.Italic else null,
                            fontSize = pairList[i].textSize,
                            fontFamily = FontFamily(
                                Font(R.font.jetbrains, FontWeight.Normal)
                            )
                        )


                    }


                }
            }
        }
    }

    fun isNumeric(s: String): Boolean {
        val pos = ParsePosition(0)
        NumberFormat.getInstance().parse(s, pos)
        return s.length == pos.index
    }

    //Конвертируем строку и индекс в красивый вид
    private fun convertStringToPairTextAndColor(str: String, index: Int) {

        pairList.clear()

        pairList.add(
            PairTextAndColor(
                text = "$index", colorText = Color.Black, colorBg = Color.White
            )
        )

        //Разобрать строку на список команд
        val listCMD = str.split(" ")
        if (listCMD.isEmpty()) {
            println("convertStringToPairTextAndColor: Error listCMD == 0")
            return
        }

        /////////////////////
        when (listCMD[0]) {

            "CH1", "CH2" -> pairList.add(
                PairTextAndColor(
                    text = listCMD[0], colorText = Color(0xFFF5FFFF), colorBg = Color(0xFF1976D2)
                )
            )

            "CR1", "CR2" -> pairList.add(
                PairTextAndColor(
                    text = listCMD[0], colorText = Color(0xFFF5FFFF), colorBg = Color(0xFFB388FF)
                )
            )

            "AM1", "AM2" -> pairList.add(
                PairTextAndColor(
                    text = listCMD[0], colorText = Color.Green, colorBg = Color.Magenta
                )
            )

            "LOAD"       -> pairList.add(
                PairTextAndColor(
                    text = listCMD[0], colorText = Color.Green, colorBg = Color.Magenta
                )
            )

            "GOTO"       -> pairList.add(
                PairTextAndColor(
                    text = listCMD[0], colorText = Color(0xFF2A2A1E), colorBg = Color(0xFF64FFDA)
                )
            )


            "DELAY"      -> pairList.add(
                PairTextAndColor(
                    text = listCMD[0],
                    colorText = Color(0xFFF5FFFF),
                    colorBg = Color(0xFF1976D2),
                    bold = true
                )
            )

            else         -> {
                pairList.add(
                    PairTextAndColor(
                        text = listCMD[0],
                        colorText = Color.Black,
                        colorBg = Color.Green,
                        bold = true
                    )
                )
            }

        } /////////////////////////////////////

        if (listCMD.size >= 2) {

            if (!isNumeric(listCMD[1])) {
                when (listCMD[1]) {

                    "CR", "AM", "FM" -> pairList.add(
                        PairTextAndColor(
                            text = listCMD[1], colorText = Color.Green, colorBg = Color.Magenta
                        )
                    )

                    else             -> {

                        pairList.add(
                            PairTextAndColor(
                                text = listCMD[1], colorText = Color.Black, colorBg = Color.Green
                            )
                        )
                    }


                }
            } else {
                pairList.add(
                    PairTextAndColor(
                        text = listCMD[1],
                        colorText = Color(0xFF2B2B2B),
                        colorBg = Color(0xFF81D4FA)
                    )
                )
            }


        } /////////////////////////////////////////////////////
        if (listCMD.size >= 3) {


            if (!isNumeric(listCMD[2])) {

                when (listCMD[2]) {

                    "ON"  -> pairList.add(
                        PairTextAndColor(
                            text = listCMD[2], colorText = Color.Black, colorBg = Color.Green
                        )
                    )

                    "OFF" -> pairList.add(
                        PairTextAndColor(
                            text = listCMD[2], colorText = Color.White, colorBg = Color.Blue
                        )
                    )

                    else  -> {
                        pairList.add(
                            PairTextAndColor(
                                text = listCMD[2], colorText = Color.Black, colorBg = Color.Green
                            )
                        )
                    }
                }
            } else {

                pairList.add(
                    PairTextAndColor(
                        text = listCMD[2],
                        colorText = Color(0xFF2B2B2B),
                        colorBg = Color(0xFF81D4FA)
                    )
                )
            }
        }

        ///////////////////////////////
        if (listCMD.size >= 4) {

            if (!isNumeric(listCMD[3])) {

                when (listCMD[3]) {


                    "CH1", "CH2" -> pairList.add(
                        PairTextAndColor(
                            text = listCMD[3], colorText = Color.Green, colorBg = Color.Magenta
                        )
                    )

                    else         -> {
                        pairList.add(
                            PairTextAndColor(
                                text = listCMD[3], colorText = Color.Black, colorBg = Color.Green
                            )
                        )
                    }
                }

            } else {
                pairList.add(
                    PairTextAndColor(
                        text = listCMD[3],
                        colorText = Color(0xFF2B2B2B),
                        colorBg = Color(0xFF81D4FA)
                    )
                )

            }
        }

        ////////////////////////////


    }

}

//@Preview
//@Composable
//fun Xxx_preview() {
//    val items = ScriptItem()
//    Column() {
//        items.Draw("CH1 CR ON")
//        items.Draw("CH1 CR ON")
//        items.Draw("CH1 CR ON")
//        items.Draw("CH1 CR ON")
//    }
//}