import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun EditorCanvas()
{

    val path1 = remember { Path() }





    Box(modifier = Modifier.fillMaxWidth().aspectRatio(1f).background(Color.White))
    {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val canvasWidth = size.width
            val canvasHeight = size.height

            path1.reset()

            drawLine(
                start = Offset(x = canvasWidth, y = 0f),
                end = Offset(x = 0f, y = canvasHeight),
                color = Color.Blue
            )

            path1.moveTo(100f, 100f)
            // Draw a line from top right corner (100, 100) to (100,300)
            path1.lineTo(100f, 300f)
            // Draw a line from (100, 300) to (300,300)
            path1.lineTo(300f, 300f)
            // Draw a line from (300, 300) to (300,100)
            path1.lineTo(300f, 100f)
            // Draw a line from (300, 100) to (100,100)
            path1.lineTo(100f, 100f)

            drawPath(
                color = Color.Red,
                path = path1,
                style = Stroke(
                    width = 2.dp.toPx(),
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f))
                )
            )

        }
    }
}



