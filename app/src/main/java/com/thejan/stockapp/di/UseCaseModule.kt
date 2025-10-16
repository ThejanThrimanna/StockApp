package com.thejan.stockapp.di

import com.thejan.stockapp.domain.repository.MarketRepository
import com.thejan.stockapp.domain.usecase.GetMarketSummaryUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetMarketSummaryUseCase(
        repository: MarketRepository
    ): GetMarketSummaryUseCase {
        return GetMarketSummaryUseCase(repository)
    }
}