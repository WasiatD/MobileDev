package com.example.wasiatd.ui.register

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.wasiatd.databinding.ActivityRegisterBinding
import com.example.wasiatd.ui.login.LoginActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val registerViewModel by viewModels<RegisterViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.signUpButton.setOnClickListener {
            Log.d("RegisterActivity", "Sign Up button clicked")

            if(binding.emailField.error == null &&
                binding.passwordField.error == null) {

                val email = binding.emailField.text.toString()
                val password = binding.passwordField.text.toString()

                Log.d("RegisterActivity", "Email: $email, dan Password: $password")

                registerViewModel.getRegisterResponse(email, password)
            }
        }

        binding.subtitleText.setOnClickListener {
            backToLogin()
        }
    }

    private fun backToLogin() {
        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}
