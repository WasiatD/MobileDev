package com.example.wasiatd.ui.detailplant

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.wasiatd.R
import com.example.wasiatd.ui.dashboard.dashboardMainActivity.DashboardMainActivity
import com.example.wasiatd.data.local.ItemDetailPlant
import com.example.wasiatd.data.remote.config.ApiConfig
import com.example.wasiatd.data.remote.config.ApiServices
import com.example.wasiatd.data.remote.responses.GetDetailIotResponse
import com.example.wasiatd.data.remote.responses.UpdatePlantResponse
import com.example.wasiatd.databinding.CustomToastBinding
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailPlantActivity : AppCompatActivity() {
    private lateinit var itemDetailPlantList: MutableList<ItemDetailPlant>
    private lateinit var apiService: ApiServices

    private lateinit var relayTextView: TextView
    private lateinit var suhuTextView: TextView
    private lateinit var kelembapanTextView: TextView
    private lateinit var cahayaTextView: TextView
    private lateinit var reminderTextView: TextView

    private lateinit var plantNameEditText: EditText
    private lateinit var plantDescriptionEditText: EditText
    private lateinit var plantLocationEditText: EditText
    private lateinit var saveButton: Button

    private var iotId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_plant)

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toolbar.setNavigationOnClickListener {
            finish()
        }

        val plantName = intent.getStringExtra("plant_name")
        val plantDescription: String? = intent.getStringExtra("plant_description")
        val plantLocation = intent.getStringExtra("plant_location")
        iotId = intent.getStringExtra("plant_id")

        plantNameEditText = findViewById(R.id.plant_name)
        plantLocationEditText = findViewById(R.id.plant_location)
        plantDescriptionEditText = findViewById(R.id.plant_description)
        reminderTextView = findViewById(R.id.reminderText)


        plantNameEditText.setText(plantName)
        plantLocationEditText.setText(plantLocation)
        plantDescriptionEditText.setText(plantDescription)

        relayTextView = findViewById(R.id.plant_relay)
        suhuTextView = findViewById(R.id.plant_temperature)
        kelembapanTextView = findViewById(R.id.plant_humidity)
        cahayaTextView = findViewById(R.id.plant_light)
        saveButton = findViewById(R.id.button_save)

        itemDetailPlantList = mutableListOf()
        apiService = ApiConfig.getApiService()

        iotId?.let {
            fetchDetailIotResponse(it)
        }

        saveButton.setOnClickListener {
            updatePlantData()
        }
    }

    private fun fetchDetailIotResponse(iotId: String) {
        val call = apiService.getDetailIotResponse(iotId)
        call.enqueue(object : Callback<GetDetailIotResponse> {
            override fun onResponse(call: Call<GetDetailIotResponse>, response: Response<GetDetailIotResponse>) {
                if (response.isSuccessful) {
                    Log.d("DetailPlantActivity", "Response successful")
                    val detailResponse = response.body()
                    Log.d("DetailPlantActivity", "Received response: $detailResponse")
                    detailResponse?.let {
                        itemDetailPlantList.add(ItemDetailPlant(it.relay, it.suhu, it.kelembapan, it.cahaya))
                        Log.d("DetailPlantActivity", "Added item: Relay=${it.relay}, Suhu=${it.suhu}, Kelembapan=${it.kelembapan}. Cahaya=${it.cahaya}")

                        val condition = checkCondition(it.kelembapan, it.suhu, it.cahaya)

                        relayTextView.text = it.relay
                        suhuTextView.text = it.suhu
                        kelembapanTextView.text = it.kelembapan
                        cahayaTextView.text = it.cahaya
                        reminderTextView.text = condition
                    }
                } else {
                    Log.e("DetailPlantActivity", "Response not successful: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<GetDetailIotResponse>, t: Throwable) {
                Log.e("DetailPlantActivity", "API call failed: ${t.message}")
            }
        })
    }

    private fun updatePlantData() {
        val name = plantNameEditText.text.toString().trim()
        val description = plantDescriptionEditText.text.toString().trim()
        val location = plantLocationEditText.text.toString().trim()

        if (name.isEmpty() || description.isEmpty() || location.isEmpty()) {
            showCustomToast("Please fill all fields")
            return
        }

        val jsonObject = JSONObject()
        jsonObject.put("id", iotId)
        jsonObject.put("nama", name)
        jsonObject.put("deskripsi", description)
        jsonObject.put("lokasi", location)

        val requestBody = jsonObject.toString().toRequestBody("application/json".toMediaTypeOrNull())

        val call = apiService.updatePlantData(requestBody)
        call.enqueue(object : Callback<UpdatePlantResponse> {
            override fun onResponse(call: Call<UpdatePlantResponse>, response: Response<UpdatePlantResponse>) {
                if (response.isSuccessful) {
                    val intent = Intent(this@DetailPlantActivity, DashboardMainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                }
            }

            override fun onFailure(call: Call<UpdatePlantResponse>, t: Throwable) {
            }
        })
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

    private fun checkCondition(kelembabanText: String, suhuText: String, cahayaText: String): String {
        val kelembaban: Float? = kelembabanText.toFloatOrNull()
        val suhu: Float? = suhuText.toFloatOrNull()
        val cahaya: Float? = cahayaText.toFloatOrNull()

        val kelembabanCondition: String = when {
            kelembaban == null -> "Invalid humidity"
            kelembaban < 30 -> "Bad humidity"
            kelembaban in 30.0..60.0 -> "Moderate humidity"
            kelembaban > 60 -> "Good humidity"
            else -> "Invalid humidity"
        }

        val suhuCondition: String = when {
            suhu == null -> "Invalid temperature"
            suhu < 15 -> "Bad temperature"
            suhu in 15.0..25.0 -> "Good temperature"
            suhu > 25 -> "Moderate temperature"
            else -> "Invalid temperature"
        }

        val cahayaCondition: String = when {
            cahaya == null -> "Invalid light"
            cahaya < 30 -> "Bad light"
            cahaya in 31.0..70.0 -> "Moderate light"
            cahaya > 70.0 -> "Good light"
            else -> "Invalid light"
        }

        val overallCondition: String = when {
            kelembabanCondition.contains("Invalid") || suhuCondition.contains("Invalid") || cahayaCondition.contains("Invalid") -> {
                "Invalid input provided for one or more parameters."
            }
            kelembabanCondition.contains("Bad") || suhuCondition.contains("Bad") || cahayaCondition.contains("Bad") -> {
                "The plant condition is not good. Please check humidity, temperature, and light levels."
            }
            kelembabanCondition.contains("Moderate") || suhuCondition.contains("Moderate") || cahayaCondition.contains("Moderate") -> {
                "The plant condition is moderate. Consider improving the environment."
            }
            else -> {
                "The plant condition is good."
            }
        }

        return overallCondition
    }

}
