package com.example.grupo7

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment

 class ProfileFragment : Fragment() {


    private lateinit var tvTitle: TextView
    private lateinit var tvTaskNumber: TextView
    private lateinit var tvTaskPercent: TextView
    private lateinit var progressBar: ProgressBar
     private lateinit var tvVoiceNotesCount: TextView
    private lateinit var tvProjectsCount: TextView

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

        mostrarInformacion()

        return view
    }

    private fun mostrarInformacion() {

        val nombre = "Grupo 7"


        tvTitle.text = "¡Hola $nombre!"
        tvTaskNumber.text = "5"
        tvTaskPercent.text = "1%"
        progressBar.progress = 1

        tvVoiceNotesCount.text = "3"
        tvProjectsCount.text = "5"
    }
}