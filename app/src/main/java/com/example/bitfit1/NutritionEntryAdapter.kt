package com.example.bitfit1

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bitfit1.NutritionEntry

class NutritionEntryAdapter(private val context: Context, private val entries: List<NutritionEntry>) :
    RecyclerView.Adapter<NutritionEntryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_nutrition_entry, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val entry = entries[position]
        holder.bind(entry)
    }

    override fun getItemCount() = entries.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val foodNameTextView = itemView.findViewById<TextView>(R.id.food_name_text)
        private val caloriesTextView = itemView.findViewById<TextView>(R.id.calories_text)

        fun bind(entry: NutritionEntry) {
            foodNameTextView.text = entry.foodName ?: "Unknown Food"
            caloriesTextView.text = "${entry.calories ?: 0} Calories"
        }
    }
}