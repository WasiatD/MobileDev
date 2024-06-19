import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wasiatd.R
import com.example.wasiatd.data.local.ItemDataDashboard
import com.example.wasiatd.ui.detailplant.DetailPlantActivity

class DashboardAdapter(private val plantList: List<ItemDataDashboard>) : RecyclerView.Adapter<DashboardAdapter.PlantViewHolder>() {

    inner class PlantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val plantName: TextView = itemView.findViewById(R.id.dashboardPlantName)
        val plantLocation: TextView = itemView.findViewById(R.id.dashboardPlantLocation)
        val plantDescription: TextView = itemView.findViewById(R.id.dashboardPlantDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.dashboard_item_layout, parent, false)
        return PlantViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {
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
