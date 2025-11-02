package com.thejan.stockapp.domain.usecase

import com.thejan.stockapp.data.model.MarketSummaryResponse
import com.thejan.stockapp.domain.repository.MarketRepository
import javax.inject.Inject

class GetMarketSummaryUseCase @Inject constructor(
    private val repository: MarketRepository,
) {
    suspend operator fun invoke(): MarketSummaryResponse = repository.getMarketSummary()
}
