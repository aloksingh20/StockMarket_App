package com.example.stockapp.di

import com.example.stockapp.data.csv.CSVParser
import com.example.stockapp.data.csv.CompanyListingParser
import com.example.stockapp.data.csv.IntradayInfoParser
import com.example.stockapp.data.repository.StockRepositoryImpl
import com.example.stockapp.domain.model.CompanyListing
import com.example.stockapp.domain.model.IntraDayInfo
import com.example.stockapp.domain.repository.StockRepository
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
    abstract fun bindCompanyListingParser(
        companyListingParser: CompanyListingParser
    ): CSVParser<CompanyListing>

    @Binds
    @Singleton
    abstract fun bindStockRepository(
        stockRepositoryImpl: StockRepositoryImpl
    ):StockRepository

    @Binds
    @Singleton
    abstract fun bindIntradayInfoParser(
        intradayInfoParser: IntradayInfoParser
    ): CSVParser<IntraDayInfo>


}