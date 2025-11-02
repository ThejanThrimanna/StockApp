package com.thejan.stockapp.domain.usecase

import com.thejan.stockapp.data.model.ProfileResponse
import com.thejan.stockapp.domain.repository.MarketRepository
import javax.inject.Inject

class GetProfileSummaryUseCase @Inject constructor(
    private val repository: MarketRepository,
) {
    suspend operator fun invoke(symbol: String): Result<ProfileResponse?> {
        return try {
            val response = repository.getStockProfile(symbol)
            val quotesSummary = response.quoteSummary

            val result = quotesSummary?.result
            val error = quotesSummary?.error

            when {
                result != null && result.isNotEmpty() -> Result.success(response)
                error != null -> {
                    Result.failure(Exception(error.description ?: "API returned an error"))
                }

                else -> Result.failure(Exception("Unknown error"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
