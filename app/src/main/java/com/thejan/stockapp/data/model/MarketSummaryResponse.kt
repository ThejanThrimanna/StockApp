package com.thejan.stockapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MarketSummaryResponse(
    val marketSummaryAndSparkResponse: MarketSummaryAndSparkResponse,
) : Parcelable
