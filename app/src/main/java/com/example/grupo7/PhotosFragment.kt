package com.example.grupo7

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

class PhotosFragment : Fragment() {

    private var imgBloque1: ImageView? = null
    private var imgBloque2: ImageView? = null
    private var imgBloque3: ImageView? = null
    private var txtDescripcionSeleccionada: TextView? = null
    private var txtTituloSeleccionado: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_photos, container, false)

        imgBloque1 = view.findViewById(R.id.imgBloque1)
        imgBloque2 = view.findViewById(R.id.imgBloque2)
        imgBloque3 = view.findViewById(R.id.imgBloque3)
        txtTituloSeleccionado = view.findViewById(R.id.txtTituloSeleccionado)
        txtDescripcionSeleccionada = view.findViewById(R.id.txtDescripcionSeleccionada)

        // Descripción inicial
        txtTituloSeleccionado?.text = "Editar Video"
        txtDescripcionSeleccionada?.text =
            "Tarea Pendiente - Día 29 de abril a las 10:00 a.m. - Supervise y actualice los lineamientos visuales para asegurar la coherencia de marca en la nueva campaña estacional. - Tener presente editar el video para su entrega."

        imgBloque1?.setOnClickListener {
            txtTituloSeleccionado?.text = "Editar Video"
            txtDescripcionSeleccionada?.text =
                "Tarea Pendiente - Día 29 de abril a las 10:00 a.m. - Supervise y actualice los lineamientos visuales para asegurar la coherencia de marca en la nueva campaña estacional. - Tener presente editar el video para su entrega."
        }

        imgBloque2?.setOnClickListener {
            txtTituloSeleccionado?.text = "Reunión con Equipo"
            txtDescripcionSeleccionada?.text =
                "Tarea Pendiente - Día 26 de abril a las 4:00 p.m. - Gestione la refactorización técnica de la plataforma principal para mejorar la experiencia del usuario y tiempos de carga. - Manejar objetivo en reunión e indicaciones."
        }

        imgBloque3?.setOnClickListener {
            txtTituloSeleccionado?.text = "Seguimiento de Actividades"
            txtDescripcionSeleccionada?.text =
                "Tarea Pendiente - Día 30 de abril a las 9:00 a.m. - Revisar tareas pendientes, validar avances del proyecto y actualizar compromisos del equipo."
        }

        return view
    }
}