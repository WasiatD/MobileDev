package com.example.wasiatd.dashboard.dashboardMainActivity

import DashboardAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wasiatd.R
import com.example.wasiatd.fragments.HomeFragment
import com.example.wasiatd.fragments.PlantFragment
import com.example.wasiatd.fragments.TasksFragment
import com.example.wasiatd.fragments.SearchFragment
import com.example.wasiatd.utils.ItemDataDashboard
import com.google.android.material.bottomnavigation.BottomNavigationView

class dashboardMainActivity : AppCompatActivity() {
    private val homeFragment = HomeFragment()
    private val plantFragment = PlantFragment()
    private val tasksFragment = TasksFragment()
    private val searchFragment = SearchFragment()
    private var activeFragment: Fragment = homeFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard_main)

        // Inside onCreateView() or onViewCreated() of each fragment


        supportFragmentManager.beginTransaction().apply {
            add(R.id.fragment_container, searchFragment, "4")
            hide(searchFragment)
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
                    val searchFragment = SearchFragment.newInstance("param1", "param2")
                    supportFragmentManager.commit {
                        replace(R.id.fragment_container, searchFragment)
                        addToBackStack(null) // Optional: Add transaction to back stack
                    }
                    true
                }
                else -> false
            }
        }
    }
}
