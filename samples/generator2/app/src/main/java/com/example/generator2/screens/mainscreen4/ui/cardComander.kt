package com.example.generator2.screens.mainscreen4.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.generator2.R
import com.example.generator2.theme.colorLightBackground
import com.example.generator2.theme.colorLightBackground2

@Composable
fun CardCommander() {
    Card(
        Modifier
            .height(40.dp)
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp), backgroundColor = colorLightBackground
    )
    {
        Row(Modifier.fillMaxSize(), Arrangement.Start, Alignment.CenterVertically) {

            IconButton(
                onClick = { })
            {
                Icon(
                    painter = painterResource(R.drawable.folder_open2),
                    contentDescription = null
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            IconButton(
                onClick = { })
            {
                Icon(
                    painter = painterResource(R.drawable.save2),
                    contentDescription = null
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            IconButton(onClick = { })
            {
                Icon(painter = painterResource(R.drawable.link), contentDescription = null)
            }


        }


    }
}