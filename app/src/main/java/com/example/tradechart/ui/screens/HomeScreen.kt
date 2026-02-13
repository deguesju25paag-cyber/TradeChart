package com.example.tradechart.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tradechart.ui.components.CandleChart

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    val candles by viewModel.candles.collectAsState()
    val selectedTimeFrame by viewModel.selectedTimeFrame.collectAsState()
    val usdBalance by viewModel.usdBalance.collectAsState()
    val assetBalance by viewModel.assetBalance.collectAsState()
    val currentPrice by viewModel.currentPrice.collectAsState()

    val backgroundColor = Color.parseColor("#131722")
    val panelColor = Color.parseColor("#1E222D")
    val greenColor = Color.parseColor("#089981")
    val redColor = Color.parseColor("#F23645")
    val textColor = Color.White
    val labelColor = Color.Gray

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        // --- Barra Superior: Selector de Temporalidad ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            TimeFrame.values().forEach { timeFrame ->
                TimeFrameChip(
                    text = timeFrame.label,
                    isSelected = timeFrame == selectedTimeFrame,
                    onClick = { viewModel.onTimeFrameSelected(timeFrame) }
                )
            }
        }

        // --- Panel de Información (Balance y Precio) ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .background(panelColor, RoundedCornerShape(8.dp))
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = "Balance Available", color = labelColor, fontSize = 12.sp)
                Text(text = "$${String.format("%,.2f", usdBalance)}", color = textColor, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(text = "Bitcoin Held", color = labelColor, fontSize = 12.sp)
                Text(text = "${String.format("%.4f", assetBalance)} BTC", color = textColor, fontSize = 16.sp)
            }
            
            Column(horizontalAlignment = Alignment.End) {
                Text(text = "Current Price", color = labelColor, fontSize = 12.sp)
                Text(text = "$${String.format("%,.2f", currentPrice)}", color = textColor, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }
        }

        // --- Gráfico ---
        Box(modifier = Modifier.weight(1f)) {
            CandleChart(candles = candles)
        }

        // --- Botones de Trading ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Botón VENDER
            Button(
                onClick = { viewModel.sellAll() }, // Vende todo por simplicidad
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = redColor)
            ) {
                Text(text = "SELL ALL", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }

            // Botón COMPRAR
            Button(
                onClick = { viewModel.buy(1000.0) }, // Compra $1000
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = greenColor)
            ) {
                Text(text = "BUY $1k", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun TimeFrameChip(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected) Color.parseColor("#2962FF") else Color.Transparent
    val textColor = if (isSelected) Color.White else Color.Gray

    Box(
        modifier = Modifier
            .padding(end = 8.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(backgroundColor)
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

fun Color.Companion.parseColor(colorString: String): Color {
    return Color(android.graphics.Color.parseColor(colorString))
}
