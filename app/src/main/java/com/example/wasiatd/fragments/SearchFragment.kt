package com.example.wasiatd.fragments

import DashboardAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wasiatd.R
import com.example.wasiatd.utils.ItemDataDashboard

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class SearchFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

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
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        // Make sure that the view is not null before accessing its properties
        val recyclerView: RecyclerView? = view.findViewById(R.id.recyclerView)
        recyclerView?.layoutManager = LinearLayoutManager(context)

        // Create dummy data
        val dummyData = listOf(
            ItemDataDashboard("Plant 1", "Healthy", "10:00 AM"),
            ItemDataDashboard("Plant 2", "Needs Water", "11:00 AM"),
            ItemDataDashboard("Plant 3", "Fertilized", "12:00 PM"),
            ItemDataDashboard("Plant 4", "Healthy", "1:00 PM"),
            ItemDataDashboard("Plant 5", "Needs Water", "2:00 PM"),
            ItemDataDashboard("Plant 6", "Fertilized", "3:00 PM"),
            ItemDataDashboard("Plant 7", "Healthy", "4:00 PM"),
            ItemDataDashboard("Plant 8", "Needs Water", "5:00 PM"),
            ItemDataDashboard("Plant 9", "Fertilized", "6:00 PM"),
            ItemDataDashboard("Plant 10", "Healthy", "7:00 PM"),
            ItemDataDashboard("Plant 11", "Needs Water", "8:00 PM"),
            ItemDataDashboard("Plant 12", "Fertilized", "9:00 PM"),
            ItemDataDashboard("Plant 13", "Healthy", "10:00 PM"),
            ItemDataDashboard("Plant 14", "Needs Water", "11:00 PM"),
            ItemDataDashboard("Plant 15", "Fertilized", "12:00 AM"),
            ItemDataDashboard("Plant 16", "Healthy", "1:00 AM"),
            ItemDataDashboard("Plant 17", "Needs Water", "2:00 AM"),
            ItemDataDashboard("Plant 18", "Fertilized", "3:00 AM"),
            ItemDataDashboard("Plant 19", "Healthy", "4:00 AM"),
            ItemDataDashboard("Plant 20", "Needs Water", "5:00 AM")
        )

        val adapter = DashboardAdapter(dummyData)
        recyclerView?.adapter = adapter

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SearchFragment.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}