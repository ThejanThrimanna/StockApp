package com.thejan.stockapp.presentation.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.thejan.stockapp.data.model.SummaryProfile

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarketDetailScreen(
    viewModel: MarketDetailsViewModel,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    symbol: String,
) {
    val uiState = viewModel.uiState.collectAsState().value

    LaunchedEffect(symbol) {
        viewModel.onEvent(MarketDetailsEvent.LoadStockDetails(symbol))
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Profile") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    val isRefreshing = uiState.isLoading && uiState.stock != null
                    if (isRefreshing) {
                        CircularProgressIndicator(
                            modifier = modifier.size(24.dp),
                            strokeWidth = 2.dp
                        )
                    }
                }
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            when {
                uiState.isLoading && uiState.stock == null -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator()
                        Spacer(Modifier.height(8.dp))
                        Text("Loading profile details...")
                    }
                }

                uiState.stock != null -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(14.dp)
                    ) {
                        SummaryProfileSection(uiState.stock)
                    }
                }

                uiState.userMessage != null -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = uiState.userMessage,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SummaryProfileSection(profile: SummaryProfile) {
    Column(modifier = Modifier.fillMaxWidth()) {
        InfoItem(
            "Address", listOfNotNull(
                profile.address1,
                profile.address2,
                profile.city,
                profile.country
            ).joinToString(", ")
        )

        InfoItem("Phone", profile.phone)
        InfoItem("Website", profile.website)
        InfoItem("Sector", profile.sectorDisp)
        InfoItem("Industry", profile.industryDisp)
        InfoItem("Employees", profile.fullTimeEmployees?.toString())
        InfoItem(
            "Description",
            profile.longBusinessSummary ?: profile.description,
            multiline = true
        )
    }
}

@Composable
fun InfoItem(title: String, value: String?, multiline: Boolean = false) {
    if (value.isNullOrBlank()) return

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = if (multiline) Int.MAX_VALUE else 2,
            overflow = if (multiline) TextOverflow.Clip else TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(8.dp))
        HorizontalDivider()
    }
}