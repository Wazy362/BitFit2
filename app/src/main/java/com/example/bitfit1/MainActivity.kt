package com.example.bitfit1

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bitfit1.databinding.ActivityMainBinding // FIX: Ensure this import is correct
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import com.example.bitfit1.NutritionEntryEntity
import com.example.bitfit1.AddEntryActivity

const val NUTRITION_ENTRY_EXTRA = "NUTRITION_ENTRY_EXTRA"

class MainActivity : AppCompatActivity() {
    private lateinit var entriesRecyclerView: RecyclerView
    private lateinit var binding: ActivityMainBinding

    private val entries = mutableListOf<NutritionEntry>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        entriesRecyclerView = binding.nutritionEntriesList

        val entryAdapter = NutritionEntryAdapter(this, entries)
        entriesRecyclerView.adapter = entryAdapter

        entriesRecyclerView.layoutManager = LinearLayoutManager(this).also {
            val dividerItemDecoration = DividerItemDecoration(this, it.orientation)
            entriesRecyclerView.addItemDecoration(dividerItemDecoration)
        }

        lifecycleScope.launch {
            (application as BitFitApplication).db.nutritionEntryDao().getAll().collect { databaseList ->
                val mappedList = databaseList.map { entity: NutritionEntryEntity ->
                    NutritionEntry(
                        foodName = entity.foodName,
                        calories = entity.calories
                    )
                }

                entries.clear()
                entries.addAll(mappedList)
                entryAdapter.notifyDataSetChanged()
            }
        }

        binding.addEntryButton.setOnClickListener {
            val intent = Intent(this, AddEntryActivity::class.java)
            startActivity(intent)
        }
    }
}