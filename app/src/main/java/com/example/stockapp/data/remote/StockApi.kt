package com.example.stockapp.data.remote

import com.example.stockapp.data.remote.dto.CompanyInfoDto
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface StockApi {
//    https://www.alphavantage.co/&apikey=demo
    @GET("query?function=LISTING_STATUS&date=2014-07-10&state=delisted")
    suspend fun getListings(
        @Query("apikey") apiKey:String = API_KEY
    ):ResponseBody


    @GET("query?function=TIME_SERIES_INTRADAY&interval=60min&datatype=csv")
    suspend fun getIntradayInfo(
        @Query("symbol") symbol:String,
        @Query("apikey") apiKey:String = API_KEY
    ):ResponseBody

    @GET("query?function=OVERVIEW")
    suspend fun getCompanyInfo(
        @Query("symbol") symbol:String,
        @Query("apikey") apiKey:String = API_KEY
    ):CompanyInfoDto

    companion object{
        const val API_KEY = "CQ0HTEL0XEAX5NCT"
        const val BASE_URL = "https://alphavantage.co"
    }

}