package com.example.generator2.mainscreen4

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import colorDarkBackground

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
            contentColor = Color.White
        ),
        border = BorderStroke(1.dp, Color.LightGray)
    ) {
        Text(str)
    }

}
