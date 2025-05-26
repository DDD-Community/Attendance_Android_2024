package com.ddd.attendance.feature.login

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ddd.attendance.core.model.login.GoogleLogin
import com.ddd.attendance.core.ui.theme.AttendanceTheme
import com.ddd.attendance.core.ui.theme.DDD_BLACK
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow

@AndroidEntryPoint
class LoginProcessActivity : ComponentActivity() {

    companion object {
        private const val TAG = "LoginProcessActivity"
        private const val CLIENT_ID = "369957721624-ajhu5s3msc9jbhsrjgp561lpghaik9ji.apps.googleusercontent.com"
    }

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth

    private val googleLogin = MutableStateFlow(GoogleLogin())

    private val googleLoginLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        handleSignInResult(result)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initGoogleSignIn()
        initFirebaseAuth()
        setupWindow()

        setContent {
            val userInfo by googleLogin.collectAsStateWithLifecycle()

            AttendanceTheme {
                LoginProcessScreen(
                    userInfo = userInfo,
                    onClickGoogle = { launchGoogleSignIn() }
                )
            }
        }
    }

    private fun initGoogleSignIn() {
        val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestIdToken(CLIENT_ID)
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, options)
    }

    private fun initFirebaseAuth() {
        auth = Firebase.auth
    }

    private fun setupWindow() {
        window.statusBarColor = DDD_BLACK.toArgb()
        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars = false
    }

    private fun launchGoogleSignIn() {
        googleLoginLauncher.launch(googleSignInClient.signInIntent)
    }

    private fun handleSignInResult(result: ActivityResult) {
        if (result.resultCode != RESULT_OK) return

        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            account?.idToken?.let { handleGoogleIdToken(it) }
        } catch (e: ApiException) {
            Log.e(TAG, "Google Sign-In failed: ${e.message}", e)
        }
    }

    private fun handleGoogleIdToken(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = task.result.user

                    Log.i(TAG, "구글 로그인 성공: ${user?.email}, ${user?.displayName}, ${user?.uid}")

                    googleLogin.value = GoogleLogin(
                        email = user?.email.orEmpty(),
                        displayName = user?.displayName.orEmpty(),
                        uid = user?.uid.orEmpty()
                    )
                } else {
                    Log.e(TAG, "구글 로그인 실패: ${task.exception?.message}", task.exception)
                }
            }
    }
}