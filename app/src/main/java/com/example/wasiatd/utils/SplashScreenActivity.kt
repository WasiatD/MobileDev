package com.example.wasiatd.utils

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.wasiatd.R
import com.example.wasiatd.ui.dashboard.dashboardMainActivity.dashboardMainActivity
import com.example.wasiatd.ui.login.LoginActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {
    private val splashTimeOut: Long = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        CoroutineScope(Dispatchers.Main).launch {
            delay(splashTimeOut)
            checkSession()
        }
    }

    private fun checkSession() {
        val sharedPreferences = getSharedPreferences("wasiatd-data", MODE_PRIVATE)
        val idToken = sharedPreferences.getString("idToken", null)

        if (idToken.isNullOrEmpty()) {
            startActivity(Intent(this, LoginActivity::class.java))
        } else {
            startActivity(Intent(this, dashboardMainActivity::class.java))
        }
        finish()
    }
}