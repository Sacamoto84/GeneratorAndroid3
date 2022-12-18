package com.example.generator2.screens.scripting.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.generator2.screens.mainscreen4.VMMain4
import com.example.generator2.screens.scripting.VMScripting


//Блок регистров
@Composable
fun RegisterViewDraw(modifier: Modifier = Modifier, global: VMScripting) {
    Box(
        modifier = Modifier.padding(start = 6.dp, end = 6.dp)
            .fillMaxWidth() //.background(Color.Red)
            //.border(width = 2.dp, color = Color.White, shape = RoundedCornerShape(8.dp))
            //.wrapContentHeight()
            .then(modifier)
    ) {
        Column(
            Modifier.height(50.dp)
        ) {
            Row(modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                repeat(5) {
                    ComposeBoxForF(it, Modifier.weight(1f), global = global)
                }
            }

            Row(modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                repeat(5) {
                    ComposeBoxForF(it + 5, Modifier.weight(1f), global = global)
                }
            }
        }
    }
}

//Ячейка регистра
@Composable
private fun ComposeBoxForF(index: Int, modifier: Modifier = Modifier, global: VMScripting) {

    Box(
        modifier = Modifier.padding(start = 1.dp, end = 1.dp).height(25.dp).fillMaxWidth()
            .border(1.dp, Color.LightGray, RoundedCornerShape(4.dp))
            .then(modifier) //, contentAlignment = Alignment.CenterStart

    ) {

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier.width(12.dp).height(25.dp).background(Color.DarkGray),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "$index",
                    color = Color.White,
                    fontSize = 14.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }

            Text(
                text = "${global.hub.script.f[index]}",
                color = Color.White,
                fontSize = 14.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    }
}