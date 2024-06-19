package com.example.wasiatd.ui.dashboard.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wasiatd.R
import com.example.wasiatd.data.local.ItemDataDashboard
import DashboardAdapter
import android.util.Log
import android.widget.ProgressBar
import android.widget.TextView
import com.example.wasiatd.data.remote.config.ApiConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var plantCount: TextView
    private lateinit var tasksCount: TextView


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
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        progressBar = view.findViewById(R.id.progressBar)
        recyclerView = view.findViewById(R.id.recyclerView)
        plantCount = view.findViewById(R.id.plantCount)
        tasksCount = view.findViewById(R.id.tasksCount)
        recyclerView.layoutManager = LinearLayoutManager(context)

        val apiService = ApiConfig.getApiService()
        val itemDataDashboardList = mutableListOf<ItemDataDashboard>()

        GlobalScope.launch(Dispatchers.IO) {
            try {
                withContext(Dispatchers.Main) {
                    progressBar.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                }

                val response = apiService.getIot()
                val dataFromApi = response.isi
                withContext(Dispatchers.Main) {
                    dataFromApi?.let { isiItems ->
                        for (isiItem in isiItems) {
                            val id = isiItem?.id
                            val nama = isiItem?.nama
                            val lokasi = isiItem?.lokasi
                            val deskripsi = isiItem?.deskripsi

                            val itemDataDashboard = ItemDataDashboard("$id", "$nama", "$deskripsi", "$lokasi")

                            itemDataDashboardList.add(itemDataDashboard)
                        }
                        val responseTask = apiService.getNotes()
                        val dataFromApiTask = responseTask.notes

                        val adapter = DashboardAdapter(itemDataDashboardList)
                        recyclerView.adapter = adapter

                        progressBar.visibility = View.GONE
                        recyclerView.visibility = View.VISIBLE
                        plantCount.text = itemDataDashboardList.size.toString()
                        tasksCount.text = dataFromApiTask.size.toString()
                    }
                }
            } catch (e: Exception) {
                // Handle API error
                Log.e("API Error", e.message ?: "Unknown error")
                withContext(Dispatchers.Main) {
                    progressBar.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE
                }
            }
        }

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
