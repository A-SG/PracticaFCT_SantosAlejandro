package com.example.practicaprueba.data.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupWindow
import androidx.recyclerview.widget.RecyclerView
import com.example.practicaprueba.R
import com.example.practicaprueba.databinding.ItemFacturaBinding
import com.example.practicaprueba.data.network.domain.model.Factura


class FacturasViewHolder (view: View): RecyclerView.ViewHolder(view) {

    private var popupactivo : Boolean = false

    //Uso de binding para acceder directamente a la vista
    val binding = ItemFacturaBinding.bind(view)

    //Función que se llama por cada uno de los items del recyclerView
    fun bind(factura: Factura){

        binding.estadoFactura.text = factura.descEstado
        binding.importeFactura.text = factura.importeOrdenacion.toString()
        binding.fechaFactura.text = factura.fecha

        //Hacer click en cada una de las celdas
        itemView.setOnClickListener(View.OnClickListener {
            if (!popupactivo) {
                popupactivo = true
                val popup = PopupWindow(itemView.context)
                val popupView = LayoutInflater.from(itemView.context).inflate(R.layout.popup_factura, null)
                popup.contentView = popupView
                popup.width = ViewGroup.LayoutParams.WRAP_CONTENT
                popup.height = ViewGroup.LayoutParams.WRAP_CONTENT
                popup.showAtLocation(popupView, 1, 0, 0)

                val closeButton = popupView.findViewById<Button>(R.id.buttonsalir)
                closeButton.setOnClickListener {
                    popup.dismiss()
                    popupactivo = false
                }
                popup.showAsDropDown(itemView)
            }
        })

    }

}