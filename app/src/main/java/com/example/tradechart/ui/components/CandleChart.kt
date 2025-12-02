package com.example.tradechart.ui.components

import android.graphics.Color
import android.graphics.Paint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.example.tradechart.domain.model.Candle
import com.github.mikephil.charting.charts.CandleStickChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.CandleData
import com.github.mikephil.charting.data.CandleDataSet
import com.github.mikephil.charting.data.CandleEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun CandleChart(modifier: Modifier = Modifier, candles: List<Candle>) {
    AndroidView(
        modifier = modifier.fillMaxSize(),
        factory = {
            CandleStickChart(it).apply {
                description.isEnabled = false
                legend.isEnabled = false
                isDragEnabled = true
                setScaleEnabled(true)
                setDrawGridBackground(false)

                xAxis.apply {
                    position = XAxis.XAxisPosition.BOTTOM
                    setDrawGridLines(false)
                    granularity = 1f
                }

                axisLeft.setDrawGridLines(true)
                axisRight.isEnabled = false
            }
        },
        update = { chart ->
            if (candles.isEmpty()) return@AndroidView

            chart.xAxis.valueFormatter = object : ValueFormatter() {
                private val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
                override fun getAxisLabel(value: Float, axis: com.github.mikephil.charting.components.AxisBase?): String {
                    val index = value.toInt()
                    return if (index >= 0 && index < candles.size) {
                        dateFormat.format(Date(candles[index].timestampStart))
                    } else ""
                }
            }

            val entries = candles.mapIndexed { index, candle ->
                CandleEntry(index.toFloat(), candle.high.toFloat(), candle.low.toFloat(), candle.open.toFloat(), candle.close.toFloat())
            }

            // **LA CORRECCIÓN DEFINITIVA: Crear un DataSet nuevo en cada actualización**
            val dataSet = CandleDataSet(entries, "Price").apply {
                setDrawIcons(false)
                setDrawValues(false)
                shadowColorSameAsCandle = true
                shadowWidth = 0.8f
                decreasingColor = Color.parseColor("#F44336") // Rojo
                increasingColor = Color.parseColor("#4CAF50") // Verde
                neutralColor = Color.LTGRAY // Velas planas
                // Asegurar que TODAS las velas se rellenen
                decreasingPaintStyle = Paint.Style.FILL
                increasingPaintStyle = Paint.Style.FILL
            }

            // Reemplazar los datos del gráfico por completo
            chart.data = CandleData(dataSet)
            
            chart.invalidate()

            chart.setVisibleXRangeMaximum(60f)
            chart.moveViewToX(entries.size.toFloat())
        }
    )
}
