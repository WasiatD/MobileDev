package com.example.yourapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wasiatd.R

class DashboardAdapter(private val data: List<ItemDataDashboard>) : RecyclerView.Adapter<DashboardAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.dashboard_item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.column1.text = item.column1
        holder.column2.text = item.column2
        holder.column3.text = item.column3

    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val column1: TextView = view.findViewById(R.id.plant_name)
        val column2: TextView = view.findViewById(R.id.status)
        val column3: TextView = view.findViewById(R.id.time)
    }
}
