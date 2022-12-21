package com.example.generator2.screens.mainscreen4.atom

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.generator2.theme.colorDarkBackground

@Composable
fun LR() {
    Row(
        modifier = Modifier
            .height(30.dp)
            .width(70.dp)
            .clip(RoundedCornerShape(15.dp))
            //.background(Color.Red)
        ,verticalAlignment = Alignment.CenterVertically
    )
    {
        Text(text = "L",
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(15.dp))
                .border(1.dp, Color.DarkGray, CircleShape)
                .background(Color.Black)
                .offset(0.dp, 4.dp),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = "R",
            modifier = Modifier
                .padding()
                .fillMaxHeight()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(15.dp))
                .border(1.dp, Color.DarkGray, CircleShape)
                .background(Color.Black)
                .offset(1.dp, 4.dp),
            textAlign = TextAlign.Center
        )
    }
}