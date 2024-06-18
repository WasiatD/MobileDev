package com.example.wasiatd.ui.login

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.wasiatd.R
import com.example.wasiatd.databinding.ActivityLoginBinding
import com.example.wasiatd.databinding.CustomToastBinding
import com.example.wasiatd.ui.dashboard.dashboardMainActivity.DashboardMainActivity
import com.example.wasiatd.data.remote.config.ApiConfig
import com.example.wasiatd.ui.register.RegisterActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val loginViewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        sharedPreferences = getSharedPreferences("wasiatd-data", MODE_PRIVATE)

        val auth = sharedPreferences.getString("idToken", null)
        if (auth != null) {
            ApiConfig.setAuth(auth)
            val intent = Intent(this@LoginActivity, DashboardMainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        binding.loginButton.setOnClickListener {
            if (binding.emailField.error == null && binding.passwordField.error == null) {
                val email = binding.emailField.text.toString()
                val password = binding.passwordField.text.toString()

                binding.progressBar.visibility = View.VISIBLE

                loginViewModel.getLoginInfo(email, password)

                loginViewModel.login.observe(this) {
                    binding.progressBar.visibility = View.GONE

                    if (it?.registered == true) {
                        val editor = sharedPreferences.edit()
                        editor.putString("idToken", it.idToken)
                        editor.putString("email", it.email)
                        editor.apply()

                        showCustomToast("You are signed in!")

                        val intent = Intent(this@LoginActivity, DashboardMainActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                    } else {
                        showCustomToast(getString(R.string.invalidCredential))
                    }
                }

                loginViewModel.errorMessage.observe(this) { errorMessage ->
                    if (errorMessage != null) {
                        binding.progressBar.visibility = View.GONE

                        val message = if (errorMessage == LoginViewModel.INVALID_CREDENTIALS) {
                            getString(R.string.invalidCredential)
                        } else {
                            getString(R.string.systemFailure)
                        }
                        showCustomToast(message)
                        loginViewModel.clearErrorMessage()
                    }
                }
            }
        }

        binding.loginLink.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showCustomToast(message: String) {
        val inflater = LayoutInflater.from(this)
        val binding = CustomToastBinding.inflate(inflater)
        binding.toastText.text = message

        Toast(this).apply {
            duration = Toast.LENGTH_SHORT
            view = binding.root
            show()
        }
    }
}
