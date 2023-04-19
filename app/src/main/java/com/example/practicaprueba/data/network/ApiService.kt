package com.example.practicaprueba.data.network

import com.example.practicaprueba.data.model.NumFactura
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("facturas")
    suspend fun getFacturas() : Response<NumFactura>

}