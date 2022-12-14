package com.example.generator2.screens.mainscreen4.card

import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.generator2.R
import com.example.generator2.data.LiveData
import com.example.generator2.screens.mainscreen4.atom.LR
import com.example.generator2.screens.mainscreen4.vm.VMMain4
import com.example.generator2.theme.colorDarkBackground
import com.example.generator2.theme.colorLightBackground
import com.siddroid.holi.colors.MaterialColor

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CardCommander( vm : VMMain4) {

    Card(
        Modifier.height(40.dp).fillMaxWidth().padding(start = 8.dp, end = 8.dp)
            .horizontalScroll(rememberScrollState()), backgroundColor = colorLightBackground )
    {

        Row(Modifier.fillMaxSize(), Arrangement.Start, Alignment.CenterVertically) {
            val mono = LiveData.mono.collectAsState()
            val color = if (mono.value) MaterialColor.GREEN_400  else colorDarkBackground

            Spacer(modifier = Modifier.fillMaxWidth().weight(1f))

            IconButton( onClick = { LiveData.mono.value = !LiveData.mono.value }) {
                Icon( painter = if(mono.value) painterResource(R.drawable.mono) else painterResource(R.drawable.stereo),
                    contentDescription = null , tint = Color.LightGray) }

            LR()

            val shuffle = LiveData.shuffle.collectAsState()
            val invert = LiveData.invert.collectAsState()
            val time = 160

            Crossfade(targetState = mono.value, animationSpec = tween(time))
            {
                if (!it) {
                    IconButton( onClick = { LiveData.shuffle.value = !LiveData.shuffle.value  }) {
                        val color =  if (shuffle.value) MaterialColor.GREEN_500  else colorDarkBackground
                        Icon( painter = painterResource(R.drawable.shuffle74), contentDescription = null , tint = color)
                    }
                }
                else {
                    IconButton(onClick = { LiveData.invert.value = !LiveData.invert.value }) {
                        val color = if (invert.value) MaterialColor.GREEN_500  else colorDarkBackground
                        Icon(painter = painterResource(R.drawable.arrow_up_arrow_down51), contentDescription = null, tint = color)
                    }
                }
            }
            //Spacer(modifier = Modifier.width(8.dp))

            IconButton(onClick = { vm.hub.audioDevice.playbackEngine.resetAllPhase() }) {
                Icon(painter = painterResource(R.drawable.reset_phase), contentDescription = null, tint = Color.LightGray)
            }

        }


    }
}