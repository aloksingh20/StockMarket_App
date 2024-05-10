package com.example.stockapp.presentation.company_info

import com.example.stockapp.domain.model.CompanyInfo
import com.example.stockapp.domain.model.IntraDayInfo

data class CompanyInfoState(
    val stockInfos: List<IntraDayInfo> = emptyList(),
    val companyInfo: CompanyInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null

)