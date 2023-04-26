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


@AndroidEntryPoint
class SecondActivity : AppCompatActivity(){

    private lateinit var binding: ActivitySecondBinding
    @Inject lateinit var facturaRepository :FacturaRepository
    //private lateinit var adapter : FacturasAdapter


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
            /*val resultIntent = Intent()
            val listaFacturas : String
            val json = Gson()
            listaFacturas = json.toJson(getParametrosEntradaActividad())
            resultIntent.putExtra("ListaFiltrada",listaFacturas)
            setResult(RESULT_OK, resultIntent)*/
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

        //Botón de aplicar filtros a la correspondintes facturas
        binding.btnAplicarFiltros.setOnClickListener(){
            /*val intent = Intent(this, MainActivity::class.java)
            //startActivity(intent)
            intent.putExtra("fechaInicio", binding.fechaInicial.text.toString())
            intent.putExtra("fechaFin", binding.fechaFin.text.toString())
            intent.putExtra("SliderImporte", binding.slImporte.value)
            intent.putExtra("Pagadas", binding.cbPagadas.isChecked)
            intent.putExtra("Anuladas", binding.cbAnuladas.isChecked)
            intent.putExtra("Cuotafija", binding.cbCuotafija.isChecked)
            intent.putExtra("Planpago", binding.cbPlanpago.isChecked)
            intent.putExtra("PedientesPago", binding.cbPedientesPago.isChecked)
            setResult(RESULT_OK, intent)
            getParametrosEntradaActividad()*/

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


            //Filtrado de factura por su importe
            listaFiltrada = facturas.filter { factura: Factura -> factura.importeOrdenacion <= binding.slImporte.value.toDouble() }

            //Filtrado de facturas por fecha de inicio y fecha de fin
            if (binding.fechaInicial.text.toString() != "dia/mes/año" && binding.fechaFin.text.toString() != "dia/mes/año" ){
                firstDate = formatoFecha.parse(binding.fechaFin.text.toString())
                secondDate = formatoFecha.parse(binding.fechaInicial.text.toString())
                listaFiltrada = facturas.filter { factura: Factura -> formatoFecha.parse(factura.fecha) >= secondDate && formatoFecha.parse(factura.fecha) <= firstDate}
            }

            //Filtrado de facturas por estado
            if (binding.cbPagadas.isChecked)
            {
                    listaFiltrada += facturas.filter { factura: Factura -> factura.descEstado == "Pagada" }
            }

            if (binding.cbPagadas.isChecked)
            {
                listaFiltrada += facturas.filter { factura: Factura -> factura.descEstado == "Anuladas" }
            }

            if (binding.cbPagadas.isChecked)
            {
                listaFiltrada += facturas.filter { factura: Factura -> factura.descEstado == "Cuota Fija" }
            }

            if (binding.cbPagadas.isChecked)
            {
                listaFiltrada += facturas.filter { factura: Factura -> factura.descEstado == "Plan de pago" }
            }

            if (binding.cbPedientesPago.isChecked)
            {
                listaFiltrada += facturas.filter { factura: Factura -> factura.descEstado == "Pendiente de pago" }
            }
            Log.d("listaFiltradaFecha1", listaFiltrada.toString())
        }
        Log.d("ListaFiltradadesdejson" , listaFiltrada.toString())
        return  listaFiltrada
    }
}