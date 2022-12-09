package com.example.generator2.screens.firebase

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ExitToApp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalTextInputService
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import colorLightBackground2
import com.example.generator2.R
import com.example.generator2.vm.Global
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.BuildConfig
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.talhafaki.composablesweettoast.util.SweetToastUtil.SweetError
import com.talhafaki.composablesweettoast.util.SweetToastUtil.SweetSuccess
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.awt.font.TextAttribute


data class LoadingState private constructor(var status: Status, var msg: String? = null) {

    companion object {
        val LOADED  = LoadingState(Status.SUCCESS)
        val IDLE    = LoadingState(Status.IDLE)
        val LOADING = LoadingState(Status.RUNNING)
        fun error(msg: String?) = LoadingState(Status.FAILED, msg)
    }

    enum class Status {
        RUNNING, SUCCESS, FAILED, IDLE,
    }
}


class Firebas(val context: Context) {

    lateinit var auth: FirebaseAuth

    //UI
    private var email by mutableStateOf("")
    private var password by mutableStateOf("")

    var componentActivity: ComponentActivity? = null

    var uid by mutableStateOf("")


    @OptIn(ExperimentalComposeUiApi::class)
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @Composable
    fun LoginScreen(viewModel: Global) {

        val state by viewModel.loadingState.collectAsState()

        // Equivalent of onActivityResult
        val launcher =
            rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
                try {
                    val account = task.getResult(ApiException::class.java)!!
                    val credential = GoogleAuthProvider.getCredential(account.idToken!!, null)
                    viewModel.signWithCredential(credential)
                } catch (e: ApiException) {
                    Log.w("TAG", "Google sign in failed", e)
                }
            }

        Column(
            Modifier.fillMaxWidth().background(colorLightBackground2)
        ) {

            //Индикатор работы
            if (state.status == LoadingState.Status.RUNNING) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            } else Spacer(modifier = Modifier.height(4.dp))

            when (state.status) {
                LoadingState.Status.SUCCESS -> {
                    SweetSuccess(
                        message = "Success",
                        duration = Toast.LENGTH_LONG, //padding = PaddingValues(top = 16.dp),
                        contentAlignment = Alignment.BottomCenter
                    )
                    state.msg = ""
                }
                LoadingState.Status.FAILED  -> {
                    var s = state.msg ?: "Error"
                    s = if (s.indexOf("Error 403") != -1) "Error 403 Forbidden, please use VPN"
                    else s

                    Text(
                        text = s,
                        maxLines = 7,
                        color = Color.Red,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    state.msg = ""
                }
                else                        -> {}
            }

            Text(
                modifier = Modifier.fillMaxWidth().padding(start = 8.dp),
                textAlign = TextAlign.Left,
                style = MaterialTheme.typography.caption,
                text = "uid: $uid",
                color = Color.LightGray
            )

            Text(
                modifier = Modifier.fillMaxWidth().padding(start = 8.dp),
                textAlign = TextAlign.Left,
                style = MaterialTheme.typography.caption,
                text = "email: ${auth.currentUser?.email}",
                color = Color.LightGray
            )

            if (((uid != "") && (uid != "null"))) {

                Box(modifier = Modifier.fillMaxWidth().padding(8.dp), contentAlignment = Alignment.Center) {

                    Button(
                        modifier = Modifier.width(200.dp).height(40.dp),
                        enabled = ((uid != "") && (uid != "null")),
                        content = {
                            Text(text = "Sign Out")
                        },

                        onClick = { //AuthUI.getInstance().signOut(componentActivity!!)
                            Firebase.auth.signOut()
                            updateUI(null)

                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFF4CAF50),
                            disabledBackgroundColor = Color(0xFF262726),
                            contentColor = Color.White
                        )
                    )
                }


            } else {
                //Регистрация и вход
                Column() {

                    //логин и пароль
                    Row() {

                        val focusManager = LocalFocusManager.current


                        val focus = remember { mutableStateOf(false) }
                        val inputService = LocalTextInputService.current

                        val keyboardController = LocalSoftwareKeyboardController.current

                        OutlinedTextField(
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                textColor = Color.White,
                                focusedBorderColor = Color.LightGray,
                                focusedLabelColor = Color.White
                            ),
                            modifier = Modifier.fillMaxWidth().weight(1f).padding(8.dp),
                            value = email,
                            label = {
                                Text(text = "Email")
                            },
                            onValueChange = { email = it },
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Next, keyboardType = KeyboardType.Email
                            ),
                            keyboardActions = KeyboardActions(onNext = {
                                focusManager.moveFocus(FocusDirection.Right)
                            })

                        )

                        OutlinedTextField(colors = TextFieldDefaults.outlinedTextFieldColors(
                            textColor = Color.White,
                            focusedBorderColor = Color.LightGray,
                            focusedLabelColor = Color.White
                        ),

                            modifier = Modifier.fillMaxWidth().weight(1f)
                                .padding(8.dp), //visualTransformation = PasswordVisualTransformation(),
                            value = password,
                            label = { Text(text = "Password") },
                            onValueChange = { password = it },

                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
                        )

                    }

                    //Кнопки входа и регистрации
                    Row(verticalAlignment = Alignment.CenterVertically) {

                        Button(modifier = Modifier.padding(8.dp).fillMaxWidth().height(40.dp)
                            .weight(1f),

                            content = {
                                Text(
                                    text = "Sign In",
                                    color = Color.White,
                                    fontSize = 18.sp
                                )
                            },
                            onClick = {
                                viewModel.signInWithEmailAndPassword(
                                    email.trim(),
                                    password.trim()
                                )
                            },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color(0xFF4CAF50),
                                disabledBackgroundColor = Color(0xFF262726)
                            )
                        )

                        Button(
                            modifier = Modifier.padding(8.dp).fillMaxWidth().height(40.dp)
                                .weight(1f),
                            content = {
                                Text(
                                    text = "Register",
                                    color = Color.LightGray,
                                    fontSize = 18.sp
                                )
                            },
                            onClick = {
                                state.msg = ""
                                createAccount(email.trim(), password.trim(), viewModel, state)
                            },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color(0xFF388E3C),
                                disabledBackgroundColor = Color(0xFF262726)
                            )
                        )
                    }

                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.caption,
                        text = "or sign in with",
                        color = Color.White
                    )

                    val context = LocalContext.current
                    val token = stringResource(R.string.default_web_client_id)

                    //Кнопка гугла
                    OutlinedButton(

                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFFFFFFFF), contentColor = Color.Black
                        ),

                        border = ButtonDefaults.outlinedBorder.copy(width = 1.dp),

                        modifier = Modifier.padding(8.dp).fillMaxWidth().height(40.dp), onClick = {
                            val gso =
                                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                    .requestIdToken(token).requestEmail().build()
                            val googleSignInClient = GoogleSignIn.getClient(context, gso)
                            launcher.launch(googleSignInClient.signInIntent)
                        }, content = {
                            Row(modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                                content = {
                                    Icon(
                                        tint = Color.Unspecified,
                                        painter = painterResource(id = R.drawable.icons8_google),
                                        contentDescription = null,
                                    )
                                    Text(
                                        color = Color.DarkGray,
                                        text = "Sign in with Google",
                                        fontSize = 18.sp
                                    )
                                    Icon(
                                        tint = Color.Transparent,
                                        imageVector = Icons.Default.MailOutline,
                                        contentDescription = null,
                                    )
                                })
                        })
                }
            }

        }
    }

    fun reload() {
        auth.currentUser!!.reload().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                updateUI(auth.currentUser)
                Toast.makeText(context, "Reload successful!", Toast.LENGTH_SHORT).show()
            } else {
                Log.e("Firebase", "reload", task.exception)
                Toast.makeText(context, "Failed to reload user.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun updateUI(user: FirebaseUser?) {
        uid = auth.currentUser?.uid.toString()
    }

    private fun createAccount(email: String, password: String, viewModel: Global, state: LoadingState) {
        Log.d("Firebase", "createAccount:$email")

        if (!validateForm()) {
            return
        }

        GlobalScope.launch {
            viewModel.loadingState.emit(LoadingState.LOADING)
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(componentActivity!!) { task ->

                if (task.isSuccessful) { // Sign in success, update UI with the signed-in user's information
                    Log.d(
                        "Firebase", "createUserWithEmail:success"
                    ) //Toast.makeText( context, "createUserWithEmail:success", Toast.LENGTH_LONG ).show()
                    val user = auth.currentUser
                    updateUI(user)
                    GlobalScope.launch {
                        viewModel.loadingState.emit(LoadingState.LOADED)
                    }
                } else { // If sign in fails, display a message to the user.
                    Log.w(
                        "Firebase", "createUserWithEmail:failure", task.exception
                    ) //Toast.makeText( context, "Authentication failed. ${task.exception}", Toast.LENGTH_LONG ).show() //updateUI(null)


                    GlobalScope.launch {
                        viewModel.loadingState.emit(LoadingState.IDLE)
                        state.status = LoadingState.Status.FAILED
                        state.msg = task.exception.toString()
                    }

                }


            }
    }


    private fun validateForm(): Boolean {
        var valid = true
        if (email.isEmpty()) valid = false
        if (password.isEmpty()) valid = false
        return valid
    }

}