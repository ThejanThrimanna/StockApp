package com.thejan.stockapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RegularMarketValue(
    val raw: Double,
    val fmt: String
) : Parcelable