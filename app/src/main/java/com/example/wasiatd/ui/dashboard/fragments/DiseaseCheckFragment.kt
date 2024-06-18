package com.example.wasiatd.ui.dashboard.fragments

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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.wasiatd.R
import com.example.wasiatd.data.remote.PredictRequest
import com.example.wasiatd.data.remote.config.ApiConfig
import com.example.wasiatd.ui.diseasedetail.DiseaseDetailActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream

class DiseaseCheckFragment : Fragment() {
    private lateinit var cameraLauncher: ActivityResultLauncher<Intent>
    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>
    private lateinit var plantInformation: TextView
    private lateinit var detailButton: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var capturedImageView: ImageView
    private lateinit var overlayView: View


    private var currentBitmap: Bitmap? = null
    private var base64Image: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_disease_check, container, false)
        plantInformation = view.findViewById(R.id.diseaseCheckDiseaseInformation)
        detailButton = view.findViewById(R.id.detailLabel)
        progressBar = view.findViewById(R.id.progressBar)
        capturedImageView = view.findViewById(R.id.capturedImageView)
        overlayView = view.findViewById(R.id.overlayView)


        val cameraButton: Button = view.findViewById(R.id.button_open_camera)
        val galleryButton: Button = view.findViewById(R.id.button_open_gallery)
        val decodeButton: Button = view.findViewById(R.id.button_decode)
        detailButton.visibility = View.GONE

        // Initialize the camera launcher
        cameraLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == AppCompatActivity.RESULT_OK) {
                    val imageBitmap = result.data?.extras?.get("data") as Bitmap
                    currentBitmap = resizeAndCropBitmap(imageBitmap)
                    base64Image = bitmapToBase64(currentBitmap!!)
                    Log.d("Image Base64", base64Image!!)
                    capturedImageView.setImageBitmap(currentBitmap)  // Set the captured image to ImageView
                }
            }

        galleryLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == AppCompatActivity.RESULT_OK) {
                    val selectedImageUri = result.data?.data
                    selectedImageUri?.let {
                        currentBitmap = uriToBitmap(it)
                        currentBitmap = resizeAndCropBitmap(currentBitmap!!)
                        base64Image = bitmapToBase64(currentBitmap!!)
                        Log.d("Image Base64", base64Image!!)
                        capturedImageView.setImageBitmap(currentBitmap)  // Set the selected image to ImageView
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
            }
        }

        return view
    }

    private fun bitmapToBase64(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, byteArrayOutputStream)
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
            val source = ImageDecoder.createSource(requireActivity().contentResolver, selectedImageUri)
            ImageDecoder.decodeBitmap(source)
        } else {
            MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, selectedImageUri)
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
        return Bitmap.createScaledBitmap(croppedBitmap, 299, 299, true)
    }

    private fun sendImageToApi(base64Image: String) {
        val apiService = ApiConfig.getApiService()
        showLoading(true)
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val predictRequest = PredictRequest(base64Image)
                val response = apiService.predictDisease(predictRequest)

                Log.d("API Response", response.toString())
                withContext(Dispatchers.Main) {
                    val plantInfo = response.predictedClass
                    Log.d("Predicted Class", "Plant Info: $plantInfo")

                    plantInfo?.let { fetchDiseaseDetails(it) }

                    detailButton.setOnClickListener {
                        val intent =
                            Intent(requireContext(), DiseaseDetailActivity::class.java)
                        intent.putExtra("PREDICTED_CLASS", plantInfo)
                        startActivity(intent)
                    }
                }
            } catch (e: Exception) {
                Log.e("API Error", e.message ?: "Unknown error")
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        requireContext(),
                        "API Error: ${e.message ?: "Unknown error"}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun fetchDiseaseDetails(disease: String) {
        val apiService = ApiConfig.getApiService()

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = apiService.getDiseaseInfo(disease)

                Log.d("Disease Info Response", response.toString())

                val formatDiseaseName = formatDiseaseName(disease)
                plantInformation.text = formatDiseaseName
                withContext(Dispatchers.Main) {
                    showCustomToast("Success")
                    detailButton.visibility = View.VISIBLE
                    detailButton.setOnClickListener {
                        val intent = Intent(requireContext(), DiseaseDetailActivity::class.java)
                        intent.putExtra("PREDICTED_CLASS", formatDiseaseName)
                        intent.putExtra("DISEASE_DESCRIPTION", response.diseaseInfo)
                        startActivity(intent)
                    }
                }
            } catch (e: Exception) {
                Log.e("API Error", e.message ?: "Unknown error")
                withContext(Dispatchers.Main) {
                    showCustomToast("Failed to get disease info Please Try Again")
                }
            } finally {
                withContext(Dispatchers.Main) {
                    showLoading(false)
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            overlayView.visibility = View.VISIBLE
            progressBar.visibility = View.VISIBLE
        } else {
            overlayView.visibility = View.GONE
            progressBar.visibility = View.GONE
        }
    }

    private fun formatDiseaseName(disease: String): String {
        val diseaseTrimmed = disease.replace("_+".toRegex(), " ")
        Log.d("diseaseTrim", diseaseTrimmed)
        return diseaseTrimmed
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DiseaseCheckFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
    private fun showCustomToast(message: String) {
        val inflater = LayoutInflater.from(requireContext())
        val layout = inflater.inflate(R.layout.custom_toast, requireView().findViewById(R.id.custom_toast_container))

        layout.findViewById<TextView>(R.id.toast_text).text = message

        Toast(requireContext()).apply {
            duration = Toast.LENGTH_SHORT
            view = layout
            show()
        }
    }
}
