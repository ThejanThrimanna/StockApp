package com.thejan.stockapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MarketSummaryItem(
    val symbol: String,
    val shortName: String,
    val regularMarketPrice: RegularMarketValue,
    val regularMarketChange: RegularMarketValue?,
    val regularMarketChangePercent: RegularMarketValue?,
    val regularMarketPreviousClose: RegularMarketValue?,
    val fullExchangeName: String,
    val exchangeTimezoneName: String,
    val exchange: String
) : Parcelable