package com.example.practicaprueba.data.network.domain.ui.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.practicaprueba.data.FacturaRepository
import com.example.practicaprueba.data.adapter.FacturasAdapter
import com.example.practicaprueba.data.network.domain.model.Factura
import com.example.practicaprueba.data.network.domain.ui.viewmodel.FacturasViewModel
import com.example.practicaprueba.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: FacturasAdapter
    private val facturasViewModel: FacturasViewModel by viewModels()
    @Inject lateinit var facturaRepository: FacturaRepository
    private lateinit var listadoFiltraFactura: String
    private val gson = Gson()


    private val responseLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            val jsonFiltroFacturasModel = activityResult.data?.getStringExtra("ListaFiltrada")
            Log.d("Adapter", adapter.facturas.toString())


            if (activityResult.resultCode == RESULT_OK) {
                // Intento de filtrar pasando lo valores de cada elemento
                adapter.facturas = gson.fromJson(
                    jsonFiltroFacturasModel,
                    object : TypeToken<List<Factura?>?>() {}.type
                )
                adapter.notifyDataSetChanged()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {

            super.onCreate(savedInstanceState)
            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)

            adapter = FacturasAdapter(emptyList())
            binding.rvFacturas.adapter = adapter
            facturasViewModel.onCreate()

            //Botón para pasar a la SecondActivity ( Actividad de fitros)
            binding.imageButtonFiltro.setOnClickListener() {

            val intent = Intent(this, SecondActivity::class.java)

                if (facturasViewModel.facturas.value?.isNotEmpty() == true){
                    listadoFiltraFactura = gson.toJson(facturasViewModel.facturas.value)
                    intent.putExtra("listaFacturasSinFiltrar", listadoFiltraFactura)
                    Log.d("listaporintent", listadoFiltraFactura)
                    responseLauncher.launch(intent)
                    facturasViewModel.onCreate()
                }
        }

        //Obsevador del ViewModel
        facturasViewModel.facturas.observe(this) {
            adapter.facturas = facturasViewModel.facturas.value!!
            adapter.notifyDataSetChanged()
        }
    }
}





