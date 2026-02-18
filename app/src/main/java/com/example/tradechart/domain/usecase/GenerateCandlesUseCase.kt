package com.example.tradechart.domain.usecase

import com.example.tradechart.domain.model.Candle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class GenerateCandlesUseCase @Inject constructor(
    private val getPriceStreamUseCase: GetPriceStreamUseCase
) {
    operator fun invoke(assetId: String, vsCurrency: String, candleIntervalSeconds: Long): Flow<List<Candle>> = flow {
        val candleIntervalMillis = TimeUnit.SECONDS.toMillis(candleIntervalSeconds)
        val candles = mutableListOf<Candle>()

        getPriceStreamUseCase(assetId, vsCurrency).collect { price ->
            val currentTime = System.currentTimeMillis()
            val lastCandle = candles.lastOrNull()

            if (lastCandle == null || currentTime >= lastCandle.timestampEnd) {
                val openPrice = lastCandle?.close ?: price
                val timestampStart = currentTime - (currentTime % candleIntervalMillis)
                
                val newCandle = Candle(
                    open = openPrice,
                    close = price,
                    high = maxOf(openPrice, price),
                    low = minOf(openPrice, price),
                    timestampStart = timestampStart, 
                    timestampEnd = timestampStart + candleIntervalMillis
                )
                candles.add(newCandle)
            } else {
                val updatedCandle = lastCandle.copy(
                    high = maxOf(lastCandle.high, price),
                    low = minOf(lastCandle.low, price),
                    close = price
                )
                candles[candles.lastIndex] = updatedCandle
            }
            emit(candles.toList())
        }
    }
}
