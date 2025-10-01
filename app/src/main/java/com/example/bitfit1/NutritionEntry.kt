package com.example.bitfit1

import java.io.Serializable

data class NutritionEntry(
    val foodName: String?,
    val calories: Int?
) : Serializable