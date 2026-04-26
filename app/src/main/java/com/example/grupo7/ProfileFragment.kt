package com.example.grupo7

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import java.text.SimpleDateFormat
import java.util.*

 class ProfileFragment : Fragment() {


    private lateinit var tvTitle: TextView
    private lateinit var tvTaskNumber: TextView
    private lateinit var tvTaskPercent: TextView
    private lateinit var progressBar: ProgressBar
     private lateinit var tvVoiceNotesCount: TextView
    private lateinit var tvProjectsCount: TextView

    private lateinit var ivProfile: ImageView



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        tvTitle = view.findViewById(R.id.tv_profile_title)
        tvTaskNumber = view.findViewById(R.id.tv_task_number)
        tvTaskPercent = view.findViewById(R.id.tv_task_percent)
        progressBar = view.findViewById(R.id.pb_tasks)

        tvVoiceNotesCount = view.findViewById(R.id.tv_voice_notes_count)
        tvProjectsCount = view.findViewById(R.id.tv_projects_count)
        ivProfile = view.findViewById(R.id.iv_profile_picture)

        mostrarInformacion()

        return view
    }

     private fun obtenerTotalTareas(): Int {
         val prefs = requireContext().getSharedPreferences("tareas_app", android.content.Context.MODE_PRIVATE)
         val tareasString = prefs.getString("lista_tareas", "[]")
         val jsonArray = org.json.JSONArray(tareasString)

         return jsonArray.length()
     }

     private fun obtenerTareasVencidas(): Int {
         val prefs = requireContext().getSharedPreferences("tareas_app", android.content.Context.MODE_PRIVATE)
         val jsonString = prefs.getString("lista_tareas", "[]")
         val jsonArray = org.json.JSONArray(jsonString)

         val formato = java.text.SimpleDateFormat("dd/MM/yyyy HH:mm", java.util.Locale.ROOT)
         val ahora = java.util.Calendar.getInstance().time

         var contadorVencidas = 0

         for (i in 0 until jsonArray.length()) {
             try {
                 val tarea = jsonArray.getJSONObject(i)
                 val fechaStr = tarea.optString("fecha", "").trim()
                 val horaStr = tarea.optString("hora", "").trim()

                 if (fechaStr.isNotEmpty() && horaStr.isNotEmpty()) {
                     val fechaTarea = formato.parse("$fechaStr $horaStr")

                     val esVencida = fechaTarea != null && fechaTarea.before(ahora)

                     println("TAREA $i: $fechaStr $horaStr | ¿VENCIDA?: $esVencida")

                     if (esVencida) {
                         contadorVencidas++
                     }
                 } else {
                     println("TAREA $i: Saltada por falta de datos (Fecha: '$fechaStr', Hora: '$horaStr')")
                 }
             } catch (e: Exception) {
                 println("TAREA $i: Error crítico al procesar: ${e.message}")
             }
         }
         return contadorVencidas
     }

    private fun mostrarInformacion() {
        val nombre = "Grupo 7"
        tvTitle.text = "¡Hola $nombre!"
        ivProfile.setImageResource(R.drawable.perfil)

        val total = obtenerTotalTareas()
        val vencidas = obtenerTareasVencidas()

        val porcentaje = if (total > 0) {
            ((vencidas.toFloat() / total.toFloat()) * 100).toInt()
        } else {
            0
        }

        tvTaskNumber.text = vencidas.toString()
        tvTaskPercent.text = "$porcentaje%"
        progressBar.progress = porcentaje

        if (vencidas > 0) {
            tvTaskNumber.setTextColor(android.graphics.Color.RED)
            tvTaskPercent.setTextColor(android.graphics.Color.RED)
        } else {
            tvTaskNumber.setTextColor(android.graphics.Color.BLACK)
            tvTaskPercent.setTextColor(android.graphics.Color.BLACK)
        }

        tvVoiceNotesCount.text = "3"
        tvProjectsCount.text = "5"

    }
}