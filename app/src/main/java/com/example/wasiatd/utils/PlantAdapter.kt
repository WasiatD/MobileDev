import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wasiatd.R
import com.example.wasiatd.data.local.Plant
import com.example.wasiatd.ui.detailplant.DetailPlantActivity

class PlantAdapter(private val plantList: List<Plant>) : RecyclerView.Adapter<PlantAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val plantName: TextView = itemView.findViewById(R.id.plantName)
        val plantLocation: TextView = itemView.findViewById(R.id.plantLocation)
        val plantDescription: TextView = itemView.findViewById(R.id.plantDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.plant_list_layout, parent, false)
        return TaskViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val currentItem = plantList[position]

        holder.plantName.text = currentItem.name
        holder.plantLocation.text = currentItem.location
        holder.plantDescription.text = currentItem.description
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, DetailPlantActivity::class.java).apply {
                putExtra("plant_name", currentItem.name)
//                putExtra("plant_location", plant.imageUri.toString()) // Assuming imageUri is of type Uri
//                // Add other plant details here if needed
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = plantList.size
}
