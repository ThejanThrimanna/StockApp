package com.thejan.stockapp.di

import com.thejan.stockapp.data.repository.MarketRepositoryImpl
import com.thejan.stockapp.domain.repository.MarketRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindStockRepository(
        impl: MarketRepositoryImpl,
    ): MarketRepository
}
