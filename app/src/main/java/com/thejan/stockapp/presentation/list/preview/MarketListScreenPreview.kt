package com.thejan.stockapp.presentation.list.preview

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.thejan.stockapp.data.model.MarketSummaryItem
import com.thejan.stockapp.data.model.RegularMarketValue
import com.thejan.stockapp.presentation.list.MarketListContent
import com.thejan.stockapp.presentation.list.MarketListItem
import com.thejan.stockapp.presentation.list.MarketListUiState


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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarketListScreenPreviewContent(
    uiState: MarketListUiState,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Market List") },
                actions = {
                    val isRefreshing = uiState.isLoading && uiState.stocks.isNotEmpty()
                    if (isRefreshing) {
                        CircularProgressIndicator(
                            modifier = modifier.size(24.dp),
                            strokeWidth = 2.dp
                        )
                    } else {
                        IconButton(onClick = { }) {
                            Icon(
                                Icons.Default.Refresh,
                                "Refresh"
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
                    onValueChange = { },
                    label = { Text("Search stocks...") },
                    modifier = modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(14.dp))

                MarketListContent(
                    uiState = uiState,
                    onStockClick = { }
                )
            }
        }
    )
}

@Preview(showBackground = true, device = "spec:width=360dp,height=640dp,dpi=480")
@Composable
fun MarketListScreenPreview_Loading() {
    MaterialTheme {
        MarketListScreenPreviewContent(
            uiState = MarketListUiState(
                stocks = emptyList(),
                isLoading = true,
                searchQuery = ""
            )
        )
    }
}

@Preview(showBackground = true, device = "spec:width=360dp,height=640dp,dpi=480")
@Composable
fun MarketListScreenPreview_Empty() {
    MaterialTheme {
        MarketListScreenPreviewContent(
            uiState = MarketListUiState(
                stocks = emptyList(),
                isLoading = false,
                searchQuery = ""
            )
        )
    }
}

