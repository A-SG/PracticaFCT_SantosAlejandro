package com.example.practicaprueba.data.network

import android.util.Log
import com.example.practicaprueba.core.RetrofitHelper
import com.example.practicaprueba.data.model.NumFactura
import com.example.practicaprueba.data.network.domain.RetromockService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FacturasService @Inject constructor(private val api : ApiService, private val mock : RetromockService){
    suspend fun getFacturas() : NumFactura {
        return withContext(Dispatchers.IO){
            val response = try {
                api.getFacturas()
            } catch (e : Exception){
                mock.getFactura()
            }
            Log.d("ListaConRetromock" , response.body().toString())
            response?.body()!!
        }
    }
}