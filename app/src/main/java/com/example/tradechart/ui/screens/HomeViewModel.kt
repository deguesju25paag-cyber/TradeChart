package com.example.tradechart.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tradechart.domain.model.Candle
import com.example.tradechart.domain.usecase.GenerateCandlesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

enum class TimeFrame(val label: String, val seconds: Long) {
    M1("1m", 60),
    M5("5m", 5 * 60),
    M15("15m", 15 * 60),
    H1("1h", 60 * 60)
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val generateCandlesUseCase: GenerateCandlesUseCase
) : ViewModel() {

    // --- Chart Data ---
    private val _candles = MutableStateFlow<List<Candle>>(emptyList())
    val candles: StateFlow<List<Candle>> = _candles.asStateFlow()

    private val _selectedTimeFrame = MutableStateFlow(TimeFrame.M1)
    val selectedTimeFrame: StateFlow<TimeFrame> = _selectedTimeFrame.asStateFlow()

    private val _currentPrice = MutableStateFlow(0.0)
    val currentPrice: StateFlow<Double> = _currentPrice.asStateFlow()

    // --- Trading Data ---
    private val _usdBalance = MutableStateFlow(10000.0) // $10,000 Fake Money Start
    val usdBalance: StateFlow<Double> = _usdBalance.asStateFlow()

    private val _assetBalance = MutableStateFlow(0.0) // Cantidad de Bitcoin
    val assetBalance: StateFlow<Double> = _assetBalance.asStateFlow()

    private var candlesJob: Job? = null

    init {
        startCandleGeneration(TimeFrame.M1)
    }

    fun onTimeFrameSelected(timeFrame: TimeFrame) {
        if (_selectedTimeFrame.value == timeFrame) return
        
        _selectedTimeFrame.value = timeFrame
        startCandleGeneration(timeFrame)
    }

    // --- Trading Functions ---

    fun buy(amountUsd: Double) {
        val price = _currentPrice.value
        if (price > 0 && _usdBalance.value >= amountUsd) {
            val assetAmount = amountUsd / price
            _usdBalance.value -= amountUsd
            _assetBalance.value += assetAmount
        }
    }

    fun sell(amountAsset: Double) { // Vender cantidad de Bitcoin
        val price = _currentPrice.value
        if (price > 0 && _assetBalance.value >= amountAsset) {
            val usdAmount = amountAsset * price
            _assetBalance.value -= amountAsset
            _usdBalance.value += usdAmount
        }
    }
    
    // Función auxiliar para vender todo (útil para un botón "Vender Todo")
    fun sellAll() {
        sell(_assetBalance.value)
    }

    private fun startCandleGeneration(timeFrame: TimeFrame) {
        candlesJob?.cancel()
        _candles.value = emptyList()

        candlesJob = generateCandlesUseCase("bitcoin", "usd", timeFrame.seconds)
            .onEach { updatedCandles ->
                _candles.value = updatedCandles
                // Actualizar el precio actual con el cierre de la última vela
                updatedCandles.lastOrNull()?.let {
                    _currentPrice.value = it.close
                }
            }
            .launchIn(viewModelScope)
    }
}
