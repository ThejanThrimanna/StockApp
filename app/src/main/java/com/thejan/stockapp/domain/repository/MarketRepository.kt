package com.thejan.stockapp.domain.repository

import com.thejan.stockapp.data.model.MarketSummaryResponse
import com.thejan.stockapp.data.model.ProfileResponse

interface MarketRepository {
    suspend fun getMarketSummary(): MarketSummaryResponse
    suspend fun getStockProfile(symbol: String): ProfileResponse
}
