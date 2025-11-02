package com.thejan.stockapp.data.repository

import com.thejan.stockapp.data.model.MarketSummaryResponse
import com.thejan.stockapp.data.model.ProfileResponse
import com.thejan.stockapp.data.network.ApiService
import com.thejan.stockapp.domain.repository.MarketRepository
import javax.inject.Inject

class MarketRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
) : MarketRepository {

    override suspend fun getMarketSummary(): MarketSummaryResponse {
        return apiService.getMarketSummary()
    }

    override suspend fun getStockProfile(symbol: String): ProfileResponse {
        return apiService.getProfile(symbol)
    }
}
