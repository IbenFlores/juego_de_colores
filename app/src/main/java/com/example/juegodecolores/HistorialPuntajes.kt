package com.example.juegodecolores

// usamos un singleton (object) para mantener el historial solo durante la sesion
object HistorialPuntajes {
    val puntajes = mutableListOf<Int>()
}