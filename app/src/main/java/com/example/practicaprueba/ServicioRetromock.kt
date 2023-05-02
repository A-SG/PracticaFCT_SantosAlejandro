package com.example.practicaprueba

import co.infinum.retromock.meta.Mock
import co.infinum.retromock.meta.MockResponse
import com.example.practicaprueba.data.network.domain.model.Factura
import retrofit2.Call
import retrofit2.http.GET

interface ServicioRetromock {

    @Mock
    @MockResponse(body = "response.json")
    @GET("facturas")
    open fun getFacturas(): Call<Factura>?
}