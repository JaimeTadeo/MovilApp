package com.example.app.data.api

import com.example.app.data.api.dto.StockDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface IStockApiService {
    @GET("query")
    suspend fun getStockData(
        @Query("function") function: String = "GLOBAL_QUOTE",
        @Query("symbol") symbol: String,
        @Query("apikey") apiKey: String
    ): Response<StockDto>
}
