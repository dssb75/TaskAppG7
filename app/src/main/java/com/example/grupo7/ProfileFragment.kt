package com.example.grupo7

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import org.json.JSONArray
import java.io.File
import java.io.FileOutputStream
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

    private val selectorImagen = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val uri = result.data?.data
            uri?.let {
                guardarImagenInterna(it)
                ivProfile.setImageURI(it)
            }
        }
    }

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

        ivProfile.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            selectorImagen.launch(intent)
        }

        mostrarInformacion()

        return view
    }

    private fun obtenerTotalTareas(): Int {
        val prefs = requireContext().getSharedPreferences("tareas_app", Context.MODE_PRIVATE)
        val tareasString = prefs.getString("lista_tareas", "[]")
        val jsonArray = JSONArray(tareasString)
        return jsonArray.length()
    }

    private fun obtenerTareasVencidas(): Int {
        val prefs = requireContext().getSharedPreferences("tareas_app", Context.MODE_PRIVATE)
        val jsonString = prefs.getString("lista_tareas", "[]")
        val jsonArray = JSONArray(jsonString)
        val formato = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ROOT)
        val ahora = Calendar.getInstance().time
        var contadorVencidas = 0

        for (i in 0 until jsonArray.length()) {
            try {
                val tarea = jsonArray.getJSONObject(i)
                val fechaStr = tarea.optString("fecha", "").trim()
                val horaStr = tarea.optString("hora", "").trim()

                if (fechaStr.isNotEmpty() && horaStr.isNotEmpty()) {
                    val fechaTarea = formato.parse("$fechaStr $horaStr")
                    if (fechaTarea != null && fechaTarea.before(ahora)) {
                        contadorVencidas++
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return contadorVencidas
    }

    private fun obtenernotas(): Int {
        val prefs = requireContext().getSharedPreferences("tareas_app", Context.MODE_PRIVATE)
        return prefs.getInt("notas_voz", 0)
    }

    private fun guardarImagenInterna(uri: Uri) {
        try {
            val inputStream = requireContext().contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            val archivo = File(requireContext().filesDir, "perfil.png")
            val out = FileOutputStream(archivo)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
            out.flush()
            out.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun mostrarInformacion() {
        val nombre = "Grupo 7"
        tvTitle.text = "¡Hola $nombre!"

        val archivoFoto = File(requireContext().filesDir, "perfil.png")
        if (archivoFoto.exists()) {
            val bitmap = BitmapFactory.decodeFile(archivoFoto.absolutePath)
            ivProfile.setImageBitmap(bitmap)
        } else {
            ivProfile.setImageResource(R.drawable.perfil)
        }

        val total = obtenerTotalTareas()
        val vencidas = obtenerTareasVencidas()
        val notasVoz = obtenernotas()

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

        tvVoiceNotesCount.text = notasVoz.toString()
        tvProjectsCount.text = "5"
    }
}