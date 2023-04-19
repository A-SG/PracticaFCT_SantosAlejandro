package com.example.practicaprueba.data.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.practicaprueba.data.model.Factura
import com.example.practicaprueba.R

class FacturasAdapter(var  facturas: List<Factura>): RecyclerView.Adapter<FacturasViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FacturasViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return FacturasViewHolder(layoutInflater.inflate(R.layout.item_factura, parent, false))
    }

    override fun getItemCount(): Int {
        return facturas.size
    }

    override fun onBindViewHolder(holder: FacturasViewHolder, position: Int) {
        val item = facturas[position]
        holder.bind(item)

    }


}