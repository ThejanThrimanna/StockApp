package com.thejan.stockapp.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.thejan.stockapp.presentation.details.MarketDetailsViewModel
import com.thejan.stockapp.presentation.details.SummaryProfileSection
import com.thejan.stockapp.presentation.list.MarketListScreen
import com.thejan.stockapp.presentation.list.MarketListUiEvent
import com.thejan.stockapp.presentation.list.MarketListViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AppNavGraph(
    navController: NavHostController,
    startDestination: String = Screen.MarketList.route,
) {
    NavHost(
        navController = navController,
        startDestination = Screen.MarketList.route,
    ) {
        composable(route = Screen.MarketList.route, enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(400),
            )
        }, exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(400),
            )
        }) { backStackEntry ->
            val viewModel: MarketListViewModel = hiltViewModel(backStackEntry)

            LaunchedEffect(Unit) {
                viewModel.uiEvent.collectLatest { event ->
                    when (event) {
                        is MarketListUiEvent.NavigateToDetail -> {
                            navController.navigate(Screen.MarketDetail.createRoute(symbol = event.symbol))
                        }

                        else -> Unit
                    }
                }
            }

            MarketListScreen(viewModel = viewModel)
        }

        composable(
            route = Screen.MarketDetail.routeWithArgs,
            arguments = Screen.MarketDetail.arguments,
        ) { backStackEntry ->
            val symbol = backStackEntry.arguments?.getString(Screen.MarketDetail.ARG_SYMBOL)
                ?: return@composable

            val viewModel: MarketDetailsViewModel = hiltViewModel(backStackEntry)

            SummaryProfileSection(
                viewModel = viewModel,
                symbol = symbol,
                navController = navController,
            )
        }
    }
}
