package com.example.mygoodbudgetpart2



data class Income(
    val id: Long = 0,
    val amount: Double,
    val source: String,
    val date: String,
    val userId: Long
)