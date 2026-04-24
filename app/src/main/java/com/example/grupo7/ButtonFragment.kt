package com.example.grupo7

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.ChipGroup
import com.google.android.material.textfield.TextInputEditText
import java.util.Calendar

class ButtonFragment : Fragment() {

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

        // Botón Guardar (arriba)
        view.findViewById<MaterialButton>(R.id.btn_save).setOnClickListener {
            guardarTarea(etTitle, etNotes, etDate, etTime, chipGroup)
        }

        // Grabar voz
        view.findViewById<MaterialButton>(R.id.btn_voice).setOnClickListener {
            Toast.makeText(context, "Grabando nota de voz...", Toast.LENGTH_SHORT).show()
        }

        // Adjuntar imagen
        view.findViewById<MaterialButton>(R.id.btn_image).setOnClickListener {
            Toast.makeText(context, "Seleccionar imagen...", Toast.LENGTH_SHORT).show()
        }

        // Grabar video
        view.findViewById<MaterialButton>(R.id.btn_video).setOnClickListener {
            Toast.makeText(context, "Grabando video...", Toast.LENGTH_SHORT).show()
        }

        // Selector de fecha
        etDate.setOnClickListener {
            val cal = Calendar.getInstance()
            DatePickerDialog(
                requireContext(),
                { _, year, month, day ->
                    etDate.setText("%02d/%02d/%04d".format(day, month + 1, year))
                },
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        // Selector de hora
        etTime.setOnClickListener {
            val cal = Calendar.getInstance()
            TimePickerDialog(
                requireContext(),
                { _, hour, minute ->
                    etTime.setText("%02d:%02d".format(hour, minute))
                },
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
            ).show()
        }

        // Botón Create Task (abajo)
        view.findViewById<MaterialButton>(R.id.btn_create_task).setOnClickListener {
            guardarTarea(etTitle, etNotes, etDate, etTime, chipGroup)
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

        if (title.isEmpty()) {
            etTitle.error = "El título es obligatorio"
            return
        }

        val prioridad = when (chipGroup.checkedChipId) {
            R.id.chip_low  -> "Baja"
            R.id.chip_high -> "Alta"
            else           -> "Media"
        }

        Toast.makeText(
            context,
            "✅ Tarea \"$title\" creada · Prioridad: $prioridad",
            Toast.LENGTH_LONG
        ).show()

        etTitle.text?.clear()
        etNotes.text?.clear()
        etDate.text?.clear()
        etTime.text?.clear()
    }
}
