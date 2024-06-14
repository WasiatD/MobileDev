package com.example.wasiatd.ui.diseasedetail

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.wasiatd.R

class DiseaseDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_disease_detail)

        val plantDiseaseEditText = findViewById<EditText>(R.id.plantDisease)
        val diseaseDescriptionEditText = findViewById<EditText>(R.id.plantDiseaseDescriptionLabel)

        val predictedClass = intent.getStringExtra("PREDICTED_CLASS")
        val diseaseDescription = intent.getStringExtra("DISEASE_DESCRIPTION")

        Log.d("DiseaseDetailActivity", "Predicted Class: $predictedClass")
        Log.d("DiseaseDetailActivity", "Disease Description: $diseaseDescription")

        // Pastikan nilai tidak null sebelum setText
        plantDiseaseEditText.setText(predictedClass ?: "No predicted class available")
        diseaseDescriptionEditText.setText(diseaseDescription ?: "No description available")
    }
}


