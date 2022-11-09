package com.example.generator2.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import colorDarkBackground
import colorLightBackground
import com.example.generator2.Global
import com.example.generator2.R
import com.example.generator2.filesInDirToList
import kotlinx.coroutines.delay
import libs.MToast

@Composable
fun DialogSaveAs(openDialog :  MutableState<Boolean>) {

    val context = LocalContext.current
    var value by remember { mutableStateOf("")}
    val focusRequester = remember { FocusRequester() }

    if (openDialog.value) Dialog(onDismissRequest = { openDialog.value = false }) {

        Card(
            Modifier.height(400.dp).width(220.dp), elevation = 8.dp, border = BorderStroke(
                1.dp, Color.Gray
            ), shape = RoundedCornerShape(36.dp), backgroundColor = colorDarkBackground
        ) {

            LaunchedEffect(Unit) {
                delay(500)
                focusRequester.requestFocus()
            }

            Column() {
                Text(
                    text = "Сохранить как",
                    modifier = Modifier.fillMaxWidth()
                        .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 8.dp)
                        .clip(RoundedCornerShape(36.dp)).background(Color.DarkGray),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.jetbrains)),
                    color = Color.LightGray
                )

                val files = filesInDirToList(context, "/Script")

                Column(
                    Modifier.fillMaxSize().weight(1f).padding(4.dp).background(Color(0x8B1D1C1C))
                        .verticalScroll(rememberScrollState())
                ) {

                    Spacer(modifier = Modifier.height(4.dp))

                    for (index in files.indices) {
                        Text(
                            text = " " + files[index],
                            color = Color.DarkGray,
                            modifier = Modifier.fillMaxWidth()
                                .padding(start = 8.dp, top = 2.dp, end = 8.dp).clip(
                                    RoundedCornerShape(8.dp)
                                )
                                .background( Color.LightGray ),
                            fontFamily = FontFamily(Font(R.font.jetbrains)),
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = value,
                    onValueChange = { value = it },
                    modifier = Modifier.height(72.dp)
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                        .focusRequester(focusRequester),
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.LightGray,
                        leadingIconColor = Color.LightGray,
                        backgroundColor = colorLightBackground,
                        focusedIndicatorColor = Color.Transparent

                    ),
                    placeholder = { Text(text = "Имя файла", color = Color.Gray) },
                    singleLine = true,
                    shape = RoundedCornerShape(36.dp),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = {
                        Global.script.list[0] = value
                        Global.saveListToScript(value)
                        openDialog.value = false
                        MToast(context, "Сохранено")
                    }),
                    textStyle = TextStyle(
                        fontSize = 18.sp,
                        fontFamily = FontFamily(Font(R.font.jetbrains)),
                    ),
                    )
            }
        }
    }
}
