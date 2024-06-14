import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wasiatd.R
import com.example.wasiatd.data.local.ItemDataDashboard

class DashboardAdapter(private val plantList: List<ItemDataDashboard>) : RecyclerView.Adapter<DashboardAdapter.PlantViewHolder>() {

    inner class PlantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val plantName: TextView = itemView.findViewById(R.id.dashboardPlantName)
        val plantLocation: TextView = itemView.findViewById(R.id.dashboardPlantLocation)
//        val plantHumidity: TextView = itemView.findViewById(R.id.dashboardHumidity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.dashboard_item_layout, parent, false)
        return PlantViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {
        val currentItem = plantList[position]

        holder.plantName.text = currentItem.nama
        holder.plantLocation.text = currentItem.lokasi
//        holder.plantHumidity.text = currentItem.humidity
    }

    override fun getItemCount() = plantList.size
}
