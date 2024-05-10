package com.example.stockapp.domain.repository

import com.example.stockapp.domain.model.CompanyInfo
import com.example.stockapp.domain.model.CompanyListing
import com.example.stockapp.domain.model.IntraDayInfo
import com.example.stockapp.utils.Resource
import kotlinx.coroutines.flow.Flow

interface StockRepository {

    suspend fun getCompanyListing(
        fetFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>>

    suspend fun getIntradayInfo(symbol: String): Resource<List<IntraDayInfo>>

    suspend fun getCompanyInfo(symbol: String): Resource<CompanyInfo>
}