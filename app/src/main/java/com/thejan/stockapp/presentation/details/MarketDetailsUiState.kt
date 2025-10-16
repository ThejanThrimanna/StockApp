package com.thejan.stockapp.presentation.details

import com.thejan.stockapp.data.model.SummaryProfile

data class MarketDetailsUiState (
    val isLoading: Boolean = false,
    val stock: SummaryProfile? = null,
    val userMessage: String? = null
)