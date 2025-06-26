package com.example.mygoodbudgetpart2



data class BudgetGoal(
    val id: Long = 0,
    val month: String,
    val minGoal: Double,
    val maxGoal: Double,
    val userId: Long
)