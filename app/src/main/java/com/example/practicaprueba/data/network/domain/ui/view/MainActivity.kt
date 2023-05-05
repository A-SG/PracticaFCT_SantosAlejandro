package com.example.practicaprueba.data.network.domain.ui.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.practicaprueba.data.FacturaRepository
import com.example.practicaprueba.data.adapter.FacturasAdapter
import com.example.practicaprueba.data.network.domain.GetFacturasUseCase
import com.example.practicaprueba.data.network.domain.model.Factura
import com.example.practicaprueba.data.network.domain.ui.viewmodel.FacturasViewModel
import com.example.practicaprueba.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: FacturasAdapter
    private val facturasViewModel: FacturasViewModel by viewModels()
    @Inject lateinit var facturaRepository: FacturaRepository
    private lateinit var listadoFiltraFactura: String
    private val gson = Gson()
    @Inject lateinit var repository: FacturaRepository
    @Inject lateinit var getFacturasUseCase: GetFacturasUseCase
    private var pulsaciones = 1;


    private val responseLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            val jsonFiltroFacturasModel = activityResult.data?.getStringExtra("ListaFiltrada")

            Log.d("ListaRecibida",jsonFiltroFacturasModel.toString())


            if (activityResult.resultCode == RESULT_OK) {
                adapter.facturas = gson.fromJson(jsonFiltroFacturasModel, object : TypeToken<List<Factura?>?>() {}.type)
                Log.d("Adapter", adapter.facturas.toString())

                facturasViewModel.facturas.value = adapter.facturas
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
               lifecycleScope.launch {
                   withContext(Dispatchers.IO){
                       var result = getFacturasUseCase()
                       val intent = Intent(this@MainActivity, SecondActivity::class.java)

                       if (result.isNotEmpty()){
                           listadoFiltraFactura = gson.toJson(result)
                           intent.putExtra("listaFacturasSinFiltrar", listadoFiltraFactura)
                           responseLauncher.launch(intent)
                       }
                   }
               }
        }

        binding.activarRetromock.setOnClickListener(){
            pulsaciones++
            Toast.makeText(this, "llevas:$pulsaciones", Toast.LENGTH_SHORT).show()
            if(pulsaciones == 5){
                facturasViewModel.onCreate()
                Log.d("listapulsaciones",facturasViewModel.facturas.value.toString())
                pulsaciones = 0
            }
        }

        //Obsevador del ViewModel
        facturasViewModel.facturas.observe(this) {
            adapter.facturas = facturasViewModel.facturas.value!!
            adapter.notifyDataSetChanged()
        }
    }


}





