package com.example.wasiatd.ui.register

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.example.wasiatd.R
import com.example.wasiatd.databinding.ActivityRegisterBinding
import com.example.wasiatd.ui.login.LoginActivity
import com.example.wasiatd.ui.login.LoginViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: MutableLiveData<String?> = _errorMessage

    private lateinit var binding: ActivityRegisterBinding
    private val registerViewModel by viewModels<RegisterViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.signUpButton.setOnClickListener {

            if(binding.emailField.error == null &&
                binding.passwordField.error == null) {

                val email = binding.emailField.text.toString()
                val password = binding.passwordField.text.toString()

                registerViewModel.getRegisterResponse(email, password)
            }
        }

        binding.subtitleText.setOnClickListener {
            backToLogin()
        }

        // Observe isLoading to show/hide ProgressBar
        registerViewModel.isLoading.observe(this) { isLoading ->
            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }

        // Observe register response
        registerViewModel.register.observe(this) { registerResponse ->
            if (registerResponse?.flag == "true") {
                Toast.makeText(this@RegisterActivity, "Register Success, Log in to continue!", Toast.LENGTH_SHORT).show()
                backToLogin()
            } else {
                Toast.makeText(this@RegisterActivity, "Register Failed", Toast.LENGTH_SHORT).show()
            }
        }

        // Observe error message
        registerViewModel.errorMessage.observe(this) { errorMessage ->
            if (errorMessage != null) {
                val message = if (errorMessage == LoginViewModel.INVALID_CREDENTIALS) R.string.invalidCredential else R.string.systemFailure
                Toast.makeText(this, getString(message), Toast.LENGTH_SHORT).show()
                registerViewModel.clearErrorMessage()
            }
        }
    }

    private fun backToLogin() {
        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}
