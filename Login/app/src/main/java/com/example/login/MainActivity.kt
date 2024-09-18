package com.example.login

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var productosAdapter: ProductoAdapter
    private val carrito = mutableListOf<Producto>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Configurar Toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Inicializar Firebase Auth y Firestore
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        // Configurar el RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewProductos)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Inicializar el adaptador del RecyclerView
        productosAdapter = ProductoAdapter(emptyList()) { producto ->
            carrito.add(producto)
            Toast.makeText(this, "${producto.nombre} añadido al carrito", Toast.LENGTH_SHORT).show()
        }
        recyclerView.adapter = productosAdapter

        // Cargar productos desde Firebase Firestore
        db.collection("productos")
            .get()
            .addOnSuccessListener { result ->
                val productos = result.map { document ->
                    document.toObject(Producto::class.java)
                }
                // Actualizar el adaptador con los productos obtenidos
                productosAdapter = ProductoAdapter(productos) { producto ->
                    carrito.add(producto)
                    Toast.makeText(this, "${producto.nombre} añadido al carrito", Toast.LENGTH_SHORT).show()
                }
                recyclerView.adapter = productosAdapter
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Error cargando productos: ${exception.message}", Toast.LENGTH_LONG).show()
            }

        // Botón para ver el carrito
        findViewById<Button>(R.id.btnVerCarrito).setOnClickListener {
            val intent = Intent(this, CarritoActivity::class.java)
            intent.putExtra("carrito", ArrayList(carrito)) // Convierte a ArrayList si usas Serializable
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_sign_out -> {
                auth.signOut()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()  // Cierra la actividad actual
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
