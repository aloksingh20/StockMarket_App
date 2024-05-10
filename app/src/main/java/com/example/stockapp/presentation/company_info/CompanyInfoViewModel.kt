package com.example.stockapp.presentation.company_info

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockapp.domain.repository.StockRepository
import com.example.stockapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyInfoViewModel@Inject constructor(
    private val repository: StockRepository,
    private val stateHandle: SavedStateHandle
):ViewModel(){

    var state by mutableStateOf(CompanyInfoState())

    init {
        viewModelScope.launch {

            val symbol = stateHandle.get<String>("symbol")?: return@launch
            state = state.copy(isLoading = true)
            val companyInfoResult = async { repository.getCompanyInfo(symbol) }
            val intradayInfoResult = async { repository.getIntradayInfo(symbol) }
            when(val result = companyInfoResult.await()){

                is Resource.Success -> {
                    state = state.copy(
                        companyInfo = result.data,
                        isLoading = false,
                        error = null
                    )
                }
                is Resource.Error -> {
                    state = state.copy(
                        isLoading = false,
                        error = result.message ?: "An unexpected error occurred",
                        companyInfo = null
                    )
                }
                else -> Unit
            }
            when(val result = intradayInfoResult.await()){
                is Resource.Success -> {
                    state = state.copy(
                        stockInfos = result.data?: emptyList(),
                        isLoading = false,
                        error = null
                    )
                }
                is Resource.Error -> {
                    state = state.copy(
                        stockInfos = emptyList(),
                        isLoading = false,
                        error = result.message?:"An unexpected error occurred"
                    )
                }
                else -> Unit
            }

        }

    }

}