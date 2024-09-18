package com.example.login

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ComprasAdapter(private val compras: List<Compras>) :
    RecyclerView.Adapter<ComprasAdapter.CompraViewHolder>() {

    class CompraViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompraViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_compra, parent, false)
        return CompraViewHolder(view)
    }

    override fun onBindViewHolder(holder: CompraViewHolder, position: Int) {
        val compra = compras[position]
        holder.view.findViewById<TextView>(R.id.nombreCompra).text = compra.nombreProducto
        holder.view.findViewById<TextView>(R.id.precioCompra).text = "$${compra.precio}"
    }

    override fun getItemCount(): Int = compras.size
}

