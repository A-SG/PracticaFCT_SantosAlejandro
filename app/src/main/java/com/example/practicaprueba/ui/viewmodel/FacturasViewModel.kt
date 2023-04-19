package com.example.practicaprueba.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicaprueba.domain.GetFacturasUseCase
import com.example.practicaprueba.data.model.Factura
import kotlinx.coroutines.launch

class FacturasViewModel : ViewModel() {

    private val _facturas = MutableLiveData<List<Factura>?>(emptyList())
    val facturas: MutableLiveData<List<Factura>?> get() = _facturas
    var getFacturasUseCase = GetFacturasUseCase()

    fun onCreate() {
        viewModelScope.launch {
            val result = getFacturasUseCase()
            _facturas.value = result
        }

    }


}