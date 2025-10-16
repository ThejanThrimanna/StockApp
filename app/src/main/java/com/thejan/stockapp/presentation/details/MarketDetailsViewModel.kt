package com.thejan.stockapp.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thejan.stockapp.domain.usecase.GetProfileSummaryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MarketDetailsViewModel @Inject constructor(
    private val getProfileSummaryUseCase: GetProfileSummaryUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(MarketDetailsUiState())
    val uiState: StateFlow<MarketDetailsUiState> = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<MarketDetailsUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun onEvent(event: MarketDetailsEvent) {
        when (event) {
            is MarketDetailsEvent.LoadStockDetails -> loadStockDetails(event.symbol)
        }
    }

    fun loadStockDetails(symbol: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            try {
                val result = getProfileSummaryUseCase.invoke(symbol)

                result.onSuccess { response ->
                    val profile = response?.quoteSummary?.result?.firstOrNull()?.summaryProfile

                    if (profile != null) {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            stock = profile,
                            userMessage = null
                        )
                    } else {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            stock = null,
                            userMessage = "No profile data found"
                        )
                        _uiEvent.emit(MarketDetailsUiEvent.ShowToast("No profile data found"))
                    }
                }.onFailure { e ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        userMessage = e.localizedMessage ?: "Failed to load details"
                    )
                    _uiEvent.emit(
                        MarketDetailsUiEvent.ShowToast("Error: ${e.localizedMessage ?: "Unknown error"}")
                    )
                }
            } catch (e: Exception) {
                _uiState.value = uiState.value.copy(
                    isLoading = false,
                    userMessage = e.localizedMessage ?: "Failed to load details"
                )
                _uiEvent.emit(MarketDetailsUiEvent.ShowToast("Error: ${e.localizedMessage}"))
            }
        }
    }
}
