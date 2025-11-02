package com.thejan.stockapp.presentation.list

sealed interface MarketListEvent {
    data object Refresh : MarketListEvent
    data class SearchChanged(val query: String) : MarketListEvent
    data class StockClicked(val symbol: String) : MarketListEvent
    data object UserMessageShown : MarketListEvent
}
