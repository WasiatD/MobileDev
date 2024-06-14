package com.example.wasiatd.ui.diseasecheck

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.wasiatd.R
import com.example.wasiatd.data.remote.PredictRequest
import com.example.wasiatd.data.remote.config.ApiConfig
import com.example.wasiatd.ui.diseasedetail.DiseaseDetailActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream

class DiseaseCheckActivity : AppCompatActivity() {
    private lateinit var cameraLauncher: ActivityResultLauncher<Intent>
    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>
    private lateinit var plantInformation: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_disease_check)

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // Show the back button

        toolbar.setNavigationOnClickListener {
            finish() // Handle back navigation when the back button is pressed
        }

        plantInformation = findViewById(R.id.diseaseCheckDiseaseInformation)

        val cameraButton: Button = findViewById(R.id.button_open_camera)
        val galleryButton: Button = findViewById(R.id.button_open_gallery)
        val decodeButton: Button = findViewById(R.id.button_decode)
        val detailButton: TextView = findViewById(R.id.detailLabel)

        plantInformation.text = "Lorem Ipsum"

        detailButton.setOnClickListener {
            val intent = Intent(this, DiseaseDetailActivity::class.java)
            startActivity(intent)
        }

        var currentBitmap: Bitmap? = null
        var base64Image: String? = null

        // Initialize the camera launcher
        cameraLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    val imageBitmap = result.data?.extras?.get("data") as Bitmap
                    currentBitmap = resizeAndCropBitmap(imageBitmap)
                    base64Image = bitmapToBase64(currentBitmap!!)
                    Log.d("Image Base64", base64Image!!)
                }
            }

        galleryLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    val selectedImageUri = result.data?.data
                    selectedImageUri?.let {
                        currentBitmap = uriToBitmap(it)
                        currentBitmap = resizeAndCropBitmap(currentBitmap!!)
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
            val galleryIntent =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            galleryLauncher.launch(galleryIntent)
        }

        decodeButton.setOnClickListener {
            base64Image?.let { encodedImage ->
                sendImageToApi(encodedImage)
                val decodedBitmap = base64ToBitmap(encodedImage)
//                plantImageView.setImageBitmap(decodedBitmap)
            }
        }
    }

    private fun bitmapToBase64(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, byteArrayOutputStream) // Adjust quality as needed
        val byteArray = byteArrayOutputStream.toByteArray()
        val base64String = Base64.encodeToString(byteArray, Base64.DEFAULT)
        Log.d("Base64 Length", "Base64 String Length: ${base64String.length}")
        Log.d("Decoded Data Length", "Decoded Data Length: ${byteArray.size}")
        return base64String
    }

    private fun base64ToBitmap(base64Str: String): Bitmap {
        val decodedBytes = Base64.decode(base64Str, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    }

    private fun uriToBitmap(selectedImageUri: Uri): Bitmap? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val source = ImageDecoder.createSource(this.contentResolver, selectedImageUri)
            ImageDecoder.decodeBitmap(source)
        } else {
            MediaStore.Images.Media.getBitmap(this.contentResolver, selectedImageUri)
        }
    }

    private fun resizeAndCropBitmap(bitmap: Bitmap): Bitmap {
        val dimension = minOf(bitmap.width, bitmap.height)
        val croppedBitmap = Bitmap.createBitmap(
            bitmap,
            (bitmap.width - dimension) / 2,
            (bitmap.height - dimension) / 2,
            dimension,
            dimension
        )
        return Bitmap.createScaledBitmap(croppedBitmap, 299, 299, true) // Reduce size to 150x150
    }

    private fun sendImageToApi(base64Image: String) {
        val apiService = ApiConfig.getApiService()


        GlobalScope.launch(Dispatchers.IO) {
            try {
                val predictRequest = PredictRequest(base64Image)
                val response = apiService.predictDisease(predictRequest)

                Log.d("API Response", response.toString())
                withContext(Dispatchers.Main) {
                    // Assuming response contains a field `predictedClass` with the plant information
                    val plantInfo = response.predictedClass
                    plantInformation.text = plantInfo
                    Toast.makeText(
                        applicationContext,
                        "API Response: $response",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                // Handle exceptions
                Log.e("API Error", e.message ?: "Unknown error")
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        applicationContext,
                        "API Error: ${e.message ?: "Unknown error"}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}