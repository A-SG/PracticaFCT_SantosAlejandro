package com.example.practicaprueba

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.practicaprueba.data.FacturaRepository
import com.example.practicaprueba.data.adapter.FacturasAdapter
import com.example.practicaprueba.data.network.domain.GetFacturasUseCase
import com.example.practicaprueba.data.network.domain.model.Factura
import com.example.practicaprueba.data.network.domain.ui.view.MainActivity
import com.example.practicaprueba.data.network.domain.ui.view.SecondActivity
import com.example.practicaprueba.data.network.domain.ui.viewmodel.FacturasViewModel
import com.example.practicaprueba.databinding.ActivityMainBinding
import com.example.practicaprueba.databinding.ActivityPantallaInicioBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class PantallaInicio : AppCompatActivity() {

    /*private lateinit var binding: ActivityPantallaInicioBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPantallaInicioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.practica1.setOnClickListener(){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.practica2.setOnClickListener(){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }*/

    //Variables
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: FacturasAdapter
    private val facturasViewModel: FacturasViewModel by viewModels()
    private var pulsaciones = 0
    private lateinit var listadoFiltraFactura: String
    private val gson = Gson()
    @Inject
    lateinit var facturaRepository: FacturaRepository
    @Inject
    lateinit var repository: FacturaRepository
    @Inject
    lateinit var getFacturasUseCase: GetFacturasUseCase



    // Obtenemos los resoltados de la SecondActivity
    private val responseLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            val jsonFiltroFacturasModel = activityResult.data?.getStringExtra("ListaFiltrada")

            if (activityResult.resultCode == RESULT_OK) {
                adapter.facturas = gson.fromJson(
                    jsonFiltroFacturasModel,
                    object : TypeToken<List<Factura?>?>() {}.type
                )
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
                withContext(Dispatchers.IO) {
                    val result = getFacturasUseCase()
                    val intent = Intent(this@PantallaInicio, SecondActivity::class.java)

                    if (result.isNotEmpty()) {
                        listadoFiltraFactura = gson.toJson(result)
                        intent.putExtra("listaFacturasSinFiltrar", listadoFiltraFactura)
                        responseLauncher.launch(intent)
                    }
                }
            }
        }

        //Acción que activa el Retromock al pulsar 5 veces el boton
        binding.activarRetromock.setOnClickListener() {
            pulsaciones++
            if (pulsaciones == 5) {
                facturasViewModel.onCreate()
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