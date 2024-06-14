package com.example.wasiatd.ui.detailplant

import android.content.Intent
import android.graphics.*
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.example.wasiatd.ui.diseasedetail.DiseaseDetailActivity
import com.example.wasiatd.R
import com.example.wasiatd.dashboard.dashboardMainActivity.dashboardMainActivity
import com.example.wasiatd.data.remote.PredictRequest
import com.example.wasiatd.data.remote.config.ApiConfig
import com.example.wasiatd.data.remote.responses.LoginResponse
import com.example.wasiatd.data.remote.responses.PredictResponse
import com.example.wasiatd.databinding.ActivityDetailPlantBinding
import com.example.wasiatd.ui.login.LoginActivity
import com.example.wasiatd.ui.login.LoginViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream

class DetailPlantActivity : AppCompatActivity() {
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
        val plantTemperature = intent.getStringExtra("plant_suhu")
        val plantLocation = intent.getStringExtra("plant_location")
        val plantHumidity = intent.getStringExtra("plant_humidity")
        val plantPh = intent.getStringExtra("plant_ph")

        val plantNameTextView: TextView = findViewById(R.id.plant_name)
        val plantTemperatureTextView: TextView = findViewById(R.id.plant_temperature)
        val plantLocationTextView: TextView = findViewById(R.id.plant_location)
        val plantHumidityTextView: TextView = findViewById(R.id.plant_humidity)
        val plantPhTextView: TextView = findViewById(R.id.plant_ph)

        plantNameTextView.text = plantName
        plantTemperatureTextView.text = plantTemperature
        plantLocationTextView.text = plantLocation
        plantHumidityTextView.text = plantHumidity
        plantPhTextView.text = plantPh
    }
}