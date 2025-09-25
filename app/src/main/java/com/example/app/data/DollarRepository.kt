package com.example.app.data

import com.example.app.domain.model.DollarModel
import com.example.app.domain.repository.IDollarRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DollarRepository : IDollarRepository {
    override suspend fun getDollar(): Flow<DollarModel> {
        return flow {
            emit(DollarModel("6.96", "12.6"))
        }
    }
}

