package com.example.wasiatd.utils

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wasiatd.R
import com.example.wasiatd.data.local.ItemDataTask
import com.example.wasiatd.ui.detailplant.DetailPlantActivity

class TaskAdapter(private val taskList: List<ItemDataTask>) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskTitle: TextView = itemView.findViewById(R.id.task_title)
        val taskContent: TextView = itemView.findViewById(R.id.task_description)
        val taskDate: TextView = itemView.findViewById(R.id.task_create)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.task_item_layout, parent, false)
        return TaskViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val currentItem = taskList[position]

        holder.taskTitle.text = currentItem.title
        holder.taskDate.text = currentItem.date
        holder.taskContent.text = currentItem.content
    }


    override fun getItemCount() = taskList.size
}
