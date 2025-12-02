package com.example.tradechart.data.repository

import com.example.tradechart.data.api.CoinGeckoApi
import com.example.tradechart.domain.repository.ChartRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ChartRepositoryImpl @Inject constructor(
    private val api: CoinGeckoApi
) : ChartRepository {

    override fun getPriceStream(assetId: String, vsCurrency: String): Flow<Double> = flow {
        while (true) {
            try {
                val response = api.getPrice(assetId, vsCurrency)
                val price = response[assetId]?.get(vsCurrency)
                if (price != null) {
                    emit(price)
                }
            } catch (e: Exception) {
                // Manejar la excepción, por ejemplo, emitiendo un error o reintentando
                e.printStackTrace()
            }
            delay(5000L) // Actualizar cada 5 segundos
        }
    }.flowOn(Dispatchers.IO) // <-- ¡Esta es la corrección!
}
