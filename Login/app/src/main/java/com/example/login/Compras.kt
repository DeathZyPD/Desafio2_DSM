package com.example.login

import com.google.firebase.Timestamp

data class Compras(
    val nombreProducto: String = "",
    val precio: Double = 0.0,
    val cantidad: Int = 1,
    val fecha: Timestamp = Timestamp.now()
)
