import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
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
import com.example.generator2.theme.*
import com.example.generator2.ui.wiget.InfinitySlider
import com.example.generator2.ui.wiget.UImodifier.noRippleClickable
import com.example.generator2.ui.wiget.UIspinner
import com.example.generator2.screens.mainscreen4.VMMain4
import com.example.generator2.vm.LiveConstrain
import com.example.generator2.vm.LiveData


@Composable
fun CardCarrier(str: String = "CH0", global: VMMain4) {

    val chEN: State<Boolean> = if (str == "CH0") {
        LiveData.ch1_EN.collectAsState()
    } else {
        LiveData.ch2_EN.collectAsState()
    }

    val carrierFr: State<Float?> = if (str == "CH0") {
        LiveData.ch1_Carrier_Fr.collectAsState()
    } else {
        LiveData.ch2_Carrier_Fr.collectAsState()
    }

    Card(

        backgroundColor = colorLightBackground,
        modifier = Modifier //.wrapContentHeight()
            .fillMaxWidth().padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp),
        elevation = 0.dp
    ) {

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier.background(if (str == "CH0") colorGreen else colorOrange)
                    .height(8.dp).fillMaxWidth(), contentAlignment = Alignment.Center
            ) { //Text(str)
            }

            Row(
                Modifier.padding(top = 8.dp), verticalAlignment = Alignment.CenterVertically
            ) {


                //                Switch(
                //                    modifier = Modifier.width(ms4SwitchWidth),
                //                    checked = chEN.value!!,
                //                    onCheckedChange = {
                //                        if (str == "CH0") global.liveData.ch1_EN.value = it else global.liveData.ch2_EN.value = it
                //                    })

                Box(modifier = Modifier.padding(start = 8.dp).height(24.dp).width(ms4SwitchWidth)
                    .border(
                        2.dp,
                        color = if (chEN.value) Color(0xFF1B5E20) else Color.DarkGray,
                        RoundedCornerShape(8.dp)
                    ).clip(RoundedCornerShape(8.dp)).background(
                        color = if (chEN.value) Color(0xFF4DD0E1) else colorDarkBackground
                    ).noRippleClickable(onClick = {
                        if (str == "CH0") LiveData.ch1_EN.value =
                            !LiveData.ch1_EN.value
                        else LiveData.ch2_EN.value = !LiveData.ch2_EN.value

                        println("Кнопка")

                    })
                ) {}

//////////////////////////////////////////////////////////////////////////////////////////////////////
                var expanded by remember { mutableStateOf(false) }
                var selectedIndex by remember {  mutableStateOf(0) }

                Box(Modifier.padding(start = 0.dp).height(48.dp).fillMaxWidth().weight(1f).noRippleClickable {  expanded = true }) {

                    MainscreenTextBox(
                        String.format("%d", carrierFr.value!!.toInt()),
                        Modifier.padding(start = 8.dp).height(48.dp).fillMaxSize()
                    )

                    val items = listOf<String>("600","800","1000","1500", "2000","2500","3000","3500","4000")

                    DropdownMenu(
                        offset = DpOffset(12.dp, 4.dp),
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
                                    LiveData.ch1_Carrier_Fr.value = s.toFloat()
                                } else {
                                    LiveData.ch2_Carrier_Fr.value = s.toFloat()
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
                    value = carrierFr.value,
                    sensing = LiveConstrain.sensetingSliderCr.value,
                    range = LiveConstrain.rangeSliderCr,
                    onValueChange = {
                        if (str == "CH0") LiveData.ch1_Carrier_Fr.value =
                            it else LiveData.ch2_Carrier_Fr.value = it
                    },
                    modifier = modifierInfinitySlider,
                    vertical = true,
                    invert = true,
                    visibleText = false
                )


                UIspinner.Spinner(
                    CH = str,
                    Mod = "CR",
                    modifier = Modifier.padding(top = 0.dp, start = 8.dp, end = 8.dp)
                        .wrapContentWidth().clip(shape = RoundedCornerShape(4.dp)),
                    global = global
                )

            }

            //            Slider(
            //                valueRange = rangeSliderCr,
            //                value = carrierFr.value!!,
            //                onValueChange = {
            //                    if (str == "CH0") Global.ch1_Carrier_Fr.value =
            //                        it else Global.ch2_Carrier_Fr.value = it
            //                },
            //                modifier = Modifier
            //                    .fillMaxWidth()
            //                    .padding(start = 8.dp, end = 8.dp),
            //                colors = SliderDefaults.colors(thumbColor = Color.LightGray),
            //                steps = stepSliderCr
            //            )

            Spacer(modifier = Modifier.height(8.dp))

            CardAM(str, global)
            CardFM(str, global)

        }


    }


}