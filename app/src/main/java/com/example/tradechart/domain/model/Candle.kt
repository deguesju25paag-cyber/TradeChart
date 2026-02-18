package com.example.tradechart.domain.model

data class Candle(
    val open: Double,
    val close: Double,
    val high: Double,
    val low: Double,
    val timestampStart: Long, // Start of the candle interval
    val timestampEnd: Long // End of the candle interval
)
