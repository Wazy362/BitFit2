package com.example.bitfit1

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "nutrition_entry_table")
data class NutritionEntryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "food_name") val foodName: String?,
    @ColumnInfo(name = "calories") val calories: Int?
)