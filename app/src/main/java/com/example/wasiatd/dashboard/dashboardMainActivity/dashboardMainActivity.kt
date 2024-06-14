package com.example.wasiatd.dashboard.dashboardMainActivity

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.wasiatd.R
import com.example.wasiatd.data.remote.config.ApiConfig
import com.example.wasiatd.fragments.DiseaseCheckFragment
import com.example.wasiatd.fragments.HomeFragment
import com.example.wasiatd.fragments.PlantFragment
import com.example.wasiatd.fragments.TasksFragment
import com.example.wasiatd.fragments.SearchFragment
import com.example.wasiatd.ui.diseasecheck.DiseaseCheckActivity
import com.example.wasiatd.ui.login.LoginActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class dashboardMainActivity : AppCompatActivity() {
    private val homeFragment = HomeFragment()
    private val plantFragment = PlantFragment()
    private val tasksFragment = TasksFragment()
    private val searchFragment = SearchFragment()
    private val diseaseCheckFragment = DiseaseCheckFragment()
    private lateinit var sharedPreferences: SharedPreferences
    private var activeFragment: Fragment = homeFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard_main)

        sharedPreferences = getSharedPreferences("wasiatd-data", MODE_PRIVATE)

        val auth = sharedPreferences.getString("idToken", null)
        if(auth != null) {
            ApiConfig.setAuth(auth)
        }

        supportFragmentManager.beginTransaction().apply {
            add(R.id.fragment_container, diseaseCheckFragment, "4")
            hide(diseaseCheckFragment)
            add(R.id.fragment_container, tasksFragment, "3")
            hide(tasksFragment)
            add(R.id.fragment_container, plantFragment, "2")
            hide(plantFragment)
            add(R.id.fragment_container, homeFragment, "1")
        }.commit()

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    val homeFragment = HomeFragment.newInstance("param1", "param2")
                    supportFragmentManager.commit {
                        replace(R.id.fragment_container, homeFragment)
                        addToBackStack(null) // Optional: Add transaction to back stack
                    }
                    true
                }
                R.id.navigation_plant -> {
                    val plantFragment = PlantFragment.newInstance("param1", "param2")
                    supportFragmentManager.commit {
                        replace(R.id.fragment_container, plantFragment)
                        addToBackStack(null) // Optional: Add transaction to back stack
                    }
                    true
                }
                R.id.navigation_tasks -> {
                    val tasksFragment = TasksFragment.newInstance("param1", "param2")
                    supportFragmentManager.commit {
                        replace(R.id.fragment_container, tasksFragment)
                        addToBackStack(null) // Optional: Add transaction to back stack
                    }
                    true
                }
                R.id.navigation_search -> {
//                    Log.d("Search", "Yaallah Tolong Aku Plis")
//                    val diseaseCheckFragment = DiseaseCheckFragment.newInstance("param1", "param2")
//                    supportFragmentManager.commit {
//                        replace(R.id.fragment_container, diseaseCheckFragment)
//                        addToBackStack(null) // Optional: Add transaction to back stack
//                    }
                    val intent = Intent(this, DiseaseCheckActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }

        val logoutButton = findViewById<ImageView>(R.id.logoutButton)
        logoutButton.setOnClickListener {
            sharedPreferences.edit().clear().apply()
            ApiConfig.clearAuth()
            val intent = Intent(this, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }
}