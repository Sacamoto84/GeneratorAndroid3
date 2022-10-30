package com.example.generator2.scripting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.generator2.R

class scriptCore {

    var SP = 0
    val scriptlist = mutableListOf<String>()


}

val instructionFamily = FontFamily(
    Font(R.font.instruction, FontWeight.Light),
)


var textState =  mutableStateOf(TextFieldValue())

@Composable
fun ScriptActivity() {

    val scriptlist = mutableListOf<String>()
    scriptlist.add("1")
    scriptlist.add("2")
    scriptlist.add("3")
    scriptlist.add("4")



    Column(modifier = Modifier.fillMaxSize()) {
        //1
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
                .background(Color.Green)
        )
        {
        }
        //2
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .background(Color.Black)
        )
        {

            val str = remember { mutableStateOf("Hello") }



            OutlinedTextField(
                value = textState.value,
                onValueChange = { textState.value = it },
                modifier = Modifier.fillMaxSize(),
                textStyle = TextStyle(
                    color = Color.Green,
                    fontFamily = FontFamily(
                        Font(R.font.instruction, FontWeight.Normal),
                    )
                )
            )

        }
        //3
        StriptKeyboard()
    }
}


//Нижняя клавиатура
@Composable
fun StriptKeyboard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(350.dp)
            .background(Color.Red)
    )
    {

        val text = textState.value.text
       Button(onClick = { textState.value = TextFieldValue(text = text+"ttttt") }) {

       }

    }
}