import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wasiatd.DetailPlantActivity
import com.example.wasiatd.R
import com.example.wasiatd.data.local.Plant

class PlantAdapter(private val plantList: List<Plant>) : RecyclerView.Adapter<PlantAdapter.PlantViewHolder>() {

    class PlantViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val plantImage: ImageView = view.findViewById(R.id.plant_image)
        val plantName: TextView = view.findViewById(R.id.plant_name)
        // Additional views can be added here if needed
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.plant_item_layout, parent, false)
        return PlantViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {
        val plant = plantList[position]
        holder.plantName.text = plant.name
        Glide.with(holder.itemView.context)
            .load(plant.imageUri)
            .into(holder.plantImage)

        // Set an OnClickListener on the item view
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, DetailPlantActivity::class.java).apply {
                putExtra("plant_name", plant.name)
                putExtra("plant_image_uri", plant.imageUri.toString()) // Assuming imageUri is of type Uri
                // Add other plant details here if needed
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = plantList.size
}
