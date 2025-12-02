package com.example.tradechart.domain.usecase

import com.example.tradechart.domain.repository.ChartRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPriceStreamUseCase @Inject constructor(
    private val chartRepository: ChartRepository
) {
    operator fun invoke(assetId: String, vsCurrency: String): Flow<Double> {
        return chartRepository.getPriceStream(assetId, vsCurrency)
    }
}
