import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.generator2.Global
import com.example.generator2.ui.wiget.InfinitySlider
import com.example.generator2.ui.wiget.UIspinner


@Composable
fun CardAM(str: String = "CH0") {

    val global: Global = viewModel()

    val amEN: State<Boolean?> = if (str == "CH0") {
        global.liveData.ch1_AM_EN.observeAsState()
    } else {
        global.liveData.ch2_AM_EN.observeAsState()
    }



    //Card(
//
//        backgroundColor = Color(0xFF2A2D36),
//        modifier = Modifier
//            .wrapContentHeight()
//            .fillMaxWidth()
//            .padding(start = 16.dp, end = 16.dp, top = 16.dp),
//        elevation = 5.dp
//    )
//    {
    Column(


        //modifier = Modifier
        //.wrapContentHeight()
        //.fillMaxWidth()
        //.padding(start = 16.dp, end = 16.dp, top = 16.dp).background(Color(0xFF2A2D36)),


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
            //Text("AM")
        }

        Row(
            Modifier.padding(top = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Switch(
                modifier= Modifier.width(ms4SwitchWidth),
                checked = amEN.value!!,
                onCheckedChange = {
                    if (str == "CH0") global.liveData.ch1_AM_EN.value = it else global.liveData.ch2_AM_EN.value = it
                })

            val amFr: State<Float?> = if (str == "CH0") {
                global.liveData.ch1_AM_Fr.observeAsState()
            } else {
                global.liveData.ch2_AM_Fr.observeAsState()
            }

            MainscreenTextBox(
                String.format("%.1f", amFr.value),
                Modifier
                    .padding(start=8.dp)
                    .height(48.dp)
                    .fillMaxWidth()
                    .weight(1f)
            )

            InfinitySlider(
                value = amFr.value,
                sensing = if ( amFr.value!! < 10.0F) sensetingSliderAmFm else sensetingSliderAmFm*10f,
                range = rangeSliderAmFm,
                onValueChange = {
                    if (str == "CH0") global.liveData.ch1_AM_Fr.value =
                        it else global.liveData.ch2_AM_Fr.value = it
                },
                modifier = modifierInfinitySlider
                ,
                vertical = true,
                invert = true,
                visibleText = false
            )

            UIspinner.Spinner(
                str,
                "AM",
                modifier = Modifier
                    .padding(top = 0.dp, start = 8.dp, end = 8.dp)
                    .wrapContentWidth()
                    .clip(shape = RoundedCornerShape(4.dp))
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


    }
    //}
}