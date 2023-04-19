package com.example.practicaprueba.ui.view

import DatePickerFragment
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.practicaprueba.R
import com.example.practicaprueba.databinding.ActivitySecondBinding
import java.util.*


class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val locale = Locale("es")
        Locale.setDefault(locale)
        val config = baseContext.resources.configuration
        config.setLocale(locale)
        baseContext.createConfigurationContext(config)

        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.imageButtonSalir.setOnClickListener(){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)


            actionBar?.hide()
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
    }

    private fun mostrarDatepicker() {
        val datePicker = DatePickerFragment { day, month, year -> onDateSelected(day,month,year) }
        datePicker.show(supportFragmentManager, "datePicker")
    }


    fun onDateSelected(day:Int, month: Int, year:Int){
        binding.fechaInicial.text = "$day/$month/$year"
    }

    private fun mostrarDatepickerFin(){
        val datePicker = DatePickerFragment { day, month, year -> onDateSelectedEnd(day,month,year) }
        datePicker.show(supportFragmentManager, "datePicker")
    }
    fun onDateSelectedEnd(day:Int, month: Int, year:Int){
        binding.fechaFin.text = "$day/$month/$year"
    }

}