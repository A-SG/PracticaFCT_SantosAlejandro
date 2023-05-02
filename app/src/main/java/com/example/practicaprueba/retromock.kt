package com.example.practicaprueba

import co.infinum.retromock.Retromock
import com.example.practicaprueba.core.RetrofitHelper

class retromock {

    lateinit var retrofit:RetrofitHelper

    fun getRetromock(): Retromock{
        return Retromock.Builder().retrofit(retrofit.getRetrofit()).build()
    }

}