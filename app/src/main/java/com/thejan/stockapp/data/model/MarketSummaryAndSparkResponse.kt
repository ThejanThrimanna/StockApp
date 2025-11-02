package com.thejan.stockapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MarketSummaryAndSparkResponse(
    val result: List<MarketSummaryItem>? = null,
    val error: String? = null,
) : Parcelable {
    fun isValid(): Boolean {
        return error == null && !result.isNullOrEmpty()
    }
}
