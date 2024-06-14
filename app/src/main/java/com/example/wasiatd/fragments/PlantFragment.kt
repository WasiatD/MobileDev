package com.example.wasiatd.fragments

import DashboardAdapter
import PlantAdapter
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wasiatd.R
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wasiatd.data.local.ItemDataDashboard
import com.example.wasiatd.data.local.Plant
import com.example.wasiatd.data.remote.config.ApiConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class PlantFragment : Fragment() {
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
            val view = inflater.inflate(R.layout.fragment_plant, container, false)
            recyclerView = view.findViewById(R.id.recyclerView)
            recyclerView.layoutManager = LinearLayoutManager(context)

            val apiService = ApiConfig.getApiService()
            val itemDataDashboardList = mutableListOf<ItemDataDashboard>()

            GlobalScope.launch(Dispatchers.IO) {
                try {
                    withContext(Dispatchers.Main) {
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
                            val adapter = PlantAdapter(itemDataDashboardList)
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
            PlantFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

//    class PlantFragment : Fragment() {
//        // TODO: Rename and change types of parameters
//        private var param1: String? = null
//        private var param2: String? = null
//
//        override fun onCreate(savedInstanceState: Bundle?) {
//            super.onCreate(savedInstanceState)
//            arguments?.let {
//                param1 = it.getString(ARG_PARAM1)
//                param2 = it.getString(ARG_PARAM2)
//            }
//        }
//
//        override fun onCreateView(
//            inflater: LayoutInflater, container: ViewGroup?,
//            savedInstanceState: Bundle?
//        ): View? {
//            val view = inflater.inflate(R.layout.fragment_plant, container, false)
//
//            val recyclerView: RecyclerView? = view.findViewById(R.id.recyclerView)
//            recyclerView?.layoutManager = LinearLayoutManager(context)
//
//            val adapter = PlantAdapter(getPlants())
//            recyclerView?.adapter = adapter
//
//            return view
//        }
//
//        private fun getPlants(): List<Plant> {
//            return listOf(
//                Plant(
//                    name = "Tomato",
//                    location = "Greenhouse",
//                    description = "Loren Ipsum"
//                ),
//                Plant(
//                    name = "Potato",
//                    location = "Field",
//                    description = "Loren Ipsum"
//                ),
//                Plant(
//                    name = "Strawberry",
//                    location = "Garden",
//                    description = "Loren Ipsum"
//                )
//            )
//        }
//
//        companion object {
//            /**
//             * Use this factory method to create a new instance of
//             * this fragment using the provided parameters.
//             *
//             * @param param1 Parameter 1.
//             * @param param2 Parameter 2.
//             * @return A new instance of fragment PlantFragment.
//             */
//            @JvmStatic
//            fun newInstance(param1: String, param2: String) =
//                PlantFragment().apply {
//                    arguments = Bundle().apply {
//                        putString(ARG_PARAM1, param1)
//                        putString(ARG_PARAM2, param2)
//                    }
//                }
//        }
//    }
}
