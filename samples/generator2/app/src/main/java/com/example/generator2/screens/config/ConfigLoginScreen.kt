package com.example.generator2.screens.firebase

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.generator2.R
import com.example.generator2.screens.config.DefScreenConfig
import com.example.generator2.screens.config.DefScreenConfig.caption
import com.example.generator2.screens.config.DefScreenConfig.textSizeGreenButton
import com.example.generator2.screens.config.VMConfig
import com.example.generator2.screens.config.readMetaBackupFromFirebase
import com.example.generator2.screens.config.Config_Green_button
import com.example.generator2.theme.colorDarkBackground
import com.example.generator2.theme.colorLightBackground
import com.example.generator2.theme.colorLightBackground2
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ConfigLoginScreen(vm: VMConfig) {

    val uid = vm.firebase.auth.currentUser?.uid.toString()
    val state by vm.firebase.loadingState.collectAsState()

    // Equivalent of onActivityResult
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                val credential = GoogleAuthProvider.getCredential(account.idToken!!, null)
                vm.firebase.signWithCredential(credential, readMetaBackupFromFirebase(
                    vm.firebase.auth.currentUser?.uid
                ))
            } catch (e: ApiException) {
                Log.w("TAG", "Google sign in failed", e)
            }
        }

    Column(
        Modifier
            .fillMaxWidth()
            .background(colorLightBackground)
    ) {

        //Индикатор работы
        if (state.status == LoadingState.Status.RUNNING) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        } else Spacer(modifier = Modifier.height(4.dp))

        when (state.status) {
            LoadingState.Status.SUCCESS -> {
                //vm.toastSuccess()
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
                    textAlign = TextAlign.Center,
                    style = caption
                )
                Spacer(modifier = Modifier.height(8.dp))
                state.msg = ""
            }
            else                        -> {}
        }

        Text( modifier = Modifier.fillMaxWidth().padding(start = 8.dp), textAlign = TextAlign.Left, text = "uid:   $uid", color = Color.LightGray, style = caption )
        Text( modifier = Modifier.fillMaxWidth().padding(start = 8.dp), textAlign = TextAlign.Left, text = "email: ${vm.firebase.auth.currentUser?.email}", color = Color.LightGray, style = caption )

        if (((uid != "") && (uid != "null"))) {

            Box(modifier = Modifier.fillMaxWidth().padding(8.dp), contentAlignment = Alignment.Center) {

                Button(
                    modifier = Modifier.width(200.dp).height(40.dp),
                    enabled = ((uid != "") && (uid != "null")),
                    content = {Text(text = "Sign Out", fontSize = textSizeGreenButton) },
                    onClick = { //AuthUI.getInstance().signOut(componentActivity!!)
                        Firebase.auth.signOut()
                        vm.firebase.updateUI(null)
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = DefScreenConfig.backgroundColorGreenButton,
                        disabledBackgroundColor = DefScreenConfig.disabledBackgroundColorGreenButton,
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
                    //Логин
                    OutlinedTextField(
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            textColor = Color.White, focusedBorderColor = Color.LightGray, focusedLabelColor = Color.White ),
                        modifier = Modifier.fillMaxWidth().weight(1f).padding(8.dp), value = vm.firebase.email, label = { Text(text = "Email")},
                        onValueChange = { vm.firebase.email = it }, keyboardOptions = KeyboardOptions( imeAction = ImeAction.Next, keyboardType = KeyboardType.Email ),
                        keyboardActions = KeyboardActions(onNext = {focusManager.moveFocus(FocusDirection.Right) })
                    )
                    //Пароль
                    OutlinedTextField(colors = TextFieldDefaults.outlinedTextFieldColors( textColor = Color.White, focusedBorderColor = Color.LightGray, focusedLabelColor = Color.White ),
                        modifier = Modifier.fillMaxWidth().weight(1f).padding(8.dp), //visualTransformation = PasswordVisualTransformation(),
                        value = vm.firebase.password, label = { Text(text = "Password") }, onValueChange = { vm.firebase.password = it },
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
                    )
                }

                //Кнопки входа и регистрации
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Config_Green_button( modifier= Modifier.padding(8.dp).fillMaxWidth().height(40.dp).weight(1f),
                        onClick = { vm.firebase.signInWithEmailAndPassword( vm.firebase.email.trim(), vm.firebase.password.trim()) }, label = "Sign In" )

                    Config_Green_button( modifier= Modifier.padding(8.dp).fillMaxWidth().height(40.dp).weight(1f),
                        onClick = { state.msg = ""
                            vm.firebase.createAccount(vm.firebase.email.trim(), vm.firebase.password.trim(), state)}, label = "Register" )
                }

                Text( modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center, style = MaterialTheme.typography.caption, text = "or sign in with", color = Color.White )

                val context = LocalContext.current
                val token = stringResource(R.string.default_web_client_id)

                //Кнопка гугла
                OutlinedButton( colors = ButtonDefaults.buttonColors( backgroundColor = Color(0xFFFFFFFF), contentColor = Color.Black ),
                    border = ButtonDefaults.outlinedBorder.copy(width = 1.dp),
                    modifier = Modifier.padding(8.dp).fillMaxWidth().height(40.dp),
                    onClick = {
                        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(token).requestEmail().build()
                        val googleSignInClient = GoogleSignIn.getClient(context, gso)
                        launcher.launch(googleSignInClient.signInIntent)
                    },
                    content = {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically,
                            content = {
                                Icon( tint = Color.Unspecified, painter = painterResource(id = R.drawable.icons8_google),contentDescription = null )
                                Text( color = Color.DarkGray, text = "Sign in with Google", fontSize = textSizeGreenButton )
                                Icon( tint = Color.Transparent, imageVector = Icons.Default.MailOutline, contentDescription = null )
                            })
                    })
            }
        }

    }
}