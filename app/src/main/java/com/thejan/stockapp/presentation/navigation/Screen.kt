package com.thejan.stockapp.presentation.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Screen(val route: String) {
    data object MarketList : Screen("market_list")

    data object MarketDetail : Screen("market_detail") {
        const val ARG_SYMBOL = "symbol"
        val routeWithArgs = "$route/{$ARG_SYMBOL}"

        val arguments = listOf(
            navArgument(ARG_SYMBOL) {
                type = NavType.StringType
                nullable = false
            }
        )

        fun createRoute(symbol: String): String {
            return "$route/$symbol"
        }
    }
}