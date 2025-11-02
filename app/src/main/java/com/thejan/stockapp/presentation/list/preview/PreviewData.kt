package com.thejan.stockapp.presentation.list.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.thejan.stockapp.data.model.MarketSummaryItem
import com.thejan.stockapp.data.model.RegularMarketValue
import com.thejan.stockapp.presentation.list.MarketListUiState

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
                exchange = "NMS",
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
                exchange = "NMS",
            ),
        )
}

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
                        exchange = "NMS",
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
                        exchange = "NMS",
                    ),
                ),
                isLoading = false,
                searchQuery = "",
            ),
            MarketListUiState(
                stocks = emptyList(),
                isLoading = true,
                searchQuery = "",
            ),
            MarketListUiState(
                stocks = emptyList(),
                isLoading = false,
                searchQuery = "test",
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
                        exchange = "NMS",
                    ),
                ),
                isLoading = true,
                searchQuery = "",
            ),
        )
}
