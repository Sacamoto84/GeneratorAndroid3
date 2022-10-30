import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.generator2.Global
import com.example.generator2.ui.wiget.InfinitySlider
import com.example.generator2.ui.wiget.UIspinner
import libs.modifier.recomposeHighlighter
import kotlin.properties.Delegates


@Composable
fun CardCarrier(str: String = "CH0") {

    val chEN: State<Boolean?> = if (str == "CH0") {
        Global.ch1_EN.observeAsState()
    } else {
        Global.ch2_EN.observeAsState()
    }

    val carrierFr: State<Float?> = if (str == "CH0") {
        Global.ch1_Carrier_Fr.observeAsState()
    } else {
        Global.ch2_Carrier_Fr.observeAsState()
    }

    Card(

        backgroundColor = colorLightBackground, modifier = Modifier
            //.wrapContentHeight()
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp),
        elevation = 0.dp
    )
    {

        Column(
            modifier = Modifier.fillMaxSize()
        )
        {
            Box(
                modifier = Modifier
                    .background(if (str == "CH0") colorGreen else colorOrange)
                    .height(8.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            )
            {
                //Text(str)
            }

            Row(
                Modifier.padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {


                Switch(
                    modifier = Modifier.width(ms4SwitchWidth),
                    checked = chEN.value!!,
                    onCheckedChange = {
                        if (str == "CH0") Global.ch1_EN.value = it else Global.ch2_EN.value = it
                    })

                MainscreenTextBox(
                    str = String.format("%d", carrierFr.value!!.toInt()),
                    Modifier
                        .padding(start=8.dp)
                        .height(48.dp)
                        .fillMaxWidth()
                        .weight(1f)
                )


                InfinitySlider(
                    value = carrierFr.value,
                    sensing = sensetingSliderCr,
                    range = rangeSliderCr,
                    onValueChange = {
                        if (str == "CH0") Global.ch1_Carrier_Fr.value =
                            it else Global.ch2_Carrier_Fr.value = it
                    },
                    modifier = modifierInfinitySlider,
                    vertical = true,
                    invert = true,
                    visibleText = false
                )


                UIspinner.Spinner(
                    CH = str,
                    Mod = "CR",
                    modifier = Modifier
                        .padding(top = 0.dp, start = 8.dp, end = 8.dp)
                        .wrapContentWidth()
                        .clip(shape = RoundedCornerShape(4.dp))
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

            CardAM(str)
            CardFM(str)

        }


    }


}