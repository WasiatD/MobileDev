package com.example.wasiatd.ui.task

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.wasiatd.R
import com.example.wasiatd.data.local.ItemDataTask
import com.example.wasiatd.data.remote.NotesRequest
import com.example.wasiatd.data.remote.config.ApiConfig
import com.example.wasiatd.data.remote.config.ApiServices
import com.example.wasiatd.data.remote.responses.GetNotesResponse
import com.example.wasiatd.databinding.CustomToastBinding
import com.example.wasiatd.ui.dashboard.dashboardMainActivity.DashboardMainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddTaskActivity : AppCompatActivity() {

    private lateinit var itemDataTaskList: MutableList<ItemDataTask>
    private lateinit var apiService: ApiServices

    private lateinit var taskTitleEditText: EditText
    private lateinit var taskContentEditText: EditText
    private lateinit var saveButton: Button

    private var currentId = 1 // Variable untuk menyimpan ID yang akan digunakan

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toolbar.setNavigationOnClickListener {
            finish()
        }

        taskTitleEditText = findViewById(R.id.task_title_field)
        taskContentEditText = findViewById(R.id.task_content_field)
        saveButton = findViewById(R.id.addTaskButtonNew)

        itemDataTaskList = mutableListOf()
        apiService = ApiConfig.getApiService()

        // Inisialisasi currentId dari SharedPreferences
        currentId = getCurrentId()

        saveButton.setOnClickListener {
            updateTaskData()
        }
    }

    private fun updateTaskData() {
        val taskTitle = taskTitleEditText.text.toString().trim()
        val taskContent = taskContentEditText.text.toString().trim()

        if (taskTitle.isEmpty() || taskContent.isEmpty()) {
            showCustomToast("Please fill all fields")
            return
        }

        val id = getNextId()
        val notesRequest = NotesRequest(
            title = taskTitle,
            id = "notes$id",
            content = taskContent
        )

        val call = apiService.addNotes(notesRequest)
        call.enqueue(object : Callback<GetNotesResponse> {
            override fun onResponse(call: Call<GetNotesResponse>, response: Response<GetNotesResponse>) {
                if (response.isSuccessful) {
                    saveCurrentId(currentId) // Simpan currentId yang baru
                    val intent = Intent(this@AddTaskActivity, DashboardMainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    showCustomToast("Failed to create task")
                }
            }

            override fun onFailure(call: Call<GetNotesResponse>, t: Throwable) {
                showCustomToast("Failed to create task")
            }
        })
    }

    private fun getNextId(): Int {
        val id = currentId
        currentId = if (currentId < 1000) currentId + 1 else 1
        return id
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

    private fun saveCurrentId(id: Int) {
        val sharedPreferences = getSharedPreferences("task_prefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("current_id", id)
        editor.apply()
    }

    private fun getCurrentId(): Int {
        val sharedPreferences = getSharedPreferences("task_prefs", MODE_PRIVATE)
        return sharedPreferences.getInt("current_id", 1)
    }
}
