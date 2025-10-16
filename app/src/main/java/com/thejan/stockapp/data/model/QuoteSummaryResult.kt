package com.thejan.stockapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class QuoteSummaryResult(
    val summaryProfile: SummaryProfile?
) : Parcelable