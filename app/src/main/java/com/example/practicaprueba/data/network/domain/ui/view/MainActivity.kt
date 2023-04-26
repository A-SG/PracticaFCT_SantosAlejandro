package com.example.practicaprueba.data.network.domain.ui.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
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
    private lateinit var adapter : FacturasAdapter
    private val facturasViewModel: FacturasViewModel by viewModels()
    @Inject lateinit var facturaRepository : FacturaRepository
    private lateinit var listadoFiltraFactura : String
    private val gson = Gson()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = FacturasAdapter(emptyList())
        binding.rvFacturas.adapter = adapter
        facturasViewModel.onCreate()

        binding.imageButtonFiltro.setOnClickListener(){
            val intent = Intent(this, SecondActivity::class.java)
            listadoFiltraFactura = gson.toJson(facturasViewModel.facturas.value);
            intent.putExtra("listaFacturasSinFiltrar", listadoFiltraFactura)
            startActivityForResult(intent, 1);
            //startActivity(intent)
        }



        //val intent = Intent(this, SecondActivity::class.java)
        facturasViewModel.facturas.observe(this, Observer {
            adapter.facturas = facturasViewModel.facturas.value!!
            adapter.notifyDataSetChanged()
        })
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        var jsonFiltroFacturasModel: String? = data?.getStringExtra("ListaFiltrada")
        val gson = Gson()
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && data != null) {
            if (resultCode == RESULT_OK )
             {
                adapter.facturas= gson.fromJson(jsonFiltroFacturasModel,  object : TypeToken<List<Factura?>?>() {}.type)
                 adapter.notifyDataSetChanged()
            }
        }
    }


    /*private fun mostrarFacturas(){
        val retroData = getRetrofit().create(ApiService::class.java).getFacturas()
        retroData.enqueue(object : Callback<NumFactura> {
            override fun onResponse(
                call: Call<NumFactura>,
                response: Response<NumFactura>
            ) {
                var data = response.body()!!
                //Log.d("ASG", data.facturas.toString())
                adapter.facturas = data.facturas
                adapter.notifyDataSetChanged()

            }
            override fun onFailure(call: Call<NumFactura>, t: Throwable) {
            }

        })
    }*/


    /*private fun mostrarFacturasFecha(){
        val retroData = getRetrofit().create(ApiService::class.java).getFacturasPorFecha("05/02/2019")
        retroData.enqueue(object : Callback<NumFactura> {
            override fun onResponse(
                call: Call<NumFactura>,
                response: Response<NumFactura>
            ) {
                var data = response.body()!!
                //Log.d("ASG", data.facturas.toString())
                adapter.facturas = data.facturas
                adapter.notifyDataSetChanged()

            }
            override fun onFailure(call: Call<NumFactura>, t: Throwable) {
            }

        })
    }*/
}


