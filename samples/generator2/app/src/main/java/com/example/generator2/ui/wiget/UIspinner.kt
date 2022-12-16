package com.example.generator2.ui.wiget

import android.annotation.SuppressLint
import android.provider.Settings
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.generator2.itemList
import com.example.generator2.screens.mainscreen4.VMMain4
import com.example.generator2.vm.LiveData

object UIspinner {

    @SuppressLint("StateFlowValueCalledInComposition")
    @Composable
    fun Spinner(
        CH: String,
        Mod: String,
        transparrent: Boolean = false,
        modifier: Modifier = Modifier,
        global: VMMain4
    ) {

        val expanded = remember { mutableStateOf(false) }

        //Выбор с каким списком работать
        var itemlist: ArrayList<itemList> = LiveData.itemlistCarrier
        when (Mod) {
            "CR" -> itemlist = LiveData.itemlistCarrier
            "AM" -> itemlist = LiveData.itemlistAM
            "FM" -> itemlist = LiveData.itemlistFM
        }

        //Текущий текст
        var currentValue = "---"
        if (CH == "CH0") {
            when (Mod) {
                "CR" -> currentValue = LiveData.ch1_Carrier_Filename.value
                "AM" -> currentValue = LiveData.ch1_AM_Filename.value
                "FM" -> currentValue = LiveData.ch1_FM_Filename.value
            }
        } else {
            when (Mod) {
                "CR" -> currentValue = LiveData.ch2_Carrier_Filename.value
                "AM" -> currentValue = LiveData.ch2_AM_Filename.value
                "FM" -> currentValue = LiveData.ch2_FM_Filename.value
            }
        }

        //Индекс текущего битмапа
        val indexBitmapCurrent = remember { mutableStateOf(0) }
        itemlist.forEachIndexed { index, element ->
            if (element.name == currentValue) indexBitmapCurrent.value = index
        }

        if (itemlist.isEmpty()) return

        Box(
            modifier = Modifier
                //.fillMaxSize()
                .background(Color.Transparent)
                .then(modifier), contentAlignment = Alignment.Center
        )
        {
            Row(modifier = Modifier
                .clickable {
                    expanded.value = !expanded.value
                }
                .background(color = if (transparrent) Color(0x00000000) else Color(0xFF13161B)),
                //horizontalAlignment = Alignment.CenterHorizontally,
                verticalAlignment = Alignment.CenterVertically
                //,
                //.align(Alignment.Center)
            )
            {

                Image(
                    bitmap = itemlist[indexBitmapCurrent.value].bitmap.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier
                        .width(104.dp)
                        .height(48.dp)
                        .padding(start = 4.dp, end = 4.dp) //128 64
                )

//                        Text(text = currentValue, modifier = Modifier.fillMaxWidth().padding(start=8.dp).weight(1f)
//                            , color = Color(0xFFE7E1D5)
//                            , fontSize = 24.sp, textAlign = TextAlign.Center)

                //Icon(
                //    imageVector = Icons.Filled.ArrowDropDown,
                //    contentDescription = null,
                //    modifier = Modifier.width(8.dp), //32
                //    tint = Color(0xFFE7E1D5)
                //)

                DropdownMenu(
                    expanded = expanded.value,
                    onDismissRequest = {
                        expanded.value = false
                    },
                    modifier = Modifier.background(
                        color = if (transparrent) Color(0) else Color(0xFF454954)
                    )
                )
                {

                    itemlist.forEach {
                        DropdownMenuItem(
                            modifier = Modifier
                                .background(Color(0xFF454954))
                                .width(340.dp),
                            onClick = {
                                currentValue = it.name
                                expanded.value = false
                                if (CH == "CH0") {
                                    when (Mod) {
                                        "CR" -> LiveData.ch1_Carrier_Filename.value = currentValue
                                        "AM" -> LiveData.ch1_AM_Filename.value = currentValue
                                        "FM" -> LiveData.ch1_FM_Filename.value = currentValue
                                    }
                                } else {
                                    when (Mod) {
                                        "CR" -> LiveData.ch2_Carrier_Filename.value = currentValue
                                        "AM" -> LiveData.ch2_AM_Filename.value = currentValue
                                        "FM" -> LiveData.ch2_FM_Filename.value = currentValue

                                    }
                                }

                                //Utils.Spinner_Send_Buffer(
                                //    CH,
                                //    Mod,
                                //    it.name
                                //) //Читае м отсылаем массив
                            }
                        ) {
                            Image(
                                bitmap = it.bitmap.asImageBitmap(),
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(top = 4.dp, bottom = 4.dp, end = 4.dp)
                                    .height(64.dp)
                            )
                            Text(
                                text = it.name,
                                color = Color(0xFFE7E1D5),
                                fontSize = 24.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
        //}
    }

}