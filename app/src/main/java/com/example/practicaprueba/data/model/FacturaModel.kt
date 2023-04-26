package com.example.practicaprueba.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.practicaprueba.data.network.domain.model.Factura

@Entity(tableName = "table_facturas")
data class FacturaModel (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "Estado") var descEstado : String,
    @ColumnInfo(name = "Importe") var importeOrdenacion: Double,
    @ColumnInfo(name = "Fecha") var fecha: String
)

fun Factura.toDatabase() = FacturaModel(descEstado = descEstado, importeOrdenacion = importeOrdenacion, fecha = fecha)