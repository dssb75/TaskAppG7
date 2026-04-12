package com.example.grupo7
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.VideoView
import android.net.Uri
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView


class VideoFragment : Fragment() {

    var videoV: VideoView? = null
    val handler = android.os.Handler(android.os.Looper.getMainLooper())
    var barraTiempo: SeekBar? = null
    var txtReproducido: TextView? = null
    var txtTiempoTotal: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_video, container, false)

        videoV = view.findViewById<VideoView>(R.id.videoView1)
        barraTiempo = view.findViewById<SeekBar>(R.id.seekBar1)
        txtReproducido = view.findViewById<TextView>(R.id.txtTiempoReproducido)
        txtTiempoTotal = view.findViewById<TextView>(R.id.txtTiempoVideoTotal)

        videoV?.setVideoURI(Uri.parse("android.resource://" + requireContext().packageName + "/" + R.raw.prueba3))

        videoV?.setOnPreparedListener { mp ->
            txtTiempoTotal?.text = fomatoTiempo(mp.duration)
            barraTiempo?.max = mp.duration
            videoV?.start()
            actualizarBarra()
        }

        barraTiempo?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(sb: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    videoV?.seekTo(progress)
                    txtReproducido?.text = fomatoTiempo(progress)
                }
            }
            override fun onStartTrackingTouch(sb: SeekBar?) {}
            override fun onStopTrackingTouch(sb: SeekBar?) {}
        })

        view.findViewById<Button>(R.id.btnPlay).setOnClickListener {
            videoV?.start()
            actualizarBarra()
        }
        view.findViewById<Button>(R.id.btnPause).setOnClickListener { videoV?.pause() }
        view.findViewById<Button>(R.id.btnStop).setOnClickListener {
            videoV?.stopPlayback()
            videoV?.resume()
        }

        return view
    }

    // Actualiza la SeekBar cada segundo
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
        val conversionTiempo = String.format("%02d:%02d", minutos, segundos)
        return conversionTiempo
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacksAndMessages(null)
    }
}
