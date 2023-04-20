package com.example.practicaprueba.domain.model

import com.example.practicaprueba.data.model.FacturaModel

data class Factura (var descEstado : String, var importeOrdenacion: Double, var fecha: String)

fun FacturaModel.toDomain() = Factura(descEstado, importeOrdenacion, fecha)
