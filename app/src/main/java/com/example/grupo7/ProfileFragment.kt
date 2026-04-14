package com.example.grupo7

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

// a. Fragmento utilizado por la aplicación para el módulo de Perfil
class ProfileFragment : Fragment() {

    // d. Definición de variables
    private lateinit var tvTitle: TextView
    private lateinit var tvName: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // b. Creación de la interfaz para el fragmento
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        // d. Vínculo de las variables con los identificadores (IDs)
        tvTitle = view.findViewById(R.id.tv_profile_title)
        tvName = view.findViewById(R.id.tv_user_name)

        // e. Identificación de eventos y métodos
        mostrarInformacion()

        return view
    }

    // e. Declaración de los métodos del fragmento
    private fun mostrarInformacion() {
        // Método para asignar los datos a las vistas
        tvTitle.text = "Perfil del Usuario"
        tvName.text = "Grupo 7 - Desarrollo Android"
    }
}
