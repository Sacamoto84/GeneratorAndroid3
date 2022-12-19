import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.unit.sp
import com.example.generator2.theme.colorDarkBackground
import com.example.generator2.theme.colorLightBackground2
import com.example.generator2.screens.mainscreen4.ui.InfinitySlider
import com.example.generator2.screens.mainscreen4.ui.UIspinner
import com.example.generator2.screens.mainscreen4.VMMain4
import com.example.generator2.vm.LiveConstrain
import com.example.generator2.vm.LiveData
import libs.modifier.noRippleClickable

@Composable
fun CardFM(str: String = "CH0", global: VMMain4) {


    val fmEN: State<Boolean?> = if (str == "CH0") {
        LiveData.ch1_FM_EN.collectAsState()
    } else {
        LiveData.ch2_FM_EN.collectAsState()
    }

    val fmFr: State<Float?> = if (str == "CH0") {
        LiveData.ch1_FM_Fr.collectAsState()
    } else {
        LiveData.ch2_FM_Fr.collectAsState()
    }

    val fmBase: State<Float?> = if (str == "CH0") {
        LiveData.ch1_FM_Base.collectAsState()
    } else {
        LiveData.ch2_FM_Base.collectAsState()
    }

    val fmDev: State<Float?> = if (str == "CH0") {
        LiveData.ch1_FM_Dev.collectAsState()
    } else {
        LiveData.ch2_FM_Dev.collectAsState()
    }

    Column()
    {
        Box(
            modifier = Modifier
                .background(Color.DarkGray)//colorGreen else colorOrange)
                .height(1.dp)
                .fillMaxWidth()
        )

        Row(
            Modifier
                .padding(top = 8.dp)
                .height(48.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            //ON OFF
            Box(modifier = Modifier.padding(start = 8.dp).height(24.dp).width(ms4SwitchWidth)
                .border(
                    2.dp,
                    color = if (fmEN.value!!) Color(0xFF1B5E20) else Color.DarkGray,
                    RoundedCornerShape(8.dp)
                )
                .clip(RoundedCornerShape(8.dp))
                .background(
                    color = if (fmEN.value!!) Color(0xFF01AE0F) else colorDarkBackground
                ).noRippleClickable(onClick = {
                    if (str == "CH0") LiveData.ch1_FM_EN.value =
                        !LiveData.ch1_FM_EN.value
                    else LiveData.ch2_FM_EN.value = !LiveData.ch2_FM_EN.value
                })) {}

            //////////////////////////////////////////////////////////////////////////////////////////////////////
            var expanded by remember { mutableStateOf(false) }
            var selectedIndex by remember {  mutableStateOf(0) }

            Box(Modifier.padding(start = 0.dp).height(48.dp).fillMaxWidth().weight(1f).noRippleClickable {  expanded = true }) {

                MainscreenTextBox(
                    String.format("%.1f", fmFr.value),
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
                                LiveData.ch1_FM_Fr.value = s.toFloat()
                            } else {
                                LiveData.ch2_FM_Fr.value = s.toFloat()
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
                value = fmFr.value,
                sensing = if (fmFr.value!! < 10.0F) LiveConstrain.sensetingSliderAmFm.value else LiveConstrain.sensetingSliderAmFm.value * 10f,
                range = LiveConstrain.rangeSliderAmFm,
                onValueChange = {
                    if (str == "CH0") LiveData.ch1_FM_Fr.value =
                        it else LiveData.ch2_FM_Fr.value = it
                },
                modifier = modifierInfinitySlider,
                vertical = true,
                invert = true,
                visibleText = false
            )


            UIspinner.Spinner(
                str,
                "FM",
                modifier = Modifier
                    .padding(top = 0.dp, start = 8.dp, end = 8.dp)
                    .wrapContentWidth()
                    //.fillMaxWidth()
                    .clip(shape = RoundedCornerShape(4.dp))
                    .background(Color.Black)
                , global = global)

        }

/////////////////////////


        //База 
        Row(
            Modifier
                .padding(top = 8.dp, end = 8.dp)
                .height(48.dp),
            verticalAlignment = Alignment.CenterVertically
        )
        {

            Box(
                modifier = Modifier
                    .padding(start=8.dp)
                    .height(48.dp)
                    .width(ms4SwitchWidth)
                    //.border(0.dp, Color.White, RoundedCornerShape(8.dp))
                    .clip(RoundedCornerShape(8.dp))
                    .background(colorDarkBackground)
                    .clickable { }
                , contentAlignment = Alignment.Center
            ) {
                Text("Save", fontSize = 12.sp, color = Color.LightGray )
            }

            MainscreenTextBoxPlus2Line(
                String.format("%d", fmBase.value!!.toInt()),
                String.format("%d", fmBase.value!!.toInt() + fmDev.value!!.toInt()),
                String.format("%d", fmBase.value!!.toInt() - fmDev.value!!.toInt()),
                Modifier.padding(start=8.dp)
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .weight(1f)
            )

            InfinitySlider(
                value = fmBase.value,
                sensing = LiveConstrain.sensetingSliderFmBase.value * 8,
                range = LiveConstrain.rangeSliderFmBase,
                onValueChange = {
                    if (str == "CH0") LiveData.ch1_FM_Base.value =
                        it else LiveData.ch2_FM_Base.value = it
                },
                modifier = modifierInfinitySlider,
                vertical = true,
                invert = true,
                visibleText = false
            )


            InfinitySlider(
                value = fmBase.value,
                sensing = LiveConstrain.sensetingSliderFmBase.value,
                range = LiveConstrain.rangeSliderFmBase,
                onValueChange = {
                    if (str == "CH0") LiveData.ch1_FM_Base.value =
                        it else LiveData.ch2_FM_Base.value = it
                },
                modifier = modifierInfinitySlider,
                vertical = true,
                invert = true,
                visibleText = false
            )


        }

////////
        Row(
            Modifier
                .padding(top = 8.dp, start = 0.dp, end = 8.dp)
                .height(48.dp),
            verticalAlignment = Alignment.CenterVertically
        )
        {

            Box(
                modifier = Modifier
                    .padding(start=8.dp)
                    .height(48.dp)
                    .width(ms4SwitchWidth)
                    //.border(1.dp, Color.White, RoundedCornerShape(8.dp))
                    .clip(RoundedCornerShape(8.dp))
                    .background(colorDarkBackground)
                    .clickable { }
                , contentAlignment = Alignment.Center
            ) {
                Text("Load", fontSize = 12.sp, color = Color.LightGray )
            }

            MainscreenTextBox(
                String.format("± %d", fmDev.value!!.toInt()),
                Modifier
                    .padding(start=8.dp)
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .weight(1f)
            )


            InfinitySlider(
                value = fmDev.value,
                sensing = LiveConstrain.sensetingSliderFmDev.value * 8,
                range = LiveConstrain.rangeSliderFmDev,
                onValueChange = {
                    if (str == "CH0") LiveData.ch1_FM_Dev.value =
                        it else LiveData.ch2_FM_Dev.value = it
                },
                modifier = modifierInfinitySlider,
                vertical = true,
                invert = true,
                visibleText = false
            )

            InfinitySlider(
                value = fmDev.value,
                sensing = LiveConstrain.sensetingSliderFmDev.value,
                range = LiveConstrain.rangeSliderFmDev,
                onValueChange = {
                    if (str == "CH0") LiveData.ch1_FM_Dev.value =
                        it else LiveData.ch2_FM_Dev.value = it
                },
                modifier = modifierInfinitySlider,
                vertical = true,
                invert = true,
                visibleText = false
            )


        }


        Spacer(modifier = Modifier.height(8.dp))
//        Slider(
//            valueRange = rangeSliderFmDev,
//            value = fmDev.value!!,
//            onValueChange = {
//                if (str == "CH0") Global.ch1_FM_Dev.value =
//                    it else Global.ch2_FM_Dev.value = it
//            },
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(start = 8.dp, end = 8.dp),
//            colors = SliderDefaults.colors(thumbColor = Color.LightGray)
//        )


        //}
    }


}