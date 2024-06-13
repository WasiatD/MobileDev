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
        val plantHumidity: TextView = itemView.findViewById(R.id.listPlantHumidity)
//        val id = isiItem?.id
//        val name = isiItem?.name
//        val location = isiItem?.location
//        val suhu = isiItem?.suhu
//        val humidity = isiItem?.kelembapan
//        val ph = isiItem?.ph
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.plant_list_layout, parent, false)
        return TaskViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val currentItem = plantList[position]

        holder.plantName.text = currentItem.name
//        Log.d("PlantAdapter", "onBindViewHolder: ${currentItem.name}")
        holder.plantLocation.text = currentItem.location
//        Log.d("PlantAdapter", "onBindViewHolder: ${currentItem.location}")
        holder.plantHumidity.text = currentItem.humidity
//        Log.d("PlantAdapter", "onBindViewHolder: ${currentItem.humidity}")
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, DetailPlantActivity::class.java).apply {
                putExtra("plant_name", currentItem.name)
                putExtra("plant_location", currentItem.location)
                putExtra("plant_humidity", currentItem.humidity)
                putExtra("plant_suhu", currentItem.suhu)
                putExtra("plant_ph", currentItem.ph)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = plantList.size
}
