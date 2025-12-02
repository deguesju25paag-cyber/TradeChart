package com.example.tradechart.domain.model

// Modelo de datos para una vela japonesa
data class Candle(
    val open: Double,
    val close: Double,
    val high: Double,
    val low: Double,
    val timestampStart: Long,
    val timestampEnd: Long
)
