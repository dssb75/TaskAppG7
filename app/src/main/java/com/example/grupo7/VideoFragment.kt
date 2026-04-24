package com.example.grupo7

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import android.widget.VideoView
import androidx.fragment.app.Fragment

class VideoFragment : Fragment() {

    //DECLARACION DE VARIABLES
    private var videoV: VideoView? = null
    private var barraTiempo: SeekBar? = null
    private var txtReproducido: TextView? = null
    private var txtTiempoTotal: TextView? = null
    private val handler = android.os.Handler(android.os.Looper.getMainLooper())

    //METODOS PRIMARIO
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_video, container, false)

        videoV = view.findViewById(R.id.videoView1)
        barraTiempo = view.findViewById(R.id.seekBar1)
        txtReproducido = view.findViewById(R.id.txtTiempoReproducido)
        txtTiempoTotal = view.findViewById(R.id.txtTiempoVideoTotal)
        configurarVideo()
        configurarControles(view)
        return view
    }

    //METODOS SECUNDARIOS
    private fun configurarVideo() {
        val uri = Uri.parse("android.resource://" + requireContext().packageName + "/" + R.raw.prueba3)
        videoV?.setVideoURI(uri)
        videoV?.setOnPreparedListener { mp ->
            txtTiempoTotal?.text = fomatoTiempo(mp.duration)
            barraTiempo?.max = mp.duration
        }
    }

    private fun configurarControles(view: View) {
        view.findViewById<Button>(R.id.btnPlay).setOnClickListener {
            videoV?.start()
            actualizarBarra()
        }
        view.findViewById<Button>(R.id.btnPause).setOnClickListener { videoV?.pause() }
        view.findViewById<Button>(R.id.btnStop).setOnClickListener {
            videoV?.stopPlayback()
            videoV?.resume()
        }
    }

    private fun actualizarBarra() {
        handler.postDelayed({
            videoV?.let {
                barraTiempo?.progress = it.currentPosition
                txtReproducido?.text = fomatoTiempo(it.currentPosition)
                if (it.isPlaying) actualizarBarra()
            }
        }, 500)
    }

    private fun fomatoTiempo(ms: Int): String {
        val segundos = (ms / 1000) % 60
        val minutos = (ms / 1000) / 60
        return String.format("%02d:%02d", minutos, segundos)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacksAndMessages(null)
    }
}
