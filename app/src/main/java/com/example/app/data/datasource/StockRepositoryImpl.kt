package com.example.app.data.datasource

import com.example.app.data.api.IStockApiService
import com.example.app.data.mapper.StockMapper
import com.example.app.domain.model.StockModel
import com.example.app.domain.repository.IStockRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class StockRepositoryImpl(
    private val stockApiService: IStockApiService
) : IStockRepository {

    override suspend fun fetchLatestStockStatus(stockSymbol: String): Result<StockModel> {
        return withContext(Dispatchers.IO) {
            try {
                // TODO: Obtener la API key desde las configuraciones
                val apiKey = "YOUR_API_KEY_HERE"
                val response = stockApiService.getStockData(symbol = stockSymbol, apiKey = apiKey)

                if (response.isSuccessful && response.body() != null) {
                    val stockDto = response.body()!!
                    val stockModel = StockMapper.fromDto(stockDto)
                    Result.success(stockModel)
                } else {
                    Result.failure(Exception("Error al obtener datos del stock: ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}
