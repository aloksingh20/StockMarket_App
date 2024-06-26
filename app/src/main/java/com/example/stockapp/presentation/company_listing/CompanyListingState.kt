package com.example.stockapp.presentation.company_listing

import com.example.stockapp.domain.model.CompanyListing

data class CompanyListingState(
    val companies : List<CompanyListing> = emptyList(),
    val isLoading :Boolean = false,
    val isRefreshing : Boolean = false,
    val searchQuery :String = ""
)
