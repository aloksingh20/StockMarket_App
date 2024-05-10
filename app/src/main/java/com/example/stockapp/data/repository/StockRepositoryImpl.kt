package com.example.stockapp.data.repository

import com.example.stockapp.data.csv.CSVParser
import com.example.stockapp.data.local.StockDatabase
import com.example.stockapp.data.mapper.toCompanyInfo
import com.example.stockapp.data.mapper.toCompanyListing
import com.example.stockapp.data.mapper.toCompanyListingEntity
import com.example.stockapp.data.remote.StockApi
import com.example.stockapp.domain.model.CompanyInfo
import com.example.stockapp.domain.model.CompanyListing
import com.example.stockapp.domain.model.IntraDayInfo
import com.example.stockapp.domain.repository.StockRepository
import com.example.stockapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImpl @Inject constructor(
    private val stockDb: StockDatabase,
    private val stockApi: StockApi,
    private val companyListingParser: CSVParser<CompanyListing>,
    private val intraDayInfoParser: CSVParser<IntraDayInfo>
):StockRepository {

    private val dao = stockDb.dao

    override suspend fun getCompanyListing(
        fetFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>> {
        return flow {
            emit(Resource.Loading(true))

            val localListings = dao.searchCompanyListings(query)
            emit(Resource.Success(localListings.map { it.toCompanyListing() }))

            val isDbEmpty = localListings.isEmpty() && query.isBlank()
            val shouldJustLoadFromCache = !isDbEmpty && !fetFromRemote
            if(shouldJustLoadFromCache){
                emit(Resource.Loading(isLoading = true))
                return@flow
            }
            val remoteListings = try{
                val response = stockApi.getListings()
                println(response.byteStream().readBytes().decodeToString())
                companyListingParser.parse(response.byteStream())
            }
            catch (e:IOException){
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            }
            catch (e:HttpException){
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            }
            println(remoteListings.toString())
            remoteListings?.let { listings->

                dao.clearCompanyListing()
                dao.insertCompanyListings(
                    listings.map { it.toCompanyListingEntity() }
                )
                emit(Resource.Success(
                    data = dao
                        .searchCompanyListings("")
                        .map { it.toCompanyListing() }
                ))

                emit(Resource.Loading(false))
            }

        }
    }

    override suspend fun getIntradayInfo(symbol: String): Resource<List<IntraDayInfo>> {

            return try {
                val response = stockApi.getIntradayInfo(symbol)
                Resource.Success(intraDayInfoParser.parse(response.byteStream()))
            }catch (e:HttpException){
                e.printStackTrace()
                Resource.Error("Couldn't load data")

            }catch (e:IOException){
                e.printStackTrace()
                Resource.Error("Couldn't load data")

            }
    }

    override suspend fun getCompanyInfo(symbol: String): Resource<CompanyInfo> {
        return try{
            val response = stockApi.getCompanyInfo(symbol)
            Resource.Success(response.toCompanyInfo())
        }
        catch (e:IOException){
            e.printStackTrace()
            Resource.Error("Couldn't load data")

        }
        catch (e:HttpException){
            e.printStackTrace()
            Resource.Error("Couldn't load data")
        }
    }

}