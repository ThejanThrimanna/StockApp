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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.thejan.stockapp.R
import com.thejan.stockapp.data.model.MarketSummaryItem
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarketListScreen(
    viewModel: MarketListViewModel,
    modifier: Modifier = Modifier,
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
                            strokeWidth = 2.dp,
                        )
                    } else {
                        IconButton(onClick = { viewModel.onEvent(MarketListEvent.Refresh) }) {
                            Icon(
                                Icons.Default.Refresh,
                                stringResource(R.string.market_list_button_refresh_description),
                            )
                        }
                    }
                },
            )
        },
        content = { innerPadding ->
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(14.dp),
            ) {
                OutlinedTextField(
                    value = uiState.searchQuery,
                    onValueChange = { newQuary ->
                        viewModel.onEvent(
                            MarketListEvent.SearchChanged(
                                newQuary,
                            ),
                        )
                    },
                    label = { Text(stringResource(R.string.market_list_search_placeholder)) },
                    modifier = modifier.fillMaxWidth(),
                )

                Spacer(modifier.height(14.dp))

                MarketListContent(
                    uiState = uiState,
                    onStockClick = { symbol ->
                        viewModel.onEvent(MarketListEvent.StockClicked(symbol = symbol))
                    },
                )
            }
        },
    )
}

@Composable
fun MarketListContent(
    uiState: MarketListUiState,
    onStockClick: (symbol: String) -> Unit,
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
                    if (uiState.searchQuery.isNotBlank()) {
                        "No matching stocks found"
                    } else {
                        stringResource(R.string.market_list_message_empty)
                    },
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
                        },
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
    modifier: Modifier = Modifier,
) {
    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(stock.shortName, style = MaterialTheme.typography.titleMedium)
            Text(stock.symbol, style = MaterialTheme.typography.bodyMedium, color = Color.Red)
            Text(stock.regularMarketPrice.fmt, Modifier.padding(top = 4.dp))
        }
    }
}

/*

// Preview provider for MarketSummaryItem
class MarketSummaryItemProvider : PreviewParameterProvider<MarketSummaryItem> {
    override val values: Sequence<MarketSummaryItem>
        get() = sequenceOf(
            MarketSummaryItem(
                symbol = "AAPL",
                shortName = "Apple Inc.",
                regularMarketPrice = RegularMarketValue(150.50, "150.50"),
                regularMarketChange = RegularMarketValue(2.50, "+2.50"),
                regularMarketChangePercent = RegularMarketValue(1.69, "+1.69%"),
                regularMarketPreviousClose = RegularMarketValue(148.00, "148.00"),
                fullExchangeName = "NasdaqGS",
                exchangeTimezoneName = "America/New_York",
                exchange = "NMS"
            ),
            MarketSummaryItem(
                symbol = "GOOGL",
                shortName = "Alphabet Inc.",
                regularMarketPrice = RegularMarketValue(2750.30, "2,750.30"),
                regularMarketChange = RegularMarketValue(45.20, "+45.20"),
                regularMarketChangePercent = RegularMarketValue(1.67, "+1.67%"),
                regularMarketPreviousClose = RegularMarketValue(2705.10, "2,705.10"),
                fullExchangeName = "NasdaqGS",
                exchangeTimezoneName = "America/New_York",
                exchange = "NMS"
            ),
            MarketSummaryItem(
                symbol = "TSLA",
                shortName = "Tesla Inc.",
                regularMarketPrice = RegularMarketValue(850.75, "850.75"),
                regularMarketChange = RegularMarketValue(-15.25, "-15.25"),
                regularMarketChangePercent = RegularMarketValue(-1.76, "-1.76%"),
                regularMarketPreviousClose = RegularMarketValue(866.00, "866.00"),
                fullExchangeName = "NasdaqGS",
                exchangeTimezoneName = "America/New_York",
                exchange = "NMS"
            )
        )
}

// ADD THE MISSING PROVIDER - Preview provider for MarketListUiState
class MarketListUiStateProvider : PreviewParameterProvider<MarketListUiState> {
    override val values: Sequence<MarketListUiState>
        get() = sequenceOf(
            MarketListUiState(
                stocks = listOf(
                    MarketSummaryItem(
                        symbol = "AAPL",
                        shortName = "Apple Inc.",
                        regularMarketPrice = RegularMarketValue(150.50, "150.50"),
                        regularMarketChange = RegularMarketValue(2.50, "+2.50"),
                        regularMarketChangePercent = RegularMarketValue(1.69, "+1.69%"),
                        regularMarketPreviousClose = RegularMarketValue(148.00, "148.00"),
                        fullExchangeName = "NasdaqGS",
                        exchangeTimezoneName = "America/New_York",
                        exchange = "NMS"
                    ),
                    MarketSummaryItem(
                        symbol = "GOOGL",
                        shortName = "Alphabet Inc.",
                        regularMarketPrice = RegularMarketValue(2750.30, "2,750.30"),
                        regularMarketChange = RegularMarketValue(45.20, "+45.20"),
                        regularMarketChangePercent = RegularMarketValue(1.67, "+1.67%"),
                        regularMarketPreviousClose = RegularMarketValue(2705.10, "2,705.10"),
                        fullExchangeName = "NasdaqGS",
                        exchangeTimezoneName = "America/New_York",
                        exchange = "NMS"
                    )
                ),
                isLoading = false,
                searchQuery = ""
            ),
            MarketListUiState(
                stocks = emptyList(),
                isLoading = true,
                searchQuery = ""
            ),
            MarketListUiState(
                stocks = emptyList(),
                isLoading = false,
                searchQuery = "test"
            ),
            MarketListUiState(
                stocks = listOf(
                    MarketSummaryItem(
                        symbol = "AAPL",
                        shortName = "Apple Inc.",
                        regularMarketPrice = RegularMarketValue(150.50, "150.50"),
                        regularMarketChange = RegularMarketValue(2.50, "+2.50"),
                        regularMarketChangePercent = RegularMarketValue(1.69, "+1.69%"),
                        regularMarketPreviousClose = RegularMarketValue(148.00, "148.00"),
                        fullExchangeName = "NasdaqGS",
                        exchangeTimezoneName = "America/New_York",
                        exchange = "NMS"
                    )
                ),
                isLoading = true,
                searchQuery = ""
            )
        )
}

// Individual MarketListItem previews
@Preview(showBackground = true)
@Composable
fun MarketListItemPreview() {
    MaterialTheme {
        MarketListItem(
            stock = MarketSummaryItem(
                symbol = "AAPL",
                shortName = "Apple Inc.",
                regularMarketPrice = RegularMarketValue(150.50, "150.50"),
                regularMarketChange = RegularMarketValue(2.50, "+2.50"),
                regularMarketChangePercent = RegularMarketValue(1.69, "+1.69%"),
                regularMarketPreviousClose = RegularMarketValue(148.00, "148.00"),
                fullExchangeName = "NasdaqGS",
                exchangeTimezoneName = "America/New_York",
                exchange = "NMS"
            ),
            onClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MarketListItemParameterizedPreview(
    @PreviewParameter(MarketSummaryItemProvider::class) stock: MarketSummaryItem
) {
    MaterialTheme {
        MarketListItem(
            stock = stock,
            onClick = {}
        )
    }
}

// MarketListContent previews using MarketListUiStateProvider
@Preview(showBackground = true)
@Composable
fun MarketListContentParameterizedPreview(
    @PreviewParameter(MarketListUiStateProvider::class) uiState: MarketListUiState
) {
    MaterialTheme {
        MarketListContent(
            uiState = uiState,
            onStockClick = {}
        )
    }
}

// Specific scenario previews
@Preview(showBackground = true)
@Composable
fun MarketListContentPreview_Loading() {
    MaterialTheme {
        MarketListContent(
            uiState = MarketListUiState(
                stocks = emptyList(),
                isLoading = true,
                searchQuery = ""
            ),
            onStockClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MarketListContentPreview_Empty() {
    MaterialTheme {
        MarketListContent(
            uiState = MarketListUiState(
                stocks = emptyList(),
                isLoading = false,
                searchQuery = ""
            ),
            onStockClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MarketListContentPreview_WithStocks() {
    MaterialTheme {
        MarketListContent(
            uiState = MarketListUiState(
                stocks = listOf(
                    MarketSummaryItem(
                        symbol = "AAPL",
                        shortName = "Apple Inc.",
                        regularMarketPrice = RegularMarketValue(150.50, "150.50"),
                        regularMarketChange = RegularMarketValue(2.50, "+2.50"),
                        regularMarketChangePercent = RegularMarketValue(1.69, "+1.69%"),
                        regularMarketPreviousClose = RegularMarketValue(148.00, "148.00"),
                        fullExchangeName = "NasdaqGS",
                        exchangeTimezoneName = "America/New_York",
                        exchange = "NMS"
                    ),
                    MarketSummaryItem(
                        symbol = "GOOGL",
                        shortName = "Alphabet Inc.",
                        regularMarketPrice = RegularMarketValue(2750.30, "2,750.30"),
                        regularMarketChange = RegularMarketValue(45.20, "+45.20"),
                        regularMarketChangePercent = RegularMarketValue(1.67, "+1.67%"),
                        regularMarketPreviousClose = RegularMarketValue(2705.10, "2,705.10"),
                        fullExchangeName = "NasdaqGS",
                        exchangeTimezoneName = "America/New_York",
                        exchange = "NMS"
                    )
                ),
                isLoading = false,
                searchQuery = ""
            ),
            onStockClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MarketListContentPreview_SearchResults() {
    MaterialTheme {
        MarketListContent(
            uiState = MarketListUiState(
                stocks = listOf(
                    MarketSummaryItem(
                        symbol = "AAPL",
                        shortName = "Apple Inc.",
                        regularMarketPrice = RegularMarketValue(150.50, "150.50"),
                        regularMarketChange = RegularMarketValue(2.50, "+2.50"),
                        regularMarketChangePercent = RegularMarketValue(1.69, "+1.69%"),
                        regularMarketPreviousClose = RegularMarketValue(148.00, "148.00"),
                        fullExchangeName = "NasdaqGS",
                        exchangeTimezoneName = "America/New_York",
                        exchange = "NMS"
                    )
                ),
                isLoading = false,
                searchQuery = "app"
            ),
            onStockClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MarketListContentPreview_Refreshing() {
    MaterialTheme {
        MarketListContent(
            uiState = MarketListUiState(
                stocks = listOf(
                    MarketSummaryItem(
                        symbol = "AAPL",
                        shortName = "Apple Inc.",
                        regularMarketPrice = RegularMarketValue(150.50, "150.50"),
                        regularMarketChange = RegularMarketValue(2.50, "+2.50"),
                        regularMarketChangePercent = RegularMarketValue(1.69, "+1.69%"),
                        regularMarketPreviousClose = RegularMarketValue(148.00, "148.00"),
                        fullExchangeName = "NasdaqGS",
                        exchangeTimezoneName = "America/New_York",
                        exchange = "NMS"
                    )
                ),
                isLoading = true,
                searchQuery = ""
            ),
            onStockClick = {}
        )
    }
}*/
