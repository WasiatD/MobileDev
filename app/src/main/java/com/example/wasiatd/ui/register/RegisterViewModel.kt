package com.example.wasiatd.ui.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wasiatd.data.remote.config.ApiConfig
import com.example.wasiatd.data.remote.responses.RegisterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel : ViewModel() {

    private val _register = MutableLiveData<RegisterResponse?>()
    val register: MutableLiveData<RegisterResponse?> = _register

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: MutableLiveData<String?> = _errorMessage

    fun getRegisterResponse(email: String, password: String) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().registerUser(email, password)
        client.enqueue(object : Callback<RegisterResponse> {

            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                _isLoading.value = false

                val responseBody = response.body()
                if(responseBody != null) {
                    _register.value = responseBody
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure ${t.message.toString()}")
                _errorMessage.value = LOGIN_FAILURE
            }

        })
    }

    companion object {
        const val TAG = "Register View Model"
        const val LOGIN_FAILURE = "register_failure"
    }

}