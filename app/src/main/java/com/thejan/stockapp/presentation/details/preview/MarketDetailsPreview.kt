package com.thejan.stockapp.presentation.details.preview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.thejan.stockapp.presentation.details.InfoItem
import com.thejan.stockapp.presentation.details.MarketDetailsUiState
import com.thejan.stockapp.presentation.details.SummaryProfileSection

@Preview
@Composable
fun InfoItemPreview() {
    MaterialTheme {
        InfoItem(
            title = "Address",
            value = "One Apple Park Way, Cupertino, CA 95014, United States",
        )
    }
}

@Preview
@Composable
fun InfoItemPreview_MultipleLine() {
    MaterialTheme {
        InfoItem(
            title = "Address",
            value = "Apple Inc. designs, manufactures, and markets smartphones, personal computers, tablets, wearables, and accessories worldwide.",
            multiline = true,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SummaryProfileSectionPreview() {
    MaterialTheme {
        SummaryProfileSection(
            profile = SummaryProfileProvider().values.first(),
        )
    }
}

@Preview(showBackground = true, device = "spec:width=360dp,height=640dp,dpi=480")
@Composable
fun MarketDetailScreenPreview_Loaded() {
    MaterialTheme {
        MarketDetailScreenPreviewContent(
            uiState = MarketDetailsUiStateProvider().values.first(),
        )
    }
}

@Preview(showBackground = true, device = "spec:width=360dp,height=640dp,dpi=480")
@Composable
fun MarketDetailScreenPreview_Loading() {
    MaterialTheme {
        MarketDetailScreenPreviewContent(
            uiState = MarketDetailsUiState(
                stock = null,
                isLoading = true,
                userMessage = null,
            ),
        )
    }
}

@Preview(showBackground = true, device = "spec:width=360dp,height=640dp,dpi=480")
@Composable
fun MarketDetailScreenPreview_Error() {
    MaterialTheme {
        MarketDetailScreenPreviewContent(
            uiState = MarketDetailsUiState(
                stock = null,
                isLoading = false,
                userMessage = "Failed to load stock details",
            ),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MarketDetailScreenParameterizedPreview(
    @PreviewParameter(MarketDetailsUiStateProvider::class) uiState: MarketDetailsUiState,
) {
    MaterialTheme {
        MarketDetailScreenPreviewContent(uiState = uiState)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarketDetailScreenPreviewContent(
    uiState: MarketDetailsUiState,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Stock Details") },
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                        )
                    }
                },
                actions = {
                    val isRefreshing = uiState.isLoading && uiState.stock != null
                    if (isRefreshing) {
                        CircularProgressIndicator(
                            modifier = modifier.size(24.dp),
                            strokeWidth = 2.dp,
                        )
                    }
                },
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center,
        ) {
            when {
                uiState.isLoading && uiState.stock == null -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        CircularProgressIndicator()
                        Spacer(Modifier.height(8.dp))
                        Text("Loading stock details...")
                    }
                }

                uiState.stock != null -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(14.dp),
                    ) {
                        SummaryProfileSection(uiState.stock)
                    }
                }

                uiState.userMessage != null -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Text(
                            text = uiState.userMessage,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.error,
                        )
                    }
                }
            }
        }
    }
}
