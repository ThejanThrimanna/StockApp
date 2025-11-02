package com.thejan.stockapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class QuotesSummary(
    val result: List<QuoteSummaryResult>,
    val error: QuoteSummaryError?,
) : Parcelable

@Parcelize
data class QuoteSummaryError(
    val code: String? = null,
    val description: String? = null,
) : Parcelable
