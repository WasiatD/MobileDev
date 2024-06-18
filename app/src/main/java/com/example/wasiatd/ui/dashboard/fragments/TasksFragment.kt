package com.example.wasiatd.ui.dashboard.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wasiatd.R
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wasiatd.data.local.ItemDataTask
import com.example.wasiatd.data.remote.config.ApiConfig
import com.example.wasiatd.utils.TaskAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class TasksFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tasks, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        val apiService = ApiConfig.getApiService()
        val itemDataTaskList = mutableListOf<ItemDataTask>()

        GlobalScope.launch(Dispatchers.IO) {
            try {
                withContext(Dispatchers.Main) {
                    recyclerView.visibility = View.GONE
                }
                val response = apiService.getNotes()
                val dataFromApi = response.notes
                withContext(Dispatchers.Main) {
                    dataFromApi.let { notes ->
                        for (note in notes) {
                            val id = note.id
                            val title = note.title
                            val content = note.content
                            val date = note.date

                            val itemDataTask = ItemDataTask(title, content, date, id)

                            itemDataTaskList.add(itemDataTask)
                        }
                        val adapter = TaskAdapter(itemDataTaskList)
                        recyclerView.adapter = adapter
                        recyclerView.visibility = View.VISIBLE
                    }
                }
            } catch (e: Exception) {
                // Handle API error
                Log.e("API Error", e.message ?: "Unknown error")
                withContext(Dispatchers.Main) {
                    recyclerView.visibility = View.VISIBLE
                }
            }
        }
        return view
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TasksFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
