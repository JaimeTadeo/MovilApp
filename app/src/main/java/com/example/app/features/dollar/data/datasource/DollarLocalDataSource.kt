package com.example.app.features.dollar.data.datasource

import com.example.app.features.dollar.data.database.dao.IDollarDao
import com.example.app.features.dollar.data.mapper.toEntity
import com.example.app.features.dollar.data.mapper.toModel
import com.example.app.domain.model.DollarModel

class DollarLocalDataSource(
    val dao: IDollarDao
) {
    suspend fun getList(): List<DollarModel> {
        return dao.getList().map { it.toModel() }
    }
    suspend fun deleteAll() {
        dao.deleteAll()
    }
    suspend fun inserTDollars(list: List<DollarModel>) {
        val dollarEntity = list.map { it.toEntity() }
        dao.insertDollars(dollarEntity)
    }
    suspend fun insert(dollar: DollarModel) {
        dao.insert(dollar.toEntity())
    }
}

