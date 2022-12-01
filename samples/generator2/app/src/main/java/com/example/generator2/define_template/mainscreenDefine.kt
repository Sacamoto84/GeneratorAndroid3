import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

val colorGreen = Color(0xFF4CB050)
val colorOrange = Color(0xFFD8BD12)

val colorDarkBackground = Color(0xFF13161B)
val colorLightBackground = Color(0xFF2A2D36)

//Ширина переключателей
val ms4SwitchWidth = 72.dp

val sensetingSliderCr = 0.2f
val rangeSliderCr = 600f..4000f

val sensetingSliderAmFm = 0.01f
val rangeSliderAmFm = 0.1f..100f

val sensetingSliderFmBase = 0.2f
val rangeSliderFmBase = 1000f..3000f

val sensetingSliderFmDev = 0.2f
val rangeSliderFmDev = 1f..2500f

val modifierInfinitySlider =
    Modifier
        .padding(start = 8.dp)
        .border(2.dp, Color.DarkGray, RoundedCornerShape(8.dp))
        .clip(RoundedCornerShape(8.dp)).background(Color(0xFF141414))
        .size(48.dp)