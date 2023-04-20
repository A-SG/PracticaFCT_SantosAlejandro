package com.example.practicaprueba.domain

import android.util.Log
import com.example.practicaprueba.data.FacturaRepository
import com.example.practicaprueba.data.model.FacturaModel
import com.example.practicaprueba.data.model.toDatabase
import com.example.practicaprueba.domain.model.Factura
import javax.inject.Inject

class GetFacturasUseCase @Inject constructor(private val repository : FacturaRepository){

    suspend operator fun invoke(): List<Factura> {
        val facturas = repository.getFacturasApi()
        Log.d("prueba", facturas.toString())

        return if (facturas.isNotEmpty()){
            repository.clearFacturas()
            repository.insertFacturas(facturas.map { it.toDatabase() })
            facturas
        }else{
            repository.getFacturasDatabase()
        }
    }
}