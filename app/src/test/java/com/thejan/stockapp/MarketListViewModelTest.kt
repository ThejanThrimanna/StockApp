package com.thejan.stockapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.thejan.stockapp.data.model.MarketSummaryAndSparkResponse
import com.thejan.stockapp.data.model.MarketSummaryItem
import com.thejan.stockapp.data.model.MarketSummaryResponse
import com.thejan.stockapp.data.model.RegularMarketValue
import com.thejan.stockapp.domain.usecase.GetMarketSummaryUseCase
import com.thejan.stockapp.presentation.list.MarketListEvent
import com.thejan.stockapp.presentation.list.MarketListViewModel
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MarketListViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private val getMarketSummaryUseCase: GetMarketSummaryUseCase = mockk()
    private lateinit var viewModel: MarketListViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        coEvery { getMarketSummaryUseCase() } returns MarketSummaryResponse(
            marketSummaryAndSparkResponse = MarketSummaryAndSparkResponse(
                result = listOf(createMockMarketItem()),
            ),
        )
        viewModel = MarketListViewModel(getMarketSummaryUseCase, enableAutoRefresh = false)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `refresh event updates state with new stocks`() = runTest {
        viewModel.onEvent(MarketListEvent.Refresh)
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertEquals(1, state.allStocks.size)
        assertEquals("AAPL", state.allStocks.first().symbol)
        assertEquals("Apple Inc.", state.allStocks.first().shortName)
    }

    @Test
    fun `search event filters stocks correctly`() = runTest {
        viewModel.onEvent(MarketListEvent.Refresh)
        advanceUntilIdle()

        viewModel.onEvent(MarketListEvent.SearchChanged("App"))
        val filtered = viewModel.uiState.value.stocks
        assertEquals(1, filtered.size)
        assertEquals("AAPL", filtered.first().symbol)

        viewModel.onEvent(MarketListEvent.SearchChanged("Tesla"))
        val empty = viewModel.uiState.value.stocks
        assertTrue(empty.isEmpty())
    }

    @Test
    fun `handles API error gracefully`() = runTest {
        coEvery { getMarketSummaryUseCase() } throws Exception("Network Error")

        viewModel.onEvent(MarketListEvent.Refresh)
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertTrue(state.userMessage!!.contains("Error"))
    }

    private fun createMockMarketItem(): MarketSummaryItem {
        return MarketSummaryItem(
            symbol = "AAPL",
            shortName = "Apple Inc.",
            regularMarketPrice = RegularMarketValue(150.0, "$150.00"),
            regularMarketChange = RegularMarketValue(2.0, "+2.00"),
            regularMarketChangePercent = RegularMarketValue(1.35, "+1.35%"),
            regularMarketPreviousClose = RegularMarketValue(148.0, "$148.00"),
            fullExchangeName = "NASDAQ",
            exchangeTimezoneName = "America/New_York",
            exchange = "NASQ",
        )
    }
}
