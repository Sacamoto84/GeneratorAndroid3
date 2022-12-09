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
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ExitToApp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
import java.awt.font.TextAttribute


data class LoadingState private constructor(val status: Status, val msg: String? = null) {
    companion object {
        val LOADED = LoadingState(Status.SUCCESS)
        val IDLE = LoadingState(Status.IDLE)
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

    var status by mutableStateOf("")
    var detail by mutableStateOf("")

    var email by mutableStateOf("")
    var password by mutableStateOf("")

    var componentActivity: ComponentActivity? = null


    var uid by mutableStateOf("")

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

                //Text(text = "SignOut")

                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Button(
                        modifier = Modifier.width(200.dp).height(50.dp).padding(8.dp),
                        enabled = ((uid != "") && (uid != "null")),
                        content = {
                            Text(text = "SignOut")
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
                        OutlinedTextField(colors = TextFieldDefaults.outlinedTextFieldColors(
                            textColor = Color.White,
                            focusedBorderColor = Color.LightGray,
                            focusedLabelColor = Color.White
                        ),
                            modifier = Modifier.fillMaxWidth().weight(1f).padding(8.dp),
                            value = email,
                            label = {
                                Text(text = "Email")
                            },
                            onValueChange = { email = it })

                        OutlinedTextField(
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                textColor = Color.White,
                                focusedBorderColor = Color.LightGray,
                                focusedLabelColor = Color.White
                            ),

                            modifier = Modifier.fillMaxWidth().weight(1f)
                            .padding(8.dp), //visualTransformation = PasswordVisualTransformation(),
                            value = password,
                            label = { Text(text = "Password") },
                            onValueChange = { password = it })
                    }

                    //Кнопки входа и регистрации
                    Row(verticalAlignment = Alignment.CenterVertically) {

                        Button(
                            modifier = Modifier.padding(8.dp).fillMaxWidth().height(50.dp)
                                .weight(1f),

                            enabled = email.isNotEmpty() && password.isNotEmpty(), content = {
                                Text(text = "SignIn")
                            },

                            onClick = {
                                viewModel.signInWithEmailAndPassword(
                                    email.trim(), password.trim()
                                )
                            }, colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color(0xFF4CAF50),
                                disabledBackgroundColor = Color(0xFF262726)
                            )
                        )

                        //Text("or", color= Color.White, fontSize = 20.sp)

                        Button(
                            modifier = Modifier.padding(8.dp).fillMaxWidth().height(50.dp)
                                .weight(1f),

                            enabled = email.isNotEmpty() && password.isNotEmpty(),

                            content = {
                                Text(text = "Register")
                            },

                            onClick = {
                                createAccount(email.trim(), password.trim())
                            },

                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color(0xFF4CAF50),
                                disabledBackgroundColor = Color(0xFF262726)
                            )
                        )
                    }

                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.caption,
                        text = "or Login with",
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

                        modifier = Modifier.padding(8.dp).fillMaxWidth().height(50.dp), onClick = {
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


            //Spacer(modifier = Modifier.height(18.dp))

            when (state.status) {
                LoadingState.Status.SUCCESS -> {

                    //                    Text(text = "Success",
                    //                        color = Color.White,
                    //                        modifier = Modifier.fillMaxWidth(),
                    //                        textAlign = TextAlign.Center
                    //                    )
                    SweetSuccess(
                        message = "Success",
                        duration = Toast.LENGTH_LONG,
                        //padding = PaddingValues(top = 16.dp),
                        contentAlignment = Alignment.BottomCenter
                    )




                }
                LoadingState.Status.FAILED  -> {

                    var s = state.msg ?: "Error"

                    s = if (s.indexOf("Error 403") != -1) "Error 403 Forbidden, please use VPN"
                    else "Error: $s"

                        Text(
                        text = s,
                        maxLines = 7,
                        color = Color.Red,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))



                }
                else                        -> {}
            }


        }


    }


    // Build FirebaseUI sign in intent. For documentation on this operation and all
    // possible customization see: https://github.com/firebase/firebaseui-android
    //    val signInLauncher =
    //        rememberLauncherForActivityResult(
    //        FirebaseAuthUIActivityResultContract()
    //    )
    //        {
    //                result -> this.onSignInResult(result)
    //        }


    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        if (result.resultCode == Activity.RESULT_OK) { // Sign in succeeded
            updateUI(auth.currentUser)
        } else { // Sign in failed
            Toast.makeText(context, "Sign In Failed", Toast.LENGTH_SHORT).show()
            updateUI(null)
        }
    }

    @Composable
    fun startSignIn() {

        val intent = AuthUI.getInstance().createSignInIntentBuilder()
            .setIsSmartLockEnabled(!BuildConfig.DEBUG).setAvailableProviders(
                listOf(
                    AuthUI.IdpConfig.EmailBuilder().build(),
                    AuthUI.IdpConfig.GoogleBuilder().build()
                )
            ).setLogo(R.mipmap.ic_launcher).build()


        val signInLauncher = rememberLauncherForActivityResult(
            FirebaseAuthUIActivityResultContract()
        ) { result ->
            this.onSignInResult(result)
        }

        //signInLauncher.launch(intent)
    }


    //    fun signOut() {
    //        AuthUI.getInstance().signOut(componentActivity!!)
    //        updateUI(null)
    //    }


    //    fun updateUI(user: FirebaseUser?) {
    //
    //        showProgressBar = false
    //
    //        if (user != null) {
    //            status = "Email User: ${user.email} (verified: %{user.isEmailVerified})"
    //            detail = "Firebase UID: ${user.uid}"
    //
    //            emailPasswordButtons_visibility = false
    //            emailPasswordFields_visibility = false
    //            signedInButtons_visibility = true
    //
    //            //Если пользователь верифицирован, то не показываем кнопку верификации
    //            verifyEmailButton_visibility = !user.isEmailVerified
    //        }
    //        else
    //        {
    //            status = "Signed Out"
    //            detail = ""
    //
    //            emailPasswordButtons_visibility = true
    //            emailPasswordFields_visibility = true
    //            signedInButtons_visibility = false
    //        }
    //    }


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

    //    fun signOut() {
    //        auth.signOut()
    //        updateUI(null)
    //    }

    fun updateUI(user: FirebaseUser?) {
        if (user != null) { // Signed in
            status = "Email User: ${user.email} (verified: %{user.isEmailVerified})"
            detail = "Firebase UID: ${user.uid}"
            uid = auth.currentUser?.uid.toString()

            //binding.signInButton.visibility = View.GONE
            //binding.signOutButton.visibility = View.VISIBLE
        } else { // Signed out
            status = "Signed Out"
            detail = ""
            uid = auth.currentUser?.uid.toString()

            //binding.signInButton.visibility = View.VISIBLE
            //binding.signOutButton.visibility = View.GONE
        }
    }


    //var showProgressBar by mutableStateOf(false)
    //var verifyEmailButtonisEnabled by mutableStateOf(false)

    //Visible
    //    var emailPasswordButtons_visibility by mutableStateOf(false)
    //    var emailPasswordFields_visibility by mutableStateOf(false)
    //    var signedInButtons_visibility  by mutableStateOf(false)
    //    var verifyEmailButton_visibility by mutableStateOf(false)

    //    fun reload() {
    //        auth.currentUser!!.reload().addOnCompleteListener { task ->
    //            if (task.isSuccessful) {
    //                updateUI(auth.currentUser)
    //                Toast.makeText(context, "Reload successful!", Toast.LENGTH_SHORT).show()
    //            } else {
    //                Log.e("Firebase", "reload", task.exception)
    //                Toast.makeText(context, "Failed to reload user.", Toast.LENGTH_SHORT).show()
    //            }
    //        }
    //    }
    //
    fun createAccount(email: String, password: String) {
        Log.d("Firebase", "createAccount:$email")


        //            if (!validateForm()) {
        //                return
        //            }

        //            showProgressBar = true

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(componentActivity!!) { task ->
                if (task.isSuccessful) { // Sign in success, update UI with the signed-in user's information
                    Log.d("Firebase", "createUserWithEmail:success")
                    Toast.makeText(
                        context, "createUserWithEmail:success", Toast.LENGTH_LONG
                    ).show()
                    val user = auth.currentUser
                    updateUI(user)
                } else { // If sign in fails, display a message to the user.
                    Log.w("Firebase", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        context, "Authentication failed. ${task.exception}", Toast.LENGTH_LONG
                    ).show() //updateUI(null)
                }

                //                    showProgressBar = false
            }
    } // //    fun signIn(email: String, password: String) { // //        Log.d("Firebase", "signIn:$email")
    //
    //        if (!validateForm()) {
    //            return
    //        }
    //
    //        showProgressBar = true
    //
    //        auth.signInWithEmailAndPassword(email, password)
    //            .addOnCompleteListener(componentActivity!!) { task ->
    //                if (task.isSuccessful) {
    //                    // Sign in success, update UI with the signed-in user's information
    //                    Log.d("Firebase", "signInWithEmail:success")
    //                    val user = auth.currentUser
    //                    updateUI(user)
    //                } else {
    //                    // If sign in fails, display a message to the user.
    //                    Log.w("Firebase", "signInWithEmail:failure", task.exception)
    //                    Toast.makeText(context, "Authentication failed.",
    //                        Toast.LENGTH_SHORT).show()
    //                    updateUI(null)
    //                    checkForMultiFactorFailure(task.exception!!)
    //                }
    //
    //                if (!task.isSuccessful) {
    //                    binding.status.setText(R.string.auth_failed)
    //                }
    //                showProgressBar = false
    //            }
    //    }


    //    fun updateUI(user: FirebaseUser?) {
    //
    //        showProgressBar = false
    //
    //        if (user != null) {
    //            status = "Email User: ${user.email} (verified: %{user.isEmailVerified})"
    //            detail = "Firebase UID: ${user.uid}"
    //
    //            emailPasswordButtons_visibility = false
    //            emailPasswordFields_visibility = false
    //            signedInButtons_visibility = true
    //
    //            //Если пользователь верифицирован, то не показываем кнопку верификации
    //            verifyEmailButton_visibility = !user.isEmailVerified
    //        }
    //        else
    //        {
    //            status = "Signed Out"
    //            detail = ""
    //
    //            emailPasswordButtons_visibility = true
    //            emailPasswordFields_visibility = true
    //            signedInButtons_visibility = false
    //        }
    //    }


    //    fun signOut() {
    //        auth.signOut()
    //        updateUI(null)
    //    }

    //    fun sendEmailVerification() {
    //
    //        // Disable button
    //        verifyEmailButtonisEnabled = false
    //
    //        // Send verification email
    //        val user = auth.currentUser!!
    //        user.sendEmailVerification()
    //            .addOnCompleteListener(componentActivity!!) { task ->
    //                // Re-enable button
    //                verifyEmailButtonisEnabled = true
    //
    //                if (task.isSuccessful) {
    //                    Toast.makeText(context,
    //                        "Verification email sent to ${user.email} ",
    //                        Toast.LENGTH_SHORT).show()
    //                } else {
    //                    Log.e("Firebase", "sendEmailVerification", task.exception)
    //                    Toast.makeText(context,
    //                        "Failed to send verification email.",
    //                        Toast.LENGTH_SHORT).show()
    //                }
    //            }
    //    }


    //    fun validateForm(): Boolean {
    //        var valid = true
    //        if (email.isEmpty()) valid = false
    //        if (password.isEmpty()) valid = false
    //        return valid
    //    }

}