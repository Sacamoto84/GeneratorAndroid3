import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.generator2.Global
import com.example.generator2.mainscreen4.TemplateBottomBar6Key
import com.example.generator2.mainscreen4.TemplateButtonBottomBar
import com.example.generator2.mainscreen4.bottomBarEnum
import com.example.generator2.scripting.StateCommandScript

@Composable
private fun Key0() {

    TemplateButtonBottomBar(str = "Шагнуть", onClick = {

    })
}

@Composable
private fun Key1() {




}

@Composable
private fun Key2() {
    TemplateButtonBottomBar(str = "Стоп", onClick = {
        Global.script.command(StateCommandScript.STOP)
    })
}

@Composable
private fun Key4() {

}

@Composable
private fun Key5() {
    TemplateButtonBottomBar(str = "Назад", onClick = {
        Global.bottomBarRoute.value = bottomBarEnum.HOME
    })
}

@Composable
fun bottomBarEditor() {
    TemplateBottomBar6Key(
        key0 = { Key1() },
        key2 = { Key0() },
        key3 = { Key2() },
        key5 = { Key5() },
    )
}