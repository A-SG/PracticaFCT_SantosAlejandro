package com.example.practicaprueba.data

import com.example.practicaprueba.data.database.dao.FacturaDao
import com.example.practicaprueba.data.model.FacturaModel
import com.example.practicaprueba.data.network.FacturasService
import com.example.practicaprueba.domain.model.Factura
import com.example.practicaprueba.domain.model.toDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FacturaRepository @Inject constructor(private val facturasDao: FacturaDao,
                                            private val apiService: FacturasService
){


    /*suspend fun getFacturasApi(): List<FacturaModel> {
        val response = api.getFacturas()
        facturasProvider.facturas = response.facturas
        return response.facturas
    }*/
    suspend fun getFacturasApi(): List<Factura>{
        val response :List<FacturaModel> = apiService.getFacturas().facturas
        return response.map { it.toDomain() }
    }


    suspend fun getFacturasDatabase(): List<Factura>{
        return withContext(Dispatchers.IO){
            val response:List<FacturaModel> = facturasDao.getFacturas()
            response.map { it.toDomain()}
        }
    }

    suspend fun insertFacturas(facturas:List<FacturaModel>){
        withContext(Dispatchers.IO)
        {
            facturasDao.insert(facturas)
        }
    }

    suspend fun clearFacturas(){
        withContext(Dispatchers.IO)
        {
            facturasDao.deleteAllFacturas()
        }

    }




}