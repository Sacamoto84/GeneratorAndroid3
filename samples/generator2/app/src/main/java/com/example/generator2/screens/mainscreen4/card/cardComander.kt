package com.example.generator2.screens.mainscreen4.card

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.generator2.R
import com.example.generator2.theme.colorDarkBackground
import com.example.generator2.theme.colorLightBackground
import com.example.generator2.data.LiveData
import com.example.generator2.screens.mainscreen4.vm.VMMain4
import com.siddroid.holi.colors.MaterialColor

@Composable
fun CardCommander( vm : VMMain4) {



    Card(
        Modifier
            .height(40.dp)
            .fillMaxWidth().padding(start = 8.dp, end = 8.dp), backgroundColor = colorLightBackground
    )
    {



        Row(Modifier.fillMaxSize(), Arrangement.Start, Alignment.CenterVertically) {

            IconButton(
                onClick = { })
            {
                Icon(
                    painter = painterResource(R.drawable.folder_open2),
                    contentDescription = null , tint = Color.LightGray
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            IconButton(
                onClick = { })
            {
                Icon(
                    painter = painterResource(R.drawable.save2),
                    contentDescription = null, tint = Color.LightGray
                )
            }

            Spacer(modifier = Modifier.width(16.dp))



            val mono = LiveData.mono.collectAsState()
            IconButton(onClick = { LiveData.mono.value = !LiveData.mono.value })
            {
                val color = if (mono.value) MaterialColor.GREEN_500  else colorDarkBackground
                Icon(painter = painterResource(R.drawable.link), contentDescription = null, tint = color)
            }

            val invert = LiveData.invert.collectAsState()
            IconButton(onClick = { LiveData.invert.value = !LiveData.invert.value })
            {
                val color = if (invert.value) MaterialColor.GREEN_500  else colorDarkBackground
                Icon(painter = painterResource(R.drawable.headphones), contentDescription = null, tint = color)
            }


            IconButton(onClick = { vm.hub.audioDevice.playbackEngine.resetAllPhase() })
            {

                Icon(painter = painterResource(R.drawable.close), contentDescription = null)
            }

        }


    }
}