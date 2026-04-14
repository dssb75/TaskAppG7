package com.example.grupo7

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment

// a. Fragmento utilizado por la aplicación para el módulo de Fotos
class PhotosFragment : Fragment() {

    // d. Definición de variables
    private lateinit var bloque1: LinearLayout
    private lateinit var txtTitulo1: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // b. Creación de la interfaz
        val view = inflater.inflate(R.layout.fragment_photos, container, false)

        // d. Vínculo de variables con identificadores
        bloque1 = view.findViewById(R.id.bloqueFoto1)
        txtTitulo1 = view.findViewById(R.id.txtTituloBloque1)

        // e. Identificación de eventos y métodos
        configurarEventos()

        return view
    }

    // e. Declaración de métodos
    private fun configurarEventos() {
        // Método para manejar la interacción con las fotos
        bloque1.setOnClickListener {
            // Acción al tocar el bloque de foto
        }
    }
}
