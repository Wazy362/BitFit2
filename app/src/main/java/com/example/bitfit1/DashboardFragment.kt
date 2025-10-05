package com.example.bitfit1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.text.DecimalFormat

class DashboardFragment : Fragment() {

    private lateinit var totalEntriesText: TextView
    private lateinit var totalCaloriesText: TextView
    private lateinit var avgCaloriesText: TextView
    private lateinit var clearDataButton: Button
    private val df = DecimalFormat("#.##")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        totalEntriesText = view.findViewById(R.id.total_entries_text)
        totalCaloriesText = view.findViewById(R.id.total_calories_text)
        avgCaloriesText = view.findViewById(R.id.avg_calories_text)
        clearDataButton = view.findViewById(R.id.btn_clear_data)

        lifecycleScope.launch {
            (requireActivity().application as BitFitApplication).db.nutritionEntryDao().getAll().collect { list ->
                val totalEntries = list.size
                val totalCalories = list.mapNotNull { it.calories ?: 0 }.sum()
                val avgCalories = if (totalEntries > 0) totalCalories.toDouble() / totalEntries else 0.0

                totalEntriesText.text = "Total entries: $totalEntries"
                totalCaloriesText.text = "Total calories: $totalCalories"
                avgCaloriesText.text = "Average calories: ${df.format(avgCalories)}"
            }
        }

        clearDataButton.setOnClickListener {
            lifecycleScope.launch {
                (requireActivity().application as BitFitApplication).db.nutritionEntryDao().deleteAll()
                Toast.makeText(requireContext(), "All data cleared", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
