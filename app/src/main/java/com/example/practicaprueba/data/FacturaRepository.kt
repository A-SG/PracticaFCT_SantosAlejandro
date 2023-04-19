package com.example.practicaprueba.data

import com.example.practicaprueba.data.model.Factura
import com.example.practicaprueba.data.model.FacturasProvider
import com.example.practicaprueba.data.network.FacturasService

class FacturaRepository {

    private val api = FacturasService()

    suspend fun getFacturas(): List<Factura> {
        val response = api.getFacturas()
        FacturasProvider.facturas = response.facturas
        return response.facturas
    }
}