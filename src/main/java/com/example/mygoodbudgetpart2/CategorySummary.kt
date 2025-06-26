package com.example.mygoodbudgetpart2



data class CategorySummary(
    val id: Long,
    val name: String,
    val totalSpent: Double,
    val budget: Double = 0.0
)