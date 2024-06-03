package com.example.wasiatd.dashboard.dashboardMainActivity

import DashboardAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wasiatd.R
import com.example.wasiatd.utils.ItemDataDashboard
import com.google.android.material.bottomnavigation.BottomNavigationView

class dashboardMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard_main)

        // Create a list of Plant objects (replace with your data)
        val plantList = listOf(
            ItemDataDashboard("Plant 1", "Healthy", "10:00 AM"),
            ItemDataDashboard("Plant 2", "Needs Watering", "11:00 AM"),
            ItemDataDashboard("Plant 3", "Healthy", "12:00 PM")
        )

        // Initialize the RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = DashboardAdapter(plantList)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        // Set up the navigation item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    // Handle home navigation
                    true
                }
                R.id.navigation_dashboard -> {
                    // Handle dashboard navigation
                    true
                }
                R.id.navigation_notifications -> {
                    // Handle notifications navigation
                    true
                }
                R.id.navigation_profile -> {
                    // Handle profile navigation
                    true
                }
                else -> false
            }
        }
    }
}
