package com.example.generator2.scripting.ui

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

    var text : String = ""
    private var pairList = mutableStateListOf<PairTextAndColor>()

    init {
        pairList.add(
            PairTextAndColor(
                text = "1",
                colorText = Color.Black,
                colorBg = Color.White
            )
        )

        pairList.add(
            PairTextAndColor(
                text = "2",
                colorText = Color.Green,
                colorBg = Color.White
            )
        )
    }

    @Composable
    fun Draw(str: String, index: Int = 0) {
        convertStringToPairTextAndColor(str, index)
        Box(
            modifier = Modifier
                .width(200.dp)
                .wrapContentSize()
                .background(Color.DarkGray)
        )
        {
            val s = pairList.size
            Row()
            {
                for (i in 0 until s) {
                    Box(
                        modifier = Modifier
                            .wrapContentSize()
                            .clip(shape = RoundedCornerShape(8.dp))
                            .background(pairList[i].colorBg)
                    )
                    {
                        Text(
                            modifier = Modifier.padding(4.dp),
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


    //Конвертируем строку и индекс в красивый вид
    private fun convertStringToPairTextAndColor(str: String, index: Int) {

        text = "I=$index $str"

        pairList.clear()

        pairList.add(
            PairTextAndColor(
                text = "I=$index",
                colorText = Color.Black,
                colorBg = Color.White
            )
        )

        //Разобрать строку на список команд
        val listCMD = str.split(" ")
        if (listCMD.isEmpty()) {
            println("convertStringToPairTextAndColor: Error listCMD == 0")
            return
        }


        when (listCMD[0]) {

            "CH1", "CH2" -> pairList.add(
                PairTextAndColor(
                    text = listCMD[0],
                    colorText = Color.Black,
                    colorBg = Color.Magenta
                )
            )

            "CR1", "CR2" -> pairList.add(
                PairTextAndColor(
                    text = listCMD[0],
                    colorText = Color.Green,
                    colorBg = Color.Magenta
                )
            )

            "AM1", "AM2" -> pairList.add(
                PairTextAndColor(
                    text = listCMD[0],
                    colorText = Color.Green,
                    colorBg = Color.Magenta
                )
            )

            else -> {
                PairTextAndColor(
                    text = listCMD[0],
                    colorText = Color.Black,
                    colorBg = Color.White
                )
            }

        }

        if (listCMD.size > 1)
            when (listCMD[1]) {

                "CR", "AM", "FM" -> pairList.add(
                    PairTextAndColor(
                        text = listCMD[1],
                        colorText = Color.Green,
                        colorBg = Color.Magenta
                    )
                )

                else -> {
                    PairTextAndColor(
                        text = listCMD[1],
                        colorText = Color.Black,
                        colorBg = Color.White
                    )
                }
            }

        if (listCMD.size > 2)
            when (listCMD[2]) {

                "ON" -> pairList.add(
                    PairTextAndColor(
                        text = listCMD[2],
                        colorText = Color.Black,
                        colorBg = Color.Green
                    )
                )

                "OFF" -> pairList.add(
                    PairTextAndColor(
                        text = listCMD[2],
                        colorText = Color.White,
                        colorBg = Color.Blue
                    )
                )

                else -> {}
            }


        if (listCMD.size > 3)
            when (listCMD[3]) {

                "CH1", "CH2" -> pairList.add(
                    PairTextAndColor(
                        text = listCMD[0],
                        colorText = Color.Green,
                        colorBg = Color.Magenta
                    )
                )

                else -> {}
            }

    }


}

@Preview
@Composable
fun Xxx_preview() {
    val items = ScriptItem()
    Column() {
        items.Draw("CH1 CR ON")
        items.Draw("CH1 CR ON")
        items.Draw("CH1 CR ON")
        items.Draw("CH1 CR ON")
    }

}