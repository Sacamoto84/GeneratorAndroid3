package com.example.generator2.mainscreen4

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import colorDarkBackground
import com.example.generator2.R

//Шаблон на 6 кнопок
@Composable
fun TemplateBottomBar6Key(
    key0: (@Composable () -> Unit)? = null,
    key1: (@Composable () -> Unit)? = null,
    key2: (@Composable () -> Unit)? = null,
    key3: (@Composable () -> Unit)? = null,
    key4: (@Composable () -> Unit)? = null,
    key5: (@Composable () -> Unit)? = null,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(horizontalArrangement = Arrangement.SpaceAround) {

            if (key0 != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
                {
                    key0()
                }
            }

            if (key1 != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
                {
                    key1()
                }
            }

            if (key2 != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
                {
                    key2()
                }
            }
        }
        Row() {

            if (key3 != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
                {
                    key3()
                }
            }

            if (key4 != null) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
                {
                    key4()
                }
            }

            if (key5 != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
                {
                    key5()
                }
            }
        }
    }
}

@Composable
fun TemplateButtonBottomBar(
    modifier: Modifier = Modifier,
    str: String = "?",
    onClick: () -> Unit = {},
    backgroundColor: Color = colorDarkBackground,
    contentColor: Color = Color.White
) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
            //.weight(1f)
            .padding(start = 8.dp, end = 4.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            backgroundColor = backgroundColor,
            contentColor = contentColor
        ),
        border = BorderStroke(1.dp, Color.LightGray),contentPadding = PaddingValues(2.dp)
    ) {
        Text(str, maxLines = 2, fontFamily = FontFamily(Font(R.font.jetbrains)), fontSize = 14.sp)
    }

}
