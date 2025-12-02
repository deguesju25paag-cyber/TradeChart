package com.example.tradechart.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tradechart.domain.model.Candle
import com.example.tradechart.domain.usecase.GenerateCandlesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val generateCandlesUseCase: GenerateCandlesUseCase
) : ViewModel() {

    private val _candles = MutableStateFlow<List<Candle>>(emptyList())
    val candles: StateFlow<List<Candle>> = _candles.asStateFlow()

    init {
        // El intervalo de las velas es de 60 segundos (1 minuto)
        generateCandlesUseCase("bitcoin", "usd", 60)
            .onEach { updatedCandles ->
                _candles.value = updatedCandles
            }
            .launchIn(viewModelScope)
    }
}
