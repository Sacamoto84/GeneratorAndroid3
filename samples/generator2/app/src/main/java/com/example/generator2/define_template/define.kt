import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

//Ширина переключателей
val ms4SwitchWidth = 72.dp



val modifierInfinitySlider =
    Modifier
        .padding(start = 8.dp)
        .border(2.dp, Color.DarkGray, RoundedCornerShape(8.dp))
        .clip(RoundedCornerShape(8.dp)).background(Color(0xFF141414))
        .size(48.dp)