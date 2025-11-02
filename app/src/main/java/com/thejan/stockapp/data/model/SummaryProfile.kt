package com.thejan.stockapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SummaryProfile(
    val address1: String?,
    val address2: String?,
    val city: String?,
    val zip: String?,
    val country: String?,
    val phone: String?,
    val website: String?,
    val industry: String?,
    val industryKey: String?,
    val industryDisp: String?,
    val sector: String?,
    val sectorKey: String?,
    val sectorDisp: String?,
    val longBusinessSummary: String?,
    val description: String?,
    val fullTimeEmployees: Int?,
    val irWebsite: String?,
) : Parcelable
