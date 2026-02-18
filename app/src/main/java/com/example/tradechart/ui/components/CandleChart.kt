package com.example.tradechart.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.viewinterop.AndroidView
import com.example.tradechart.domain.model.Candle
import com.github.mikephil.charting.charts.CandleStickChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.CandleData
import com.github.mikephil.charting.data.CandleDataSet
import com.github.mikephil.charting.data.CandleEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun CandleChart(modifier: Modifier = Modifier, candles: List<Candle>) {
    val chartColor = Color(0xFF9DA2B5)
    val decreasingColor = android.graphics.Color.parseColor("#F23645")
    val increasingColor = android.graphics.Color.parseColor("#089981")

    AndroidView(modifier = modifier, factory = { context ->
        CandleStickChart(context).apply {
            setBackgroundColor(android.graphics.Color.TRANSPARENT)
            description.isEnabled = false
            setTouchEnabled(true)
            isDragEnabled = true
            setScaleEnabled(true)
            setPinchZoom(true)
            legend.isEnabled = false

            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                setDrawGridLines(false)
                textColor = chartColor.hashCode()
                granularity = 1f
                valueFormatter = DateAxisValueFormatter(candles.map { it.timestampStart })
            }

            axisLeft.apply {
                setDrawGridLines(false)
                textColor = chartColor.hashCode()
            }
            axisRight.isEnabled = false
        }
    }, update = { chart ->
        val entries = candles.mapIndexed { index, candle ->
            CandleEntry(
                index.toFloat(),
                candle.high.toFloat(),
                candle.low.toFloat(),
                candle.open.toFloat(),
                candle.close.toFloat()
            )
        }

        val dataSet = CandleDataSet(entries, "Price").apply {
            setDrawIcons(false)
            axisDependency = YAxis.AxisDependency.LEFT
            shadowColor = android.graphics.Color.DKGRAY
            shadowWidth = 0.7f
            decreasingColor = decreasingColor
            decreasingPaintStyle = Paint.Style.FILL
            increasingColor = increasingColor
            increasingPaintStyle = Paint.Style.FILL
            neutralColor = android.graphics.Color.BLUE
            valueTextColor = android.graphics.Color.WHITE
            setDrawValues(false)
        }

        chart.data = CandleData(dataSet)
        chart.invalidate()
    })
}

class DateAxisValueFormatter(private val timestamps: List<Long>) : IndexAxisValueFormatter() {
    private val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
    override fun getFormattedValue(value: Float): String {
        val index = value.toInt()
        return if (index >= 0 && index < timestamps.size) {
            sdf.format(Date(timestamps[index]))
        } else {
            ""
        }
    }
}
