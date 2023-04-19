package com.example.practicaprueba.domain

import com.example.practicaprueba.data.FacturaRepository
import com.example.practicaprueba.data.model.Factura

class GetFacturasUseCase {

    private val repository = FacturaRepository()

    suspend operator fun invoke(): List<Factura>?{
        return repository.getFacturas()
    }
}