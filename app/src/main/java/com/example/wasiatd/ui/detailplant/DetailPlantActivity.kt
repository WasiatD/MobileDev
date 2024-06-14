package com.example.wasiatd.ui.detailplant

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.wasiatd.R
import com.example.wasiatd.data.local.ItemDetailPlant
import com.example.wasiatd.data.remote.config.ApiConfig
import com.example.wasiatd.data.remote.config.ApiServices
import com.example.wasiatd.data.remote.responses.GetDetailIotResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailPlantActivity : AppCompatActivity() {
    private lateinit var itemDetailPlantList: MutableList<ItemDetailPlant>
    private lateinit var apiService: ApiServices

    private lateinit var phTextView: TextView
    private lateinit var suhuTextView: TextView
    private lateinit var kelembapanTextView: TextView

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
        val iotId = intent.getStringExtra("plant_id") // Ambil IoT ID dari intent

        val plantNameTextView: TextView = findViewById(R.id.plant_name)
        val plantLocationTextView: TextView = findViewById(R.id.plant_location)
        val plantDescriptionTextView: TextView = findViewById(R.id.plant_description)

        plantNameTextView.text = plantName
        plantLocationTextView.text = plantLocation
        plantDescriptionTextView.text = plantDescription

        // Initialize TextViews for displaying API response data
        phTextView = findViewById(R.id.plant_ph)
        suhuTextView = findViewById(R.id.plant_temperature)
        kelembapanTextView = findViewById(R.id.plant_humidity)

        itemDetailPlantList = mutableListOf()
        apiService = ApiConfig.getApiService()

        iotId?.let {
            fetchDetailIotResponse(it)
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
                        itemDetailPlantList.add(ItemDetailPlant(it.ph, it.suhu, it.kelembapan))
                        Log.d("DetailPlantActivity", "Added item: PH=${it.ph}, Suhu=${it.suhu}, Kelembapan=${it.kelembapan}")

                        phTextView.text = it.ph
                        suhuTextView.text = it.suhu
                        kelembapanTextView.text = it.kelembapan
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
}
