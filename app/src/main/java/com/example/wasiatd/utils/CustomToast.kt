package com.example.wasiatd.utils

import android.content.Context
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.wasiatd.R

enum class ToastType {
    SUCCESS, WARNING, ERROR
}

object CustomToast {

    fun showCustomToast(context: Context, message: String, type: ToastType) {
        val inflater = LayoutInflater.from(context)
        val layout = inflater.inflate(R.layout.custom_toast, null)

        val toastText = layout.findViewById<TextView>(R.id.toast_text)
        val toastIcon = layout.findViewById<ImageView>(R.id.toast_image)
        val toastContainer = layout.findViewById<LinearLayout>(R.id.custom_toast_container)

        toastText.text = message

        when (type) {
            ToastType.SUCCESS -> {
                toastIcon.setImageResource(R.drawable.ic_success) // Replace with your success icon
                toastContainer.backgroundTintList = ContextCompat.getColorStateList(context, R.color.successBackground) // Replace with your success color
            }
            ToastType.WARNING -> {
                toastIcon.setImageResource(R.drawable.baseline_arrow_back_24) // Replace with your warning icon
                toastContainer.backgroundTintList = ContextCompat.getColorStateList(context, R.color.warningBackground) // Replace with your warning color
            }
            ToastType.ERROR -> {
                toastIcon.setImageResource(R.drawable.baseline_error_24) // Replace with your error icon
                toastContainer.backgroundTintList = ContextCompat.getColorStateList(context, R.color.errorBackground) // Replace with your error color
            }
        }

        Toast(context).apply {
            duration = Toast.LENGTH_SHORT
            view = layout
            show()
        }
    }
}
