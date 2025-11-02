package com.thejan.stockapp.presentation.details.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.thejan.stockapp.data.model.SummaryProfile
import com.thejan.stockapp.presentation.details.MarketDetailsUiState

class SummaryProfileProvider : PreviewParameterProvider<SummaryProfile> {
    override val values: Sequence<SummaryProfile>
        get() = sequenceOf(
            SummaryProfile(
                address1 = "One Apple Park Way",
                address2 = null,
                city = "Cupertino",
                zip = "95014",
                country = "United States",
                phone = "1-408-996-1010",
                website = "https://www.apple.com",
                industry = "Consumer Electronics",
                industryKey = "electronics",
                industryDisp = "Consumer Electronics",
                sector = "Technology",
                sectorKey = "technology",
                sectorDisp = "Technology",
                longBusinessSummary = "Apple Inc. designs, manufactures, and markets smartphones, personal computers, tablets, wearables, and accessories worldwide.",
                description = "Apple Inc. is a multinational technology company.",
                fullTimeEmployees = 164000,
                irWebsite = "https://investor.apple.com",
            ),
            SummaryProfile(
                address1 = "1600 Amphitheatre Parkway",
                address2 = null,
                city = "Mountain View",
                zip = "94043",
                country = "United States",
                phone = "1-650-253-0000",
                website = "https://www.abc.xyz",
                industry = "Internet Content & Information",
                industryKey = "internet",
                industryDisp = "Internet Content & Information",
                sector = "Communication Services",
                sectorKey = "communication",
                sectorDisp = "Communication Services",
                longBusinessSummary = "Alphabet Inc. provides online advertising services, cloud services, software and hardware products, and other services worldwide.",
                description = "Alphabet Inc. is a multinational technology conglomerate.",
                fullTimeEmployees = 156500,
                irWebsite = "https://abc.xyz/investor",
            ),
        )
}

class MarketDetailsUiStateProvider : PreviewParameterProvider<MarketDetailsUiState> {
    override val values: Sequence<MarketDetailsUiState>
        get() = sequenceOf(
            MarketDetailsUiState(
                stock = SummaryProfileProvider().values.first(),
                isLoading = false,
                userMessage = null,
            ),
            MarketDetailsUiState(
                stock = null,
                isLoading = true,
                userMessage = null,
            ),
            MarketDetailsUiState(
                stock = null,
                isLoading = false,
                userMessage = "Failed to load stock details",
            ),
        )
}
