package com.example.app.features.dollar.data

import com.example.app.features.dollar.datasource.RealTimeRemoteDataSource
import com.example.app.features.dollar.data.datasource.DollarLocalDataSource
import com.example.app.domain.model.DollarModel
import com.example.app.domain.repository.IDollarRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import com.example.app.features.dollar.data.mapper.toEntity

class DollarRepository(
    private val realTimeRemoteDataSource: RealTimeRemoteDataSource,
    private val localDataSource: DollarLocalDataSource
) : IDollarRepository {
    override suspend fun getDollar(): Flow<DollarModel> {
        return realTimeRemoteDataSource.getDollarUpdates()
            .onEach {
                localDataSource.insert(it)
            }
    }
}
