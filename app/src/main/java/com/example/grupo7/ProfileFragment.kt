package com.example.grupo7

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment

// a. Fragmento utilizado por la aplicación para el módulo de Perfil
class ProfileFragment : Fragment() {

    // d. Definición de variables
    private lateinit var tvTitle: TextView
    private lateinit var tvTaskNumber: TextView
    private lateinit var tvTaskPercent: TextView
    private lateinit var progressBar: ProgressBar

    // Nuevas variables para las secciones de conteo
    private lateinit var tvVoiceNotesCount: TextView
    private lateinit var tvProjectsCount: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // b. Creación de la interfaz para el fragmento
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        // d. Vínculo de las variables con los identificadores (IDs)
        tvTitle = view.findViewById(R.id.tv_profile_title)
        tvTaskNumber = view.findViewById(R.id.tv_task_number)
        tvTaskPercent = view.findViewById(R.id.tv_task_percent)
        progressBar = view.findViewById(R.id.pb_tasks)

        // Vínculo de las nuevas secciones actualizadas
        tvVoiceNotesCount = view.findViewById(R.id.tv_voice_notes_count)
        tvProjectsCount = view.findViewById(R.id.tv_projects_count)

        // e. Identificación de eventos y métodos
        mostrarInformacion()

        return view
    }

    // e. Declaración de los métodos del fragmento
    private fun mostrarInformacion() {
        // Definición de datos
        val nombre = "Grupo 7"

        // Asignación de textos según lo solicitado
        tvTitle.text = "¡Hola $nombre!"
        tvTaskNumber.text = "5"
        tvTaskPercent.text = "1%"
        progressBar.progress = 1

        // Valores para las nuevas secciones de CardView
        tvVoiceNotesCount.text = "3"
        tvProjectsCount.text = "5"
    }
}