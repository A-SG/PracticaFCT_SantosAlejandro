package com.example.practicaprueba.data.network.domain.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicaprueba.data.network.domain.GetFacturasUseCase
import com.example.practicaprueba.data.network.domain.model.Factura
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FacturasViewModel @Inject constructor(private val getFacturasUseCase: GetFacturasUseCase): ViewModel()  {

    //Variables
    private val _facturas = MutableLiveData<List<Factura>?>(emptyList())
    val facturas: MutableLiveData<List<Factura>?> get() = _facturas

    fun onCreate() {
        viewModelScope.launch {
            val result = getFacturasUseCase()
            _facturas.value = result
        }
    }
}
