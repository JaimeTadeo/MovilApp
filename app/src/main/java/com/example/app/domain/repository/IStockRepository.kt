package com.example.app.domain.repository

import com.example.app.domain.model.StockModel

interface IStockRepository {
    suspend fun fetchLatestStockStatus(stockSymbol: String): Result<StockModel>
}
