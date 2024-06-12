package com.example.wasiatd.fragments

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
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        progressBar = view.findViewById(R.id.progressBar)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Initialize Retrofit and ApiService
        val apiService = ApiConfig().apiService

        // Create an empty list to hold ItemDataDashboard objects
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
                        // Iterate through each IsiItem object
                        for (isiItem in isiItems) {
                            // Access properties of isiItem
                            val id = isiItem?.id
                            val lokasi = isiItem?.lokasi
                            val tanaman = isiItem?.Tanaman

                            // Create an ItemDataDashboard object using selected properties from IsiItem
                            val itemDataDashboard = ItemDataDashboard("$id", "$lokasi", "$tanaman")

                            // Add the ItemDataDashboard object to the list
                            itemDataDashboardList.add(itemDataDashboard)
                        }

                        // Create adapter with the list of ItemDataDashboard objects and set it to RecyclerView
                        val adapter = DashboardAdapter(itemDataDashboardList)
                        recyclerView.adapter = adapter

                        progressBar.visibility = View.GONE
                        recyclerView.visibility = View.VISIBLE
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
