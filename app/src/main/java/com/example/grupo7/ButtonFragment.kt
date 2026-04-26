package com.example.grupo7

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.ChipGroup
import com.google.android.material.textfield.TextInputEditText
import org.json.JSONArray
import org.json.JSONObject
import java.util.Calendar

class TareaAdapter(
    private var tareas: MutableList<JSONObject>,
    private val onDelete: (Int) -> Unit
) : RecyclerView.Adapter<TareaAdapter.VH>() {

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle = view.findViewById<TextView>(R.id.tv_task_title)
        val tvMeta  = view.findViewById<TextView>(R.id.tv_task_meta)
        val btnDel  = view.findViewById<MaterialButton>(R.id.btn_delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false))

    override fun getItemCount() = tareas.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val t = tareas[position]
        holder.tvTitle.text = t.optString("titulo", "Sin título")
        val meta = buildString {
            val fecha = t.optString("fecha", "")
            val hora  = t.optString("hora", "")
            val prio  = t.optString("prioridad", "")
            if (fecha.isNotEmpty()) append("📅 $fecha  ")
            if (hora.isNotEmpty())  append("⏰ $hora  ")
            if (prio.isNotEmpty())  append("🔖 $prio")
        }
        holder.tvMeta.text = meta
        holder.btnDel.setOnClickListener { onDelete(position) }
    }

    fun actualizar(nuevas: MutableList<JSONObject>) {
        tareas = nuevas
        notifyDataSetChanged()
    }
}

class ButtonFragment : Fragment() {

    private lateinit var adapter: TareaAdapter
    private lateinit var tvCount: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_button, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etTitle   = view.findViewById<TextInputEditText>(R.id.et_title)
        val etNotes   = view.findViewById<TextInputEditText>(R.id.et_notes)
        val etDate    = view.findViewById<TextInputEditText>(R.id.et_date)
        val etTime    = view.findViewById<TextInputEditText>(R.id.et_time)
        val chipGroup = view.findViewById<ChipGroup>(R.id.chip_group_priority)
        val rv        = view.findViewById<RecyclerView>(R.id.rv_tasks)
        tvCount       = view.findViewById(R.id.tv_task_count)

        adapter = TareaAdapter(cargarTareas()) { pos -> eliminarTarea(pos) }
        rv.layoutManager = LinearLayoutManager(context)
        rv.adapter = adapter
        actualizarContador()

        view.findViewById<MaterialButton>(R.id.btn_save).setOnClickListener {
            guardarTarea(etTitle, etNotes, etDate, etTime, chipGroup)
        }
        view.findViewById<MaterialButton>(R.id.btn_create_task).setOnClickListener {
            guardarTarea(etTitle, etNotes, etDate, etTime, chipGroup)
        }
        view.findViewById<MaterialButton>(R.id.btn_voice).setOnClickListener {
            Toast.makeText(context, "Grabando nota de voz...", Toast.LENGTH_SHORT).show()
        }
        view.findViewById<MaterialButton>(R.id.btn_image).setOnClickListener {
            Toast.makeText(context, "Seleccionar imagen...", Toast.LENGTH_SHORT).show()
        }
        view.findViewById<MaterialButton>(R.id.btn_video).setOnClickListener {
            Toast.makeText(context, "Grabando video...", Toast.LENGTH_SHORT).show()
        }

        etDate.setOnClickListener {
            val cal = Calendar.getInstance()
            DatePickerDialog(requireContext(), { _, year, month, day ->
                etDate.setText("%02d/%02d/%04d".format(day, month + 1, year))
            }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()
        }
        etTime.setOnClickListener {
            val cal = Calendar.getInstance()
            TimePickerDialog(requireContext(), { _, hour, minute ->
                etTime.setText("%02d:%02d".format(hour, minute))
            }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }
    }

    private fun guardarTarea(
        etTitle: TextInputEditText,
        etNotes: TextInputEditText,
        etDate: TextInputEditText,
        etTime: TextInputEditText,
        chipGroup: ChipGroup
    ) {
        val title = etTitle.text.toString().trim()
        if (title.isEmpty()) { etTitle.error = "El título es obligatorio"; return }

        val prioridad = when (chipGroup.checkedChipId) {
            R.id.chip_low  -> "Baja"
            R.id.chip_high -> "Alta"
            else           -> "Media"
        }

        val prefs     = requireContext().getSharedPreferences("tareas_app", Context.MODE_PRIVATE)
        val jsonArray = JSONArray(prefs.getString("lista_tareas", "[]"))

        val nueva = JSONObject().apply {
            put("titulo",    title)
            put("notas",     etNotes.text.toString().trim())
            put("fecha",     etDate.text.toString().trim())
            put("hora",      etTime.text.toString().trim())
            put("prioridad", prioridad)
        }
        jsonArray.put(nueva)
        prefs.edit().putString("lista_tareas", jsonArray.toString()).apply()

        Toast.makeText(context, "✅ Tarea \"$title\" guardada", Toast.LENGTH_SHORT).show()

        etTitle.text?.clear()
        etNotes.text?.clear()
        etDate.text?.clear()
        etTime.text?.clear()

        adapter.actualizar(cargarTareas())
        actualizarContador()
    }

    private fun eliminarTarea(pos: Int) {
        val prefs     = requireContext().getSharedPreferences("tareas_app", Context.MODE_PRIVATE)
        val jsonArray = JSONArray(prefs.getString("lista_tareas", "[]"))
        val nueva     = JSONArray()
        for (i in 0 until jsonArray.length()) { if (i != pos) nueva.put(jsonArray.get(i)) }
        prefs.edit().putString("lista_tareas", nueva.toString()).apply()
        adapter.actualizar(cargarTareas())
        actualizarContador()
        Toast.makeText(context, "Tarea eliminada", Toast.LENGTH_SHORT).show()
    }

    private fun cargarTareas(): MutableList<JSONObject> {
        val prefs     = requireContext().getSharedPreferences("tareas_app", Context.MODE_PRIVATE)
        val jsonArray = JSONArray(prefs.getString("lista_tareas", "[]"))
        return MutableList(jsonArray.length()) { jsonArray.getJSONObject(it) }
    }

    private fun actualizarContador() {
        val prefs = requireContext().getSharedPreferences("tareas_app", Context.MODE_PRIVATE)
        val count = JSONArray(prefs.getString("lista_tareas", "[]")).length()
        tvCount.text = "$count tarea(s)"
    }
}
