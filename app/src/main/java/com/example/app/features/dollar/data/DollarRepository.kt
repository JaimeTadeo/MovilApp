package com.example.app.features.dollar.data

import com.example.app.features.dollar.datasource.RealTimeRemoteDataSource
import com.example.app.features.dollar.data.datasource.DollarLocalDataSource
import com.example.app.domain.model.DollarModel
import com.example.app.domain.repository.IDollarRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.map
import com.example.app.features.dollar.data.mapper.toEntity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DollarRepository(
    private val realTimeRemoteDataSource: RealTimeRemoteDataSource,
    private val localDataSource: DollarLocalDataSource
) : IDollarRepository {
    override suspend fun getDollar(): Flow<DollarModel> {
        return realTimeRemoteDataSource.getDollarUpdates()
            .map { model ->
                val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                val now = dateFormat.format(Date())
                model.copy(
                    usdt = "6.91",
                    usdc = "6.92",
                    fechaActualizacion = now
                )
            }
            .onEach {
                localDataSource.insert(it)
            }
    }
}
