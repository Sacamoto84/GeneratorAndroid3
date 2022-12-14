package com.example.generator2

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import com.example.generator2.screens.config.ScreenConfig
import com.example.generator2.screens.editor.ScreenEditor
import com.example.generator2.screens.mainscreen4.atom.customTypeface
import com.example.generator2.screens.mainscreen4.mainsreen4
import com.example.generator2.screens.mainscreen4.vm.VMMain4
import com.example.generator2.screens.scripting.ScreenScriptCommon
import com.example.generator2.screens.scripting.ScreenScriptInfo
import com.example.generator2.theme.Generator2Theme
import com.example.generator2.theme.colorDarkBackground
import com.example.generator2.util.Utils
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import dagger.hilt.android.AndroidEntryPoint
import libs.KeepScreenOn
import javax.inject.Singleton


@Singleton
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val global: VMMain4 by viewModels()

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = global.hub.firebase.auth.currentUser
        if(currentUser != null){
            global.hub.firebase.reload();
        }

    }

    override fun onStop() {
        super.onStop()
        println("...................onStop")
        global.hub.backup.json.saveJsonConfig()
    }

    @OptIn(ExperimentalAnimationApi::class, ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        println("...........................................................................")
        println("..................................onCreate.................................")
        println("...........................................................................")

        // Initialize Firebase Auth
        global.hub.firebase.auth = Firebase.auth
        global.hub.firebase.componentActivity = this
        //val storage = Firebase.storage

        global.hub.backup.json.readJsonVolume()
        global.hub.backup.json.readJsonConfig()
        global.hub.backup.json.readJsonConstrain()

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            customTypeface = resources.getFont(R.font.jetbrains)
        }





        //        //gs://test-e538d.appspot.com/
//        val storageRef = global.storage.reference //????j???????????? ??????????
//
//        val imagesRef: StorageReference = storageRef.child("/shared/")
//        val localFile = File.createTempFile("images", ".jpg")
//
//        imagesRef.listAll()
//            .addOnSuccessListener {
//               println( "listAll addOnSuccessListener" +it.items.joinToString(","))
//        }.addOnFailureListener{
//                println("listAll addOnFailureListener:$it")
//            }

        //readMetaBackupFromFirebase(global)

        //saveBackupToFirebase(global)

//        imagesRef.getFile(localFile)
//            .addOnSuccessListener {
//                // Local temp file has been created
//                Toast.makeText( applicationContext, "imagesRef: success", Toast.LENGTH_LONG ).show()
//            }.addOnFailureListener {
//                // Handle any errors
//                Toast.makeText( applicationContext, "imagesRef: Error", Toast.LENGTH_LONG ).show()
//            }


        Utils.ContextMainActivity = applicationContext

        //Hawk.init(this).setLogInterceptor { message -> Log.i("HAWK:", message) }.build()

        setContent {

            val systemUiController = rememberSystemUiController()
            SideEffect {
                systemUiController.setSystemBarsColor(colorDarkBackground, darkIcons = false)
            }

            KeepScreenOn()

            //initialState - ?? ???????????? ???????????? ??????????????
            //targetState   -?????????????????? ????
            //enterTransition - ?????????????????? ??????, ?????? EnterTransition ??????????????????????, ?????????? targetState  NavBackStackEntry ???? ???????????? ???????????????????? ???????????? .
            //exitTransition  - ?????????????????? ??????, ?????? ExitTransition  ??????????????????????, ?????????? initialState NavBackStackEntry ???????????????? ?? ????????????.
            Generator2Theme() {
                val navController = rememberAnimatedNavController()

                AnimatedNavHost(
                    navController = navController,
                    startDestination = "home",
                    modifier = Modifier.background(Color.Black).semantics {
                        testTagsAsResourceId = true
                    }
                ) {

                    composable("home",
                        enterTransition = {fadeIn(animationSpec = tween(0)) },
                        exitTransition = {fadeOut(animationSpec = tween(0)) })
                    {
                        mainsreen4(navController, global)
                    }

                    composable("script",
                        enterTransition = { fadeIn(animationSpec = tween(0))  },
                        exitTransition  = { fadeOut(animationSpec = tween(0)) }
                    ) {

                        ScreenScriptCommon(navController)
                    }

                    composable("editor",
                        enterTransition = { fadeIn(animationSpec = tween(0))  },
                        exitTransition  = { fadeOut(animationSpec = tween(0)) }
                    ) {
                        ScreenEditor(navController)
                    }

                    composable("scriptinfo",
                        enterTransition = { fadeIn(animationSpec = tween(0))  },
                        exitTransition  = { fadeOut(animationSpec = tween(0)) }
                    ) {
                        ScreenScriptInfo(navController)
                    }

                    //?????????? ?????????????????? ??????????????????
                    composable("config",
                        enterTransition = { fadeIn(animationSpec = tween(0))  },
                        exitTransition  = { fadeOut(animationSpec = tween(0)) }
                    ) {
                        ScreenConfig(navController)
                    }

                }
            }
        }
    }
}



