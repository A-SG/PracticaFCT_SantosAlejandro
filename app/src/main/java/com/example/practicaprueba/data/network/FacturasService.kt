package com.example.practicaprueba.data.network

import com.example.practicaprueba.core.RetrofitHelper
import com.example.practicaprueba.data.model.NumFactura
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FacturasService @Inject constructor(private val api : ApiService){

    suspend fun getFacturas() : NumFactura {
        return withContext(Dispatchers.IO){
            val response = api.getFacturas()
            response.body()!!
        }
    }
}