import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.generator2.editor.PaintingState
import com.example.generator2.ui.theme.Green400
import java.util.concurrent.CancellationException

@Composable
fun ButtonPoint()
{

    var gestureText by remember { mutableStateOf("") }
    var gestureColor by remember { mutableStateOf(colorLightBackground) }
    val context = LocalContext.current

    Box(
        modifier = Modifier.padding(start = 16.dp, top = 16.dp).size(70.dp)
            .background(gestureColor).pointerInput(Unit){
                detectTapGestures(
                    onPress = {
                        //gestureText = "onPress"
                        gestureColor = Green400

                        model.state = PaintingState.PaintPoint

                        val released = try {
                            tryAwaitRelease()
                        } catch (c: CancellationException) {
                            false
                        }

                        if (released) {
                            model.state = PaintingState.Show
                            //gestureText = "onPress Released"
                            gestureColor = colorLightBackground
                        } else {
                            model.state = PaintingState.Show
                            //gestureText = "onPress canceled"
                            gestureColor = colorLightBackground
                        }
                    }
                )
            }) {
        Text(
            text = gestureText,
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }

}