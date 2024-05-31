package com.example.wasiatd.utils

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.wasiatd.MainActivity
import com.example.wasiatd.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.example.wasiatd.utils.RegisterActivity

class SplashScreenActivity : AppCompatActivity() {

    private val splashTimeOut: Long = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        CoroutineScope(Dispatchers.Main).launch {
            delay(splashTimeOut) // Delay for 3 seconds
            startActivity(Intent(this@SplashScreenActivity, RegisterActivity::class.java))
            finish()
        }
    }
}