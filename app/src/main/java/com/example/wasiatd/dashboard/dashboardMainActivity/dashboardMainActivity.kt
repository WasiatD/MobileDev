package com.example.wasiatd.dashboard.dashboardMainActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.wasiatd.R
import com.example.wasiatd.fragments.HomeFragment
import com.example.wasiatd.fragments.PlantFragment
import com.example.wasiatd.fragments.TasksFragment
import com.example.wasiatd.fragments.SearchFragment
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
                    supportFragmentManager.beginTransaction().hide(activeFragment).show(homeFragment).commit()
                    activeFragment = homeFragment
                    true
                }
                R.id.navigation_plant -> {
                    supportFragmentManager.beginTransaction().hide(activeFragment).show(plantFragment).commit()
                    activeFragment = plantFragment
                    true
                }
                R.id.navigation_tasks -> {
                    supportFragmentManager.beginTransaction().hide(activeFragment).show(tasksFragment).commit()
                    activeFragment = tasksFragment
                    true
                }
                R.id.navigation_search -> {
                    supportFragmentManager.beginTransaction().hide(activeFragment).show(searchFragment).commit()
                    activeFragment = searchFragment
                    true
                }
                else -> false
            }
        }
    }
}
