package com.example.bitfit1

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.bitfit1.NutritionEntryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NutritionEntryDao {
    @Query("SELECT * FROM nutrition_entry_table ORDER BY id ASC")
    fun getAll(): Flow<List<NutritionEntryEntity>>

    @Insert
    suspend fun insert(entry: NutritionEntryEntity)
}