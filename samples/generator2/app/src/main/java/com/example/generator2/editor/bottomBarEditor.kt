import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.generator2.Global
import com.example.generator2.mainscreen4.TemplateBottomBar6Key
import com.example.generator2.mainscreen4.TemplateButtonBottomBar
import com.example.generator2.mainscreen4.bottomBarEnum


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

    })
}

@Composable
private fun Key4() {

}

@Composable
private fun Key5(
    global: Global = viewModel()
) {
    TemplateButtonBottomBar(str = "Назад", onClick = {
        global.bottomBarRoute.value = bottomBarEnum.HOME
    })
}

@Composable
fun bottomBarEditor() {
    TemplateBottomBar6Key(
        key5 = { Key5() },
    )
}