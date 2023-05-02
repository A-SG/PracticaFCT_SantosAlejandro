package com.example.practicaprueba.data.network.domain.ui.view

import DatePickerFragment
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.practicaprueba.R
import com.example.practicaprueba.data.FacturaRepository
import com.example.practicaprueba.data.network.domain.model.Factura
import com.example.practicaprueba.databinding.ActivitySecondBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.math.ceil



@AndroidEntryPoint
class SecondActivity : AppCompatActivity(){

    private lateinit var binding: ActivitySecondBinding
    @Inject lateinit var facturaRepository :FacturaRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val locale = Locale("es")
        Locale.setDefault(locale)
        val config = baseContext.resources.configuration
        config.setLocale(locale)
        baseContext.createConfigurationContext(config)

        binding.imageButtonSalir.setOnClickListener(){
            finish()
        }

        binding.fechaInicial.setOnClickListener(){
            mostrarDatepicker()
        }

        binding.fechaFin.setOnClickListener(){
            mostrarDatepickerFin()
        }

        //Acción del botón de "Borrar filtros"
        binding.btnBorrarFiltros.setOnClickListener(){
            binding.fechaInicial.setText(R.string.fecha)
            binding.fechaFin.setText(R.string.fecha)
            binding.slImporte.value = 0F
            binding.cbPagadas.isChecked = false
            binding.cbAnuladas.isChecked = false
            binding.cbCuotafija.isChecked = false
            binding.cbPedientesPago.isChecked = false
            binding.cbPlanpago.isChecked = false
        }

        binding.slImporte.addOnChangeListener { slider, value, fromUser ->
            binding.variacionImporte.text = ceil(value).toInt().toString()
        }

        //Botón de aplicar filtros a la correspondintes facturas
        binding.btnAplicarFiltros.setOnClickListener(){
            val resultIntent = Intent()
            val listaFacturas : String
            val json = Gson()
            listaFacturas = json.toJson(getParametrosEntradaActividad())
            resultIntent.putExtra("ListaFiltrada",listaFacturas)
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }


    private fun mostrarDatepicker() {
        val datePicker = DatePickerFragment { day, month, year -> onDateSelected(day,month,year) }
        datePicker.show(supportFragmentManager, "datePicker")
    }

    fun onDateSelected(day:Int, month: Int, year:Int){
        binding.fechaInicial.text = "$day/$month/$year"
    }
    fun onDateSelectedEnd(day:Int, month: Int, year:Int){
        binding.fechaFin.text = "$day/$month/$year"
    }

    private fun mostrarDatepickerFin(){
        val datePicker = DatePickerFragment { day, month, year -> onDateSelectedEnd(day,month,year) }
        datePicker.show(supportFragmentManager, "datePicker")
    }

    //Función de filtrado de facturas
    private fun getParametrosEntradaActividad() : List<Factura>{

        //Variables
        var facturas: List<Factura>
        val json= Gson()
        val jsonFiltroFacturasModel = intent.getStringExtra("listaFacturasSinFiltrar")
        var listaFiltrada = emptyList<Factura>()
        val formatoFecha =  SimpleDateFormat("dd/MM/yyyy")
        val firstDate :Date
        val secondDate: Date

        //Obtención de listado de facturas procedentes del main activity
        if (jsonFiltroFacturasModel != null && jsonFiltroFacturasModel.isNotEmpty()) {
            facturas = json.fromJson(jsonFiltroFacturasModel, object : TypeToken<List<Factura?>?>() {}.type)

            var pagadas = emptyList<Factura>()
            var anuladas = emptyList<Factura>()
            var cuotaFija = emptyList<Factura>()
            var planPago = emptyList<Factura>()
            var pendientesPago = emptyList<Factura>()

            if (binding.cbPagadas.isChecked)
            {
                pagadas = facturas.filter { factura: Factura -> factura.descEstado == "Pagada" }
            }

            if (binding.cbAnuladas.isChecked)
            {
                anuladas = facturas.filter { factura: Factura -> factura.descEstado == "Anuladas" }
            }

            if (binding.cbCuotafija.isChecked)
            {

                cuotaFija = facturas.filter { factura: Factura -> factura.descEstado == "Cuota Fija" }

            }

            if (binding.cbPlanpago.isChecked)
            {
                planPago = facturas.filter { factura: Factura -> factura.descEstado == "Plan de pago" }
            }

            if (binding.cbPedientesPago.isChecked) {
                pendientesPago = facturas.filter { factura: Factura -> factura.descEstado == "Pendiente de pago" }
            }

            var listaPorEstado = pagadas + anuladas + planPago + cuotaFija + pendientesPago
            listaFiltrada = listaPorEstado

            Log.d("listaporetsado", listaFiltrada.toString())


            //Filtrado de facturas por fecha de inicio y fecha de fin
            if (binding.fechaInicial.text.toString() != "dia/mes/año" && binding.fechaFin.text.toString() != "dia/mes/año" ){
                firstDate = formatoFecha.parse(binding.fechaFin.text.toString())
                secondDate = formatoFecha.parse(binding.fechaInicial.text.toString())

                Log.d("firstDate", firstDate.toString())
                Log.d("SeconDate", secondDate.toString())

                listaFiltrada = listaPorEstado.filter { factura: Factura -> formatoFecha.parse(factura.fecha) >= secondDate && formatoFecha.parse(factura.fecha) <= firstDate}
            }
            else  if (binding.fechaInicial.text.toString() == "dia/mes/año" && binding.fechaFin.text.toString() != "dia/mes/año" ){
                firstDate = formatoFecha.parse(binding.fechaFin.text.toString())
                listaFiltrada = listaPorEstado.filter { factura: Factura -> formatoFecha.parse(factura.fecha) <= firstDate}

            }else  if (binding.fechaInicial.text.toString() != "dia/mes/año" && binding.fechaFin.text.toString() == "dia/mes/año" ){

                secondDate = formatoFecha.parse(binding.fechaInicial.text.toString())
                listaFiltrada = listaPorEstado.filter { factura: Factura -> formatoFecha.parse(factura.fecha) >= secondDate }
            }


            Log.d("slider", binding.slImporte.value.toString())

            //Filtrado de factura por su importe
            if (listaFiltrada.isEmpty()){
                listaFiltrada = facturas.filter { factura: Factura -> factura.importeOrdenacion <= binding.slImporte.value.toDouble() }
                Log.d("ListaImporte", listaFiltrada.toString())
            }
            if(binding.slImporte.value.toDouble() != 0.0){
                listaFiltrada = listaFiltrada.filter { factura: Factura -> factura.importeOrdenacion <= binding.slImporte.value.toDouble() }
            }
            Log.d("listaFiltradaFecha1", listaFiltrada.toString())
        }
        Log.d("ListaFiltradadesdejson" , listaFiltrada.toString())
        return  listaFiltrada
    }
}