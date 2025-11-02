package com.thejan.stockapp.data.network

import com.thejan.stockapp.data.model.MarketSummaryResponse
import com.thejan.stockapp.data.model.ProfileResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("market/v2/get-summary")
    suspend fun getMarketSummary(): MarketSummaryResponse

    @GET("stock/v3/get-profile")
    suspend fun getProfile(@Query("symbol") symbol: String): ProfileResponse
}
