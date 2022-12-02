package com.example.generator2.screens.editor.dialog

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import colorDarkBackground
import com.example.generator2.R
import com.example.generator2.screens.editor.ComboBox
import com.example.generator2.screens.editor.ui.model
import com.example.generator2.vm.Global


val comboboxLine = ComboBox(listOf("8", "16", "32", "128 ", "256", "512"))
val comboboxRow = ComboBox(listOf("8", "16", "32", "128 ", "256", "512"))

@Composable
fun DialogNew(openDialog: MutableState<Boolean>, global: Global) {

    val context = LocalContext.current
    var value by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }

    if (openDialog.value) Dialog(onDismissRequest = { openDialog.value = false }) {

        Card(
            Modifier, elevation = 0.dp, border = BorderStroke(
                1.dp, Color.Gray
            ), shape = RoundedCornerShape(8.dp), backgroundColor = colorDarkBackground
        ) {

            Column(Modifier.padding(16.dp).width(210.dp)) {


                Row(verticalAlignment = Alignment.CenterVertically) {

                    Icon(
                        painter = painterResource(R.drawable.row_triple),
                        contentDescription = null,
                        modifier = Modifier.size(32.dp)
                    )

                    Text(
                        text = " Row",
                        color = Color.White,
                        fontSize = 24.sp,
                        modifier = Modifier.width(100.dp)
                    )
                    comboboxLine.Draw()

                }

                Spacer(modifier = Modifier.height(16.dp))
                Row() {

                    Icon(
                        painter = painterResource(R.drawable.column_triple),
                        contentDescription = null,
                        modifier = Modifier.size(32.dp)
                    )
                    Text(
                        text = " Column",
                        color = Color.White,
                        fontSize = 24.sp,
                        modifier = Modifier.width(100.dp)
                    )
                    comboboxRow.Draw()
                }

                Spacer(modifier = Modifier.height(16.dp))


                Button(onClick = {
                    model.reinit(
                        _editMax = comboboxLine.items[comboboxLine.selectedIndex].toInt(),
                        comboboxRow.items[comboboxRow.selectedIndex].toInt()
                    )

                    openDialog.value = false

                }, modifier = Modifier.fillMaxWidth()) {
                    Text("Set")
                }


            }
        }


    }

}




