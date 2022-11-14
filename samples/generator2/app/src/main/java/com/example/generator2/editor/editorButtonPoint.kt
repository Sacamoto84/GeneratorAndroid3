import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.generator2.editor.PaintingState
import com.example.generator2.ui.theme.Green400
import java.util.concurrent.CancellationException

@Composable
fun ButtonPoint() {
    var gestureText by remember { mutableStateOf("") }
    var gestureColor by remember { mutableStateOf(colorLightBackground) }
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxHeight().width(80.dp).clip(shape = RoundedCornerShape(16.dp))
        .background(gestureColor).pointerInput(Unit) {
            detectTapGestures(onPress = { //gestureText = "onPress"
                gestureColor = Color.DarkGray
                model.state = PaintingState.PaintPoint
                val released = try {
                    tryAwaitRelease()
                } catch (c: CancellationException) {
                    false
                }

                if (released) {
                    model.state = PaintingState.Show //gestureText = "onPress Released"
                    gestureColor = colorLightBackground
                } else {
                    model.state = PaintingState.Show //gestureText = "onPress canceled"
                    gestureColor = colorLightBackground
                }
            })
        }) {

        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(
                center = Offset(size.width / 2, size.height / 2),
                radius = 16.dp.toPx(),
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF13C030), Color(0xFF03A9F4)
                    )
                )
            )
        }
    }
}

@Composable
fun ButtonLine() {
    var gestureText by remember { mutableStateOf("") }
    var gestureColor by remember { mutableStateOf(colorLightBackground) }
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxHeight().width(80.dp).clip(shape = RoundedCornerShape(16.dp))
        .background(gestureColor).pointerInput(Unit) {
            detectTapGestures(onPress = { //gestureText = "onPress"
                gestureColor = Color.DarkGray
                model.state = PaintingState.PaintLine
                val released = try {
                    tryAwaitRelease()
                } catch (c: CancellationException) {
                    false
                }

                if (released) {
                    model.state = PaintingState.Show //gestureText = "onPress Released"
                    gestureColor = colorLightBackground
                } else {
                    model.state = PaintingState.Show //gestureText = "onPress canceled"
                    gestureColor = colorLightBackground
                }
            })
        }) {

        Canvas(modifier = Modifier.fillMaxSize()) {
            drawLine(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF13C030), Color(0xFF03A9F4)
                    )
                ),
                start = Offset(16.dp.toPx(), size.height - 16.dp.toPx()),
                end = Offset(size.width - 16.dp.toPx(), 16.dp.toPx()),
                strokeWidth = 8.dp.toPx(),
                cap = StrokeCap.Round,
            )
        }
    }
}