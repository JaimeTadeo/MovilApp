package com.example.app.features.dollar.data.mapper

import com.example.app.features.dollar.data.database.entity.DollarEntity
import com.example.app.domain.model.DollarModel

fun DollarEntity.toModel() : DollarModel {
    return DollarModel(
        dollarOficial = this.dollarOfficial,
        dollarParallelo = this.dollarParallel
    )
}

fun DollarModel.toEntity() : DollarEntity {
    return DollarEntity(
        dollarOfficial = this.dollarOficial,
        dollarParallel = this.dollarParallelo
    )
}
