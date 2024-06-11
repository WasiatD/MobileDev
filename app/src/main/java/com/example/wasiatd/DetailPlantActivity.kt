package com.example.wasiatd

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
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
import com.bumptech.glide.Glide
import com.example.wasiatd.data.remote.config.ApiConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream

class DetailPlantActivity : AppCompatActivity() {

    private lateinit var cameraLauncher: ActivityResultLauncher<Intent>
    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_plant)

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // Show the back button

        toolbar.setNavigationOnClickListener {
            finish() // Handle back navigation when the back button is pressed
        }

        val plantName = intent.getStringExtra("plant_name")
        val plantImageUri = intent.getStringExtra("plant_image_uri")?.let { Uri.parse(it) }

        val plantNameTextView: TextView = findViewById(R.id.plant_name)
        val plantImageView: ImageView = findViewById(R.id.plant_image)
        val cameraButton: Button = findViewById(R.id.button_open_camera)
        val galleryButton: Button = findViewById(R.id.button_open_gallery)
        val decodeButton: Button = findViewById(R.id.button_decode)

        plantNameTextView.text = plantName
        plantImageUri?.let {
            Glide.with(this).load(it).into(plantImageView)
        }

        var currentBitmap: Bitmap? = null
        var base64Image: String? = null

        // Initialize the camera launcher
        cameraLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val imageBitmap = result.data?.extras?.get("data") as Bitmap
                currentBitmap = imageBitmap

//                plantImageView.setImageBitmap(imageBitmap)
                base64Image = bitmapToBase64(currentBitmap!!)
                Log.d("Image Base64", base64Image!!)
            }
        }

        // Initialize the gallery launcher
        galleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val selectedImageUri = result.data?.data
                selectedImageUri?.let {
//                    Glide.with(this).load(it).into(plantImageView)
                    val imageBitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, it)
                    currentBitmap = imageBitmap
                    base64Image = bitmapToBase64(currentBitmap!!)
                    Log.d("Image Base64", base64Image!!)
                }
            }
        }

        cameraButton.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            cameraLauncher.launch(cameraIntent)
        }

        galleryButton.setOnClickListener {
            val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            galleryLauncher.launch(galleryIntent)
        }

        decodeButton.setOnClickListener {
            base64Image?.let {encodedImage ->
                sendImageToApi(encodedImage)
                val decodedBitmap = base64ToBitmap(encodedImage)
                plantImageView.setImageBitmap(decodedBitmap)

            }
        }
    }

    private fun bitmapToBase64(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    private fun base64ToBitmap(base64Str: String): Bitmap {
        val decodedBytes = Base64.decode(base64Str, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    }

    private fun sendImageToApi(base64Image: String) {
        val apiService = ApiConfig().apiService

        GlobalScope.launch(Dispatchers.IO) {
            try {
                // Call the predictDisease endpoint with the base64 encoded image
                val response = apiService.predictDisease(base64Image)
                // Handle the response accordingly
                // For example, you can log the response
                Log.d("API Response", response.toString())
                withContext(Dispatchers.Main) {
                    Toast.makeText(applicationContext, "API Response: $response", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                // Handle exceptions
                Log.e("API Error", e.message ?: "Unknown error")
                withContext(Dispatchers.Main) {
                    Toast.makeText(applicationContext, "API Error: ${e.message ?: "Unknown error"}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}