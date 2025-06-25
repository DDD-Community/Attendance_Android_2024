package com.ddd.attendance.feature.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.app.ActivityOptionsCompat
import com.ddd.attendance.core.ui.theme.AttendanceTheme
import com.ddd.attendance.feature.login.LoginProcessActivity
import com.ddd.attendance.feature.main.MainActivity
import com.ddd.attendance.feature.main.screen.ScreenName
import com.ddd.attendance.feature.splash.screen.SplashScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AttendanceTheme {
                SplashScreen(
                    goNext = { screenName ->
                        val options = ActivityOptionsCompat.makeCustomAnimation(this, android.R.anim.fade_in, android.R.anim.fade_out)
                        when(screenName) {
                            ScreenName.MEMBER.name, ScreenName.ADMIN.name -> {
                                startActivity(Intent(this, MainActivity::class.java), options.toBundle())
                            }
                            else -> {
                                startActivity(Intent(this, LoginProcessActivity::class.java), options.toBundle())
                            }
                        }
                        finish()
                    }
                )
            }
        }
    }
}

