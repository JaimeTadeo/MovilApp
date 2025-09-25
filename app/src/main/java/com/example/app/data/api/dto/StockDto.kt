package com.example.app.data.api.dto

import com.google.gson.annotations.SerializedName

data class StockDto(
    @SerializedName("symbol")
    val stockName: String?,

    @SerializedName("price")
    val stockPrice: String?
)
