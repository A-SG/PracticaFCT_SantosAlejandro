package com.example.practicaprueba.data.model

import javax.inject.Inject

class FacturasProvider @Inject constructor(){
    var facturas:List<FacturaModel> = emptyList()
}