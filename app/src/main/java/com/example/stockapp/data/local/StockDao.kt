package com.example.stockapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StockDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompanyListings(companyListingEntity: List<CompanyListingEntity>)

    @Query(value = "DELETE FROM companyListingEntity")
    suspend fun clearCompanyListing();

    @Query("""
        SELECT * FROM companylistingentity
        WHERE LOWER(name) LIKE '%' || LOWER(:query) || '%' OR
            UPPER(:query) == symbol
    """)

    suspend fun searchCompanyListings(query: String):List<CompanyListingEntity>
}