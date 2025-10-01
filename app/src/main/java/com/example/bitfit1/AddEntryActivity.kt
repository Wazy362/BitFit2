package com.example.bitfit1

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.bitfit1.NutritionEntryEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddEntryActivity : AppCompatActivity() {

    private lateinit var foodInput: EditText
    private lateinit var caloriesInput: EditText
    private lateinit var saveButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_entry)

        foodInput = findViewById(R.id.food_name_input)
        caloriesInput = findViewById(R.id.calories_input)
        saveButton = findViewById(R.id.save_button)

        saveButton.setOnClickListener {
            saveNutritionEntry()
        }
    }

    private fun saveNutritionEntry() {
        val foodName = foodInput.text.toString().trim()
        val caloriesString = caloriesInput.text.toString().trim()

        if (foodName.isEmpty() || caloriesString.isEmpty()) {
            Toast.makeText(this, "Please enter both food name and calories.", Toast.LENGTH_SHORT).show()
            return
        }

        val calories = caloriesString.toIntOrNull()
        if (calories == null || calories < 0) {
            Toast.makeText(this, "Please enter a valid calorie amount.", Toast.LENGTH_SHORT).show()
            return
        }

        val newEntry = NutritionEntryEntity(
            foodName = foodName,
            calories = calories
        )

        lifecycleScope.launch(Dispatchers.IO) {
            (application as BitFitApplication).db.nutritionEntryDao().insert(newEntry)

            runOnUiThread {
                Toast.makeText(this@AddEntryActivity, "Nutrition entry saved!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}