import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.generator2.ui.wiget.InfinitySlider
import com.example.generator2.ui.wiget.UImodifier.noRippleClickable
import com.example.generator2.ui.wiget.UIspinner
import com.example.generator2.vm.Global


@Composable
fun CardCarrier(str: String = "CH0", global: Global) {

    val chEN: State<Boolean?> = if (str == "CH0") {
        global.liveData.ch1_EN.collectAsState()
    } else {
        global.liveData.ch2_EN.collectAsState()
    }

    val carrierFr: State<Float?> = if (str == "CH0") {
        global.liveData.ch1_Carrier_Fr.collectAsState()
    } else {
        global.liveData.ch2_Carrier_Fr.collectAsState()
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

                Box(modifier = Modifier.padding(start = 4.dp).height(24.dp).width(ms4SwitchWidth)
                    .border(
                        2.dp,
                        color = if (chEN.value!!) Color(0xFF1B5E20) else Color.DarkGray,
                        RoundedCornerShape(8.dp)
                    )
                    .clip(RoundedCornerShape(8.dp))
                    .background(
                        color = if (chEN.value!!) Color(0xFF4DD0E1) else colorDarkBackground
                    ).noRippleClickable(onClick = {
                        if (str == "CH0") global.liveData.ch1_EN.value =
                            !global.liveData.ch1_EN.value!!
                        else global.liveData.ch2_EN.value = !global.liveData.ch2_EN.value!!
                    })) {}


                //val r1 by global.liveData.ch1_AM_EN.observeAsState()
                //                                    ON_OFF(state = r1!!, config = onoffconfig1, onClick = {
                //                                        Global.ch1_EN.value = !Global.ch1_EN.value!!
                //                                    }, modifier = Modifier.padding(start = 8.dp, top = 30.dp))


                //val q = global.liveData.ch1_AM_EN.observeAsState()
                //UIswitch.ON_OFF(r1!!, global.onoffconfig1, onClick = { global.liveData.ch1_AM_EN.value = !global.liveData.ch1_AM_EN.value!! })
                //UIswitch.ON_OFF(r1!!, global.onoffconfig, onClick = { global.liveData.ch1_AM_EN.value = !global.liveData.ch1_AM_EN.value!! })

                MainscreenTextBox(
                    str = String.format("%d", carrierFr.value!!.toInt()),
                    Modifier.padding(start = 8.dp).height(48.dp).fillMaxWidth().weight(1f)
                )


                InfinitySlider(
                    value = carrierFr.value,
                    sensing = sensetingSliderCr,
                    range = rangeSliderCr,
                    onValueChange = {
                        if (str == "CH0") global.liveData.ch1_Carrier_Fr.value =
                            it else global.liveData.ch2_Carrier_Fr.value = it
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