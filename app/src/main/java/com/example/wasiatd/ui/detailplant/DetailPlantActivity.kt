package com.example.wasiatd.ui.detailplant

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.wasiatd.R

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