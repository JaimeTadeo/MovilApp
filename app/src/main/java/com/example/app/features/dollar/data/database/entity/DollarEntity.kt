package com.example.app.features.dollar.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dollars")
data class DollarEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "dollar_official")
    var dollarOfficial: String? = null,

    @ColumnInfo(name = "dollar_parallel")
    var dollarParallel: String? = null,

    @ColumnInfo(name = "usdt")
    var usdt: String? = null,

    @ColumnInfo(name = "usdc")
    var usdc: String? = null,

    @ColumnInfo(name = "fecha_actualizacion")
    var fechaActualizacion: String? = null,

    @ColumnInfo(name = "timestamp")
    var timestamp: Long = 0
)
