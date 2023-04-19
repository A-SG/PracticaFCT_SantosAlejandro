package com.example.practicaprueba.data.network

import com.example.practicaprueba.core.RetrofitHelper
import com.example.practicaprueba.data.model.NumFactura
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FacturasService {

    private val retrofit = RetrofitHelper.getRetrofit()

    suspend fun getFacturas() : NumFactura {
        return withContext(Dispatchers.IO){
            val response = retrofit.create(ApiService::class.java).getFacturas()
            response.body()!!
        }
    }
}