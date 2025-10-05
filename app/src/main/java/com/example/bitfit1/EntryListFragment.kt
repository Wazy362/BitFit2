package com.example.bitfit1

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect

class EntryListFragment : Fragment() {

    private lateinit var entriesRecyclerView: RecyclerView
    private val entries = mutableListOf<NutritionEntry>()
    private lateinit var entryAdapter: NutritionEntryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_entry_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        entriesRecyclerView = view.findViewById(R.id.nutrition_entries_list)

        entryAdapter = NutritionEntryAdapter(requireContext(), entries)
        entriesRecyclerView.adapter = entryAdapter

        entriesRecyclerView.layoutManager = LinearLayoutManager(requireContext()).also {
            val divider = DividerItemDecoration(requireContext(), it.orientation)
            entriesRecyclerView.addItemDecoration(divider)
        }

        lifecycleScope.launch {
            (requireActivity().application as BitFitApplication).db.nutritionEntryDao().getAll().collect { dbList ->
                val mapped = dbList.map { entity ->
                    NutritionEntry(foodName = entity.foodName, calories = entity.calories)
                }
                entries.clear()
                entries.addAll(mapped)
                entryAdapter.notifyDataSetChanged()
            }
        }

        val fabAddEntry: View = view.findViewById(R.id.fab_add_entry)
        fabAddEntry.setOnClickListener {
            val intent = Intent(requireContext(), AddEntryActivity::class.java)
            startActivity(intent)
        }

    }
}
