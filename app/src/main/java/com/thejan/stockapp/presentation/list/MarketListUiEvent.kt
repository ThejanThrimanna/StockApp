package com.thejan.stockapp.presentation.list

sealed interface MarketListUiEvent {
    data class NavigateToDetail(val symbol: String) : MarketListUiEvent
    data class ShowToast(val message: String) : MarketListUiEvent
}
