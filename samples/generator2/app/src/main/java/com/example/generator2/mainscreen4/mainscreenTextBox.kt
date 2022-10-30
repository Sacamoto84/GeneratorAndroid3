import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.generator2.recomposeHighlighterOneLine

@Composable
fun MainscreenTextBox(str: String, modifier: Modifier = Modifier) {
    Box(
        Modifier
            //.height(48.dp)
            //.fillMaxWidth()
            .then(modifier)
            .clip(shape = RoundedCornerShape(4.dp))
            .background(Color(0xFF13161B))
            .recomposeHighlighterOneLine(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = str,
            color = Color.LightGray,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }
}


@Composable
fun MainscreenTextBoxPlus2Line(
    str: String,
    strplus: String,
    strminus: String,
    modifier: Modifier = Modifier
) {
    Box(
        Modifier
            //.height(48.dp)
            //.fillMaxWidth()
            .then(modifier)
            .clip(shape = RoundedCornerShape(4.dp))
            .background(Color(0xFF13161B))
            .recomposeHighlighterOneLine(),
        contentAlignment = Alignment.Center
    ) {

        Row(
            Modifier
                .matchParentSize(),
                //.background(Color.DarkGray),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {

            Text(
                text = str,
                color = Color.LightGray,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier= Modifier.padding(start = 0.dp)
            )


            Column() {

                Text(
                    text = strplus,
                    color = Color.LightGray,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                   , modifier = Modifier.offset(x = 6.dp)
                )

                Text(
                    text = strminus,
                    color = Color.LightGray,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                    , modifier = Modifier.offset(x = 6.dp)
                )
            }

        }

    }
}