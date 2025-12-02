package com.example.tradechart.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userId: Long,
    val price: Double,
    val timestamp: Long,
    val type: String, // "buy" or "sell"
    val amount: Double // Cantidad de BTC
)
