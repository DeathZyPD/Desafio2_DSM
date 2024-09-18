package com.example.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class HistorialComprasActivity : AppCompatActivity() {

    private lateinit var recyclerViewHistorial: RecyclerView
    private lateinit var comprasAdapter: ComprasAdapter
    private val compras = mutableListOf<Compras>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historial_compras)

        recyclerViewHistorial = findViewById(R.id.recyclerViewHistorial)
        recyclerViewHistorial.layoutManager = LinearLayoutManager(this)

        comprasAdapter = ComprasAdapter(compras)
        recyclerViewHistorial.adapter = comprasAdapter

        // Cargar compras desde Firestore
        val db = FirebaseFirestore.getInstance()
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        db.collection("usuarios").document(userId).collection("compras")
            .get()
            .addOnSuccessListener { result ->
                compras.clear()
                for (document in result) {
                    val compra = document.toObject(Compras::class.java)
                    compras.add(compra)
                }
                comprasAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                // Manejo de errores
            }
    }
}
