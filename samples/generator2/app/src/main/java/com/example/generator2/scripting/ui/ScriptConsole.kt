package com.example.generator2.scripting.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.selectable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.generator2.Global
import libs.MToast

class ScriptConsole(private val list: SnapshotStateList<String>, private val selectLine: Int) {


    @Composable
    fun Draw(modifier: Modifier = Modifier) {

        val indexSelect = remember {
            mutableStateOf(selectLine)
        }
        indexSelect.value = selectLine

        val lazyListState: LazyListState = rememberLazyListState()
        Box(
            Modifier
                .fillMaxSize()
                .background(Color(0xFF090909))
                .then(modifier), contentAlignment = Alignment.CenterStart
        )
        {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(), state = lazyListState
            ) {
                itemsIndexed(list.toList())
                { index, item ->
                    Row(
                        //Modifier.background(Color.Magenta)
                        horizontalArrangement = Arrangement.Start
                    )
                    {

                        Box(modifier = Modifier.selectable(
                            selected = selectLine == index,
                            onClick = {
                                //indexSelect.value = index
                                Global.script.pc.value = index
                                MToast(Global.contextActivity!!, text = "$index")
                            }
                        ))
                        {

                            ScriptItem().Draw(str = item, index = index, indexSelect.value == index)

                        }

                    }
                }
            }
        }
    }
}