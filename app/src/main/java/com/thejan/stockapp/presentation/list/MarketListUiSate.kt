package com.thejan.stockapp.presentation.list

import com.thejan.stockapp.data.model.MarketSummaryItem

data class MarketListUiState(
    val stocks: List<MarketSummaryItem> = emptyList(),
    val allStocks: List<MarketSummaryItem> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = true,
    val userMessage: String? = null
)