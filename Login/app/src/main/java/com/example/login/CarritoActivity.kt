package com.example.login

import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CarritoActivity : AppCompatActivity() {

    private lateinit var listViewCarrito: ListView
    private lateinit var textViewTotal: TextView
    private lateinit var btnPurchase: Button
    private var carrito = mutableListOf<Producto>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carrito)

        listViewCarrito = findViewById(R.id.listViewCarrito)
        textViewTotal = findViewById(R.id.textViewTotal)
        btnPurchase = findViewById(R.id.btnPurchase)

        carrito = intent.getSerializableExtra("carrito") as MutableList<Producto>
        val adapter = CarritoAdapter(this, carrito)
        listViewCarrito.adapter = adapter

        val total = carrito.sumOf { it.precio }
        textViewTotal.text = getString(R.string.total_label) + " $total"

        btnPurchase.setOnClickListener {
            // Lógica para realizar la compra
            Toast.makeText(this, R.string.purchase_success, Toast.LENGTH_LONG).show()
            // Aquí agregarías el código para guardar la compra en la base de datos
        }





        fun guardarCompra(compra: Compras) {
            val db = FirebaseFirestore.getInstance()
            val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return

            db.collection("usuarios").document(userId).collection("compras")
                .add(compra)
                .addOnSuccessListener {
                    // Compra guardada exitosamente
                }
                .addOnFailureListener { exception ->
                    // Manejo de errores
                }
        }

    }
}
