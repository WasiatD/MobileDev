package com.example.wasiatd

import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.wasiatd.R

class AddPlantActivity : AppCompatActivity() {

    private lateinit var selectedImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_plant)

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // Show the back button

        toolbar.setNavigationOnClickListener {
            finish() // Handle back navigation when the back button is pressed
        }

        selectedImageView = findViewById(R.id.selected_image)
        selectedImageView.setOnClickListener {
            openImagePicker()
        }
    }

    private val getImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            selectedImageView.setImageURI(it)
        }
    }

    private fun openImagePicker() {
        getImage.launch("image/*")
    }
}
