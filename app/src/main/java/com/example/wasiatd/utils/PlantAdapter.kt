import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wasiatd.R
import com.example.wasiatd.data.local.ItemDataDashboard
import com.example.wasiatd.data.local.Plant
import com.example.wasiatd.ui.detailplant.DetailPlantActivity

class PlantAdapter(private val plantList: List<ItemDataDashboard>) : RecyclerView.Adapter<PlantAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val plantName: TextView = itemView.findViewById(R.id.listPlantName)
        val plantLocation: TextView = itemView.findViewById(R.id.listPlanLocation)
        val plantDescription: TextView = itemView.findViewById(R.id.listPlantDescription3)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.plant_list_layout, parent, false)
        return TaskViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val currentItem = plantList[position]

        holder.plantName.text = currentItem.nama
        holder.plantLocation.text = currentItem.lokasi
        holder.plantDescription.text = currentItem.deskripsi
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, DetailPlantActivity::class.java).apply {
                putExtra("plant_name", currentItem.nama)
                putExtra("plant_location", currentItem.lokasi)
                putExtra("plant_description", currentItem.deskripsi)
                putExtra("plant_id", currentItem.id)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = plantList.size
}
