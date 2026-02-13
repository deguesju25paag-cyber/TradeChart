package com.example.tradechart.ui.components

import android.graphics.Color
import android.graphics.Paint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.example.tradechart.domain.model.Candle
import com.github.mikephil.charting.charts.CandleStickChart
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.CandleData
import com.github.mikephil.charting.data.CandleDataSet
import com.github.mikephil.charting.data.CandleEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun CandleChart(modifier: Modifier = Modifier, candles: List<Candle>) {
    // Colores estilo TradingView Dark
    val backgroundColor = Color.parseColor("#131722") // Fondo muy oscuro
    val gridColor = Color.parseColor("#363c4e")       // Rejilla sutil
    val textColor = Color.parseColor("#b2b5be")       // Texto gris claro
    val greenColor = Color.parseColor("#089981")      // Verde TradingView
    val redColor = Color.parseColor("#f23645")        // Rojo TradingView

    AndroidView(
        modifier = modifier.fillMaxSize(),
        factory = {
            CandleStickChart(it).apply {
                // --- Configuración General ---
                setBackgroundColor(backgroundColor)
                description.isEnabled = false
                legend.isEnabled = false
                setDrawBorders(false)
                
                // Gestos
                isDragEnabled = true
                setScaleEnabled(true)
                setPinchZoom(true)
                isHighlightPerDragEnabled = false
                isHighlightPerTapEnabled = false

                // --- Eje X (Tiempo) ---
                xAxis.apply {
                    position = XAxis.XAxisPosition.BOTTOM
                    setDrawGridLines(true)
                    this.gridColor = gridColor
                    enableGridDashedLine(10f, 10f, 0f) // Línea discontinua
                    this.textColor = textColor
                    granularity = 1f
                    setAvoidFirstLastClipping(true)
                }

                // --- Eje Y (Precio) ---
                axisLeft.apply {
                    setDrawGridLines(true)
                    this.gridColor = gridColor
                    enableGridDashedLine(10f, 10f, 0f)
                    this.textColor = textColor
                    setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART) // Precio dentro del gráfico
                    setDrawAxisLine(false) // Quitar la línea vertical del eje
                }
                axisRight.isEnabled = false
            }
        },
        update = { chart ->
            if (candles.isEmpty()) return@AndroidView

            // Formateador de fechas
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

            // --- Configuración del DataSet (Velas) ---
            val dataSet = CandleDataSet(entries, "Price").apply {
                axisDependency = YAxis.AxisDependency.LEFT
                setDrawIcons(false)
                setDrawValues(false)
                
                // SOMBRAS (Mechas) - Clave para que se vean bien
                shadowColorSameAsCandle = true // Usa el color de la vela para la sombra
                shadowWidth = 1.3f // Grosor visible

                // Colores
                decreasingColor = redColor
                decreasingPaintStyle = Paint.Style.FILL
                increasingColor = greenColor
                increasingPaintStyle = Paint.Style.FILL
                neutralColor = textColor // Por si open == close
                
                // Configuración extra para asegurar visualización
                setDrawHorizontalHighlightIndicator(false)
                setDrawVerticalHighlightIndicator(false)
            }

            // --- Línea de Precio Actual (Animación visual) ---
            chart.axisLeft.removeAllLimitLines()
            
            if (entries.isNotEmpty()) {
                val lastEntry = entries.last()
                val currentPrice = lastEntry.close
                // Lógica correcta para el color: Cierre >= Apertura -> Verde, si no Rojo
                val isUp = lastEntry.close >= lastEntry.open
                val lineColor = if (isUp) greenColor else redColor

                val limitLine = LimitLine(currentPrice, String.format("%.2f", currentPrice)).apply {
                    lineWidth = 1f
                    this.lineColor = lineColor
                    this.textColor = textColor // Texto de la etiqueta
                    labelPosition = LimitLine.LimitLabelPosition.RIGHT_TOP
                    enableDashedLine(5f, 5f, 0f)
                }
                chart.axisLeft.addLimitLine(limitLine)
            }

            // Actualizar datos
            chart.data = CandleData(dataSet)
            chart.notifyDataSetChanged()
            chart.invalidate()

            // --- Auto-scroll inteligente ---
            // Solo movemos la vista al final si el usuario ya estaba mirando el final (o muy cerca)
            // Esto permite hacer scroll atrás sin que te "arrastre" al presente
            val isScrolledToEnd = chart.highestVisibleX >= (chart.data.entryCount - 10)
            
            // Permitimos zoom libre (quitamos setVisibleXRangeMaximum fijo)
            // Pero al inicio o si estamos al final, ajustamos para ver las últimas
            if (isScrolledToEnd) {
               chart.moveViewToX(entries.size.toFloat())
            }
        }
    )
}
