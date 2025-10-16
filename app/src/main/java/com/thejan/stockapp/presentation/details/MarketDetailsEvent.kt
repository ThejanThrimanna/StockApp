package com.thejan.stockapp.presentation.details

sealed interface MarketDetailsEvent {
    data class LoadStockDetails(val symbol: String) : MarketDetailsEvent
}