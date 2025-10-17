package com.thejan.stockapp.presentation.list

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.thejan.stockapp.data.model.MarketSummaryItem
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber
import com.thejan.stockapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarketListScreen(
    viewModel: MarketListViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collectLatest { event ->
            when (event) {
                is MarketListUiEvent.NavigateToDetail -> {
                    Timber.tag("MarketListScreen").d("Navigate to detail: ${event.symbol}")
                }

                is MarketListUiEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(id = R.string.market_list_title)) },
                actions = {
                    val isRefreshing = uiState.isLoading && uiState.stocks.isNotEmpty()
                    if (isRefreshing) {
                        CircularProgressIndicator(
                            modifier = modifier.size(24.dp),
                            strokeWidth = 2.dp
                        )
                    } else {
                        IconButton(onClick = { viewModel.onEvent(MarketListEvent.Refresh) }) {
                            Icon(
                                Icons.Default.Refresh,
                                stringResource(R.string.market_list_button_refresh_description)
                            )
                        }
                    }
                }
            )
        },
        content = { innerPadding ->
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(14.dp)
            ) {
                OutlinedTextField(
                    value = uiState.searchQuery,
                    onValueChange = { newQuary ->
                        viewModel.onEvent(
                            MarketListEvent.SearchChanged(
                                newQuary
                            )
                        )
                    },
                    label = { Text(stringResource(R.string.market_list_search_placeholder)) },
                    modifier = modifier.fillMaxWidth()
                )

                Spacer(modifier.height(14.dp))

                MarketListContent(
                    uiState = uiState,
                    onStockClick = { symbol ->
                        viewModel.onEvent(MarketListEvent.StockClicked(symbol = symbol))
                    }
                )
            }
        }
    )
}

@Composable
private fun MarketListContent(
    uiState: MarketListUiState,
    onStockClick: (symbol: String) -> Unit
) {
    when {
        uiState.isLoading && uiState.stocks.isEmpty() -> {
            Box(Modifier.fillMaxSize(), Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator()
                    Spacer(Modifier.height(8.dp))
                    Text(stringResource(R.string.market_list_message_loading))
                }
            }
        }

        uiState.stocks.isEmpty() -> {
            Box(Modifier.fillMaxSize(), Alignment.Center) {
                Text(
                    if (uiState.searchQuery.isNotBlank()) "No matching stocks found"
                    else stringResource(R.string.market_list_message_empty)
                )
            }
        }

        else -> {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(uiState.stocks, key = { it.symbol }) { stock ->
                    MarketListItem(
                        stock = stock,
                        onClick = {
                            onStockClick(stock.symbol)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun MarketListItem(
    stock: MarketSummaryItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(stock.shortName, style = MaterialTheme.typography.titleMedium)
            Text(stock.symbol, style = MaterialTheme.typography.bodyMedium)
            Text(stock.regularMarketPrice.fmt, Modifier.padding(top = 4.dp))
        }
    }
}