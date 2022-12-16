import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.example.generator2.theme.colorDarkBackground
import com.example.generator2.theme.colorLightBackground2
import com.example.generator2.ui.wiget.InfinitySlider
import com.example.generator2.ui.wiget.UImodifier.noRippleClickable
import com.example.generator2.ui.wiget.UIspinner
import com.example.generator2.screens.mainscreen4.VMMain4
import com.example.generator2.vm.LiveConstrain
import com.example.generator2.vm.LiveData


@Composable
fun CardAM(str: String = "CH0", global: VMMain4) {

    val amEN: State<Boolean?> = if (str == "CH0") {
        LiveData.ch1_AM_EN.collectAsState()
    } else {
        LiveData.ch2_AM_EN.collectAsState()
    }

    Column() {

        Box(
            modifier = Modifier.background(Color.DarkGray) //colorGreen else colorOrange)
                .height(1.dp).fillMaxWidth()
        )

        Row(
            Modifier.padding(top = 8.dp), verticalAlignment = Alignment.CenterVertically
        ) {

            val amFr: State<Float?> = if (str == "CH0") {
                LiveData.ch1_AM_Fr.collectAsState()
            } else {
                LiveData.ch2_AM_Fr.collectAsState()
            }

            Box(
                modifier = Modifier.padding(start = 8.dp).height(24.dp).width(ms4SwitchWidth)
                    .border(
                        2.dp,
                        color = if (amEN.value!!) Color(0xFF1B5E20) else Color.DarkGray,
                        RoundedCornerShape(8.dp)
                    ).clip(RoundedCornerShape(8.dp)).background(
                        color = if (amEN.value!!) Color(0xFF01AE0F) else colorDarkBackground
                    ).noRippleClickable(onClick = {
                        if (str == "CH0") LiveData.ch1_AM_EN.value =
                            !LiveData.ch1_AM_EN.value
                        else LiveData.ch2_AM_EN.value = !LiveData.ch2_AM_EN.value
                    }) //.shadow(1.dp, shape = RoundedCornerShape(8.dp), ambientColor = Color.Blue)
                , contentAlignment = Alignment.Center
            ) {

            }


//////////////////////////////////////////////////////////////////////////////////////////////////////
            var expanded by remember { mutableStateOf(false)}
            var selectedIndex by remember {  mutableStateOf(0) }

            Box(Modifier.padding(start = 0.dp).height(48.dp).fillMaxWidth().weight(1f).noRippleClickable {  expanded = true }) {

                MainscreenTextBox(
                    String.format("%.1f", amFr.value),
                    Modifier.padding(start = 8.dp).height(48.dp).fillMaxSize()
                )

                val items = listOf("0.1","1.0","5.5","10.0","40.0", "100.0")

                DropdownMenu(
                    offset = DpOffset(8.dp, 4.dp),
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        //.width(80.dp)
                        .background(
                        colorLightBackground2
                    ).border(1.dp, color = Color.DarkGray, shape = RoundedCornerShape(16.dp))
                ) {

                    items.forEachIndexed { index, s ->
                        DropdownMenuItem(onClick = {
                            selectedIndex = index
                            expanded = false

                            if (str == "CH0") {
                                LiveData.ch1_AM_Fr.value = s.toFloat()
                            } else {
                                LiveData.ch2_AM_Fr.value = s.toFloat()
                            }

                        })
                        {
                            Text(text = s, color = Color.White)
                        }
                    }
                }
            }
//////////////////////////////////////////////////////////////////////////////////////////////////////


            InfinitySlider(
                value = amFr.value,
                sensing = if (amFr.value!! < 10.0F) LiveConstrain.sensetingSliderAmFm.value else LiveConstrain.sensetingSliderAmFm.value * 10f,
                range = LiveConstrain.rangeSliderAmFm,
                onValueChange = {
                    if (str == "CH0") LiveData.ch1_AM_Fr.value =
                        it else LiveData.ch2_AM_Fr.value = it
                },
                modifier = modifierInfinitySlider,
                vertical = true,
                invert = true,
                visibleText = false
            )

            UIspinner.Spinner(
                str,
                "AM",
                modifier = Modifier.padding(top = 0.dp, start = 8.dp, end = 8.dp).wrapContentWidth()
                    .clip(shape = RoundedCornerShape(4.dp)),
                global = global
            )


        }

        Spacer(modifier = Modifier.height(8.dp))

        //        Slider(
        //            valueRange = 0.1f..100f,
        //            value = amFr.value!!,
        //            onValueChange = {
        //                if (str == "CH0") Global.ch1_AM_Fr.value =
        //                    it else Global.ch2_AM_Fr.value = it
        //            },
        //            modifier = Modifier
        //                .fillMaxWidth()
        //                .padding(start = 8.dp, end = 8.dp),
        //            colors = SliderDefaults.colors(thumbColor = Color.LightGray)
        //        )


    } //}
}