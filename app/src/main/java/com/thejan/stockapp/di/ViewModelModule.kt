package com.thejan.stockapp.di

import com.thejan.stockapp.domain.usecase.GetMarketSummaryUseCase
import com.thejan.stockapp.domain.usecase.GetProfileSummaryUseCase
import com.thejan.stockapp.presentation.details.MarketDetailsViewModel
import com.thejan.stockapp.presentation.list.MarketListViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @Provides
    fun provideMarketListViewModel(
        getMarketSummaryUseCase: GetMarketSummaryUseCase
    ): MarketListViewModel {
        return MarketListViewModel(getMarketSummaryUseCase)
    }

    @Provides
    fun provideMarketDetailsViewModel(
        getProfileSummaryUseCase: GetProfileSummaryUseCase
    ): MarketDetailsViewModel {
        return MarketDetailsViewModel(getProfileSummaryUseCase)
    }
}