package com.example.login

import java.io.Serializable

data class Producto(
    val nombre: String = "",
    val precio: Double = 0.0,
    val descripcion: String = ""
    // Agrega más campos si es necesario
) : Serializable
