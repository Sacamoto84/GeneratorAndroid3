package com.example.generator2.screens.scripting.dialog

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.generator2.R
import com.example.generator2.screens.mainscreen4.VMMain4
import com.example.generator2.screens.ui.refresh
import com.example.generator2.theme.colorDarkBackground
import com.example.generator2.theme.colorLightBackground
import libs.MToast


@Composable
fun DialogDeleteRename(openDialog: MutableState<Boolean>, name: String,   global: VMMain4) {

    val context = LocalContext.current

    println("DialogDeleteRename name:$name")

    var value by remember { mutableStateOf("") }

    value = name

    //var valueDelete by remember { mutableStateOf("") }
    if (openDialog.value) Dialog(onDismissRequest = { openDialog.value = false }) {
        Card(
            Modifier.width(220.dp), elevation = 8.dp, border = BorderStroke(
                1.dp, Color.Gray
            ), shape = RoundedCornerShape(36.dp), backgroundColor = colorDarkBackground
        ) {

            Column {

                Button(
                    onClick = {
                        global.hub.utils.deleteScriptFile(name)
                        openDialog.value = false
                        refresh.value++
                    },
                    modifier = Modifier.fillMaxWidth().height(88.dp)
                        .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 16.dp),
                    shape = RoundedCornerShape(36.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
                ) {
                    Text(
                        "Delete",
                        fontSize = 28.sp,
                        color = Color.White,
                        modifier = Modifier.offset(0.dp, (0).dp)
                    )
                }

                Divider(color = Color.Gray, thickness = 2.dp)

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = value,
                    onValueChange = { value = it },
                    modifier = Modifier.height(72.dp)
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
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

                        global.hub.utils.renameScriptFile(name, value)

                        openDialog.value = false
                        MToast(context, "Переименовали")

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
