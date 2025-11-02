package com.thejan.stockapp.presentation.details

sealed interface MarketDetailsUiEvent {
    data class ShowToast(val message: String) : MarketDetailsUiEvent
}
