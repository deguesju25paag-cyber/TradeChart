package com.example.tradechart.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tradechart.ui.components.CandleChart

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    val candles by viewModel.candles.collectAsState()
    CandleChart(candles = candles)
}
