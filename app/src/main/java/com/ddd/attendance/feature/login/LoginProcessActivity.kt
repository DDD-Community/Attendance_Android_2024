package com.ddd.attendance.feature.login

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat
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

@AndroidEntryPoint
class LoginProcessActivity : ComponentActivity() {
    private val TAG = "LoginProcessActivity"
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth

    private val googleLoginLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)
                account?.idToken?.let { idToken ->
                    Log.d(TAG, idToken)
                    // TODO: ViewModel 예정
                    handleGoogleIdToken(idToken = idToken)
                }
            } catch (e: ApiException) {
                Log.d(TAG, "Google Sign-In failed ${e.message}")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestIdToken("369957721624-ajhu5s3msc9jbhsrjgp561lpghaik9ji.apps.googleusercontent.com")
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
        auth = Firebase.auth


        window.statusBarColor = DDD_BLACK.toArgb()
        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars = false
        setContent {
            AttendanceTheme {
                LoginProcessScreen(
                    onClickGoogle = {
                        val signInIntent = googleSignInClient.signInIntent
                        googleLoginLauncher.launch(signInIntent)
                    }
                )
            }
        }
    }

    private fun handleGoogleIdToken(idToken: String) {
        val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(firebaseCredential)
            .addOnCompleteListener(this) {
                if(it.isSuccessful) {
                    // 정상 완료
                    Log.d(TAG, "로그인 성공, ${it.result.user?.uid}")
                } else {
                    Log.d(TAG, "로그인 실패, ${it.exception?.message}")
                }
            }
    }
}