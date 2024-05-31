package com.example.wasiatd

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wasiatd.R
import com.example.yourapp.DashboardAdapter
import com.example.yourapp.ItemDataDashboard
import pl.droidsonroids.gif.GifImageView


class MainActivity : AppCompatActivity() {
    private var gifImageView: GifImageView? = null
    private var recyclerView: RecyclerView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        gifImageView = findViewById<GifImageView>(R.id.gifImageView)
        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        // Set up the RecyclerView
        recyclerView?.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = DashboardAdapter(getData())
        }
    }

    private fun getData(): List<ItemDataDashboard> {
        val data = mutableListOf<ItemDataDashboard>()
        for (i in 1..20) {
            data.add(ItemDataDashboard(
                "Column 1 - $i",
                "Column 2 - $i",
                "Column 3 - $i",
                listOf("Cell 1", "Cell 2", "Cell 3", "Cell 4", "Cell 5", "Cell 6")
            ))
        }
        return data
    }
}
