package com.example.practicaprueba.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.practicaprueba.data.adapter.FacturasAdapter
import com.example.practicaprueba.databinding.ActivityMainBinding
import com.example.practicaprueba.ui.viewmodel.FacturasViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter : FacturasAdapter
    private val facturasViewModel: FacturasViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageButtonFiltro.setOnClickListener(){
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }

        adapter = FacturasAdapter(emptyList())
        binding.rvFacturas.adapter = adapter

        facturasViewModel.onCreate()

        facturasViewModel.facturas.observe(this, Observer {
            adapter.facturas = facturasViewModel.facturas.value!!
            adapter.notifyDataSetChanged()
        })
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


