package com.thejan.stockapp.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thejan.stockapp.data.model.MarketSummaryItem
import com.thejan.stockapp.domain.usecase.GetMarketSummaryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MarketListViewModel @Inject constructor(
    private val getMarketSummaryUseCase: GetMarketSummaryUseCase,
    private val enableAutoRefresh: Boolean = true
) : ViewModel() {

    private val _uiState = MutableStateFlow(MarketListUiState(isLoading = true))
    val uiState: StateFlow<MarketListUiState> = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<MarketListUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    private var periodicFetchJob: Job? = null

    init {
        fetchMarketSummary()
        if (enableAutoRefresh) {
            startPeriodicFetch()
        }
    }

    fun onEvent(event: MarketListEvent) {
        when (event) {
            is MarketListEvent.Refresh -> fetchMarketSummary()
            is MarketListEvent.SearchChanged -> onSearchQueryChange(event.query)
            is MarketListEvent.UserMessageShown -> {
                _uiState.update { it.copy(userMessage = null) }
            }

            is MarketListEvent.StockClicked -> {
                viewModelScope.launch {
                    _uiEvent.emit(MarketListUiEvent.NavigateToDetail(symbol = event.symbol))
                }
            }
        }
    }

    private fun startPeriodicFetch() {
        periodicFetchJob = viewModelScope.launch {
            while (true) {
                delay(8000)
                fetchMarketSummary()
            }
        }
    }

    private fun fetchMarketSummary() {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true) }
                val response = getMarketSummaryUseCase()
                if (response.marketSummaryAndSparkResponse.isValid()) {
                    val newStocks = response.marketSummaryAndSparkResponse.result ?: emptyList()
                    _uiState.update { currentState ->
                        currentState.copy(
                            allStocks = newStocks,
                            stocks = filterStocks(newStocks, currentState.searchQuery),
                        )
                    }
                } else {
                    val errorMessage = response.marketSummaryAndSparkResponse.error
                        ?: "An unknown error occurred"
                    _uiState.update { it.copy(userMessage = errorMessage) }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(userMessage = "Error: ${e.localizedMessage ?: "Could not connect"}") }
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    private fun onSearchQueryChange(query: String) {
        _uiState.update { currentState ->
            val filteredList = filterStocks(currentState.allStocks, query)
            currentState.copy(
                searchQuery = query,
                stocks = filteredList
            )
        }
    }

    private fun filterStocks(
        stocks: List<MarketSummaryItem>,
        query: String
    ): List<MarketSummaryItem> {
        return if (query.isBlank()) {
            stocks
        } else {
            stocks.filter { stock ->
                stock.shortName.contains(query, ignoreCase = true)
                        || stock.symbol.contains(
                    query,
                    ignoreCase = true
                )
            }
        }
    }

    public override fun onCleared() {
        periodicFetchJob?.cancel()
        super.onCleared()
    }
}