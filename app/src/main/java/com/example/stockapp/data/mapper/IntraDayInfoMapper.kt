package com.example.stockapp.data.mapper

import com.example.stockapp.data.remote.dto.CompanyInfoDto
import com.example.stockapp.data.remote.dto.IntraDayInfoDto
import com.example.stockapp.domain.model.CompanyInfo
import com.example.stockapp.domain.model.IntraDayInfo
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


fun IntraDayInfoDto.toIntraDayInfo():IntraDayInfo{
    val pattern = "yyyy-MM-dd HH:mm:ss"
    val dateTimeFormatter = DateTimeFormatter.ofPattern(pattern)
    val localDateTime = LocalDateTime.parse(timeStamp,dateTimeFormatter)
    return IntraDayInfo(
        localDateTime = localDateTime,
        close = close
    )
}

fun CompanyInfoDto.toCompanyInfo():CompanyInfo{
    return CompanyInfo(
        symbol = symbol ?: "",
        description = description ?: "",
        name = name ?: "",
        country = country ?: "",
        industry = industry ?: ""
    )
}