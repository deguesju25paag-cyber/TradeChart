package com.example.tradechart.domain.repository

import kotlinx.coroutines.flow.Flow

// Repositorio para obtener los datos del gráfico
interface ChartRepository {
    fun getPriceStream(assetId: String, vsCurrency: String): Flow<Double>
}
