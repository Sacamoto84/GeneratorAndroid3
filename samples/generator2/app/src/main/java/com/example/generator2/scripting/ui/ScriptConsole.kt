package com.example.generator2.scripting.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.generator2.Global
import libs.MToast
import kotlin.random.Random

@Composable
fun ScriptConsole(list: SnapshotStateList<String>, selectLine: Int, modifier: Modifier = Modifier) {

    println("ScriptConsole selectLine:$selectLine" )

    val indexSelect = remember { mutableStateOf(selectLine)  }

    indexSelect.value = selectLine

    val l = list.toList()

    if (indexSelect.value > l.lastIndex)
        indexSelect.value = l.lastIndex

    if (indexSelect.value == 0) {
        indexSelect.value = 1
        Global.script.pc.value = 1
    }

    val lazyListState: LazyListState = rememberLazyListState()
    Box(
        Modifier.fillMaxSize().background(Color(0xFF090909)).then(modifier),
        contentAlignment = Alignment.CenterStart
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(), state = lazyListState
        ) {
            itemsIndexed(
                l,
            ) { index, item ->
                Row( //Modifier.background(Color.Magenta)
                    horizontalArrangement = Arrangement.Start
                ) {
                    Box(
                        modifier = Modifier.selectable(
                            selected = indexSelect.value == index,
                            onClick = { Global.script.pc.value = index })
                    ) {

                        //if (indexSelect.value == 0)
                        //    indexSelect.value = 1

                        val select = indexSelect.value == index

                        ScriptItem().Draw( str = item, index = index, select )


                    }
                }
            }
        }
    }

    println("ScriptConsole..end" )
}

