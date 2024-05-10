package com.example.stockapp.domain.model

import java.time.LocalDateTime

data class IntraDayInfo (
    val localDateTime: LocalDateTime,
    val close: Double
)