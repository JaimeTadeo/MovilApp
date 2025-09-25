package com.example.app.data.mapper

import com.example.app.data.api.dto.StockDto
import com.example.app.domain.model.StockModel

object StockMapper {
    fun fromDto(dto: StockDto): StockModel {
        return StockModel(
            stockName = dto.stockName ?: "N/A",
            stockPrice = dto.stockPrice ?: "0.00"
        )
    }
}
