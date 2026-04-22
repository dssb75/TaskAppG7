package com.example.grupo7
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import java.lang.ClassCastException
import android.widget.Button

class MenuFragment : Fragment(R.layout.fragment_menu) {
    //DEFINICION DE VARIABLES
    private var btnPerfil: Button? = null
    private var btnFotos: Button? = null
    private var btnVideo: Button? = null
    private var btnWeb: Button? = null
    private var btnCrear: Button? = null
    private val boton = "Boton"
    private val web = "Web"
    private val video = "Video"
    private val fotos = "Photos"
    private val perfil = "Profile"
    private val gris = "#555555"
    private val morado = "#6200EE"
    private val moradoClaro = "#ECE8FE"
    private val blanco = "#FFFFFF"

    //METODOS PRIMARIO
    interface OnOptionClickListener {
        fun onOptionClicked(option: String)
    }

    private var listener: OnOptionClickListener? = null

    override fun onViewCreated(view: View  , savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnPerfil = view.findViewById(R.id.btn_profile)
        btnFotos = view.findViewById(R.id.btn_photos)
        btnVideo = view.findViewById(R.id.btn_video)
        btnWeb = view.findViewById(R.id.btn_web)
        btnCrear = view.findViewById(R.id.btn_button)

        if (context is OnOptionClickListener) {
            listener = context as OnOptionClickListener
        } else {
            throw ClassCastException("$context must implement OnOptionClickListener")
        }

        btnCrear?.setOnClickListener {listener?.onOptionClicked(option = boton)}
        btnWeb?.setOnClickListener {listener?.onOptionClicked(option = web)}
        btnVideo?.setOnClickListener {listener?.onOptionClicked(option = video)}
        btnFotos?.setOnClickListener {listener?.onOptionClicked(option = fotos)}
        btnPerfil?.setOnClickListener {listener?.onOptionClicked(option = perfil)}
        cambioColor(perfil)
    }

    //METODOS SECUNDARIOS
    fun cambioColor(option : String){
        //Reinicio de interfaz
        listOf(btnPerfil, btnFotos, btnVideo, btnWeb, btnCrear).forEach {
            it?.setTextColor(Color.parseColor(gris))
            it?.setBackgroundColor(Color.parseColor(blanco))
        }
        btnPerfil?.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.perfil, 0, 0)
        btnFotos?.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.imagen, 0, 0)
        btnVideo?.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.video, 0, 0)
        btnWeb?.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.web, 0, 0)
        btnCrear?.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.crear, 0, 0)

        //Ajuste de colores por boton
        when (option) {
            fotos -> {
                btnFotos?.setTextColor(Color.parseColor(morado))
                btnFotos?.setBackgroundColor(Color.parseColor(moradoClaro))
                btnFotos?.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.imagen_morado, 0, 0)
            }
            video -> {
                btnVideo?.setTextColor(Color.parseColor(morado))
                btnVideo?.setBackgroundColor(Color.parseColor(moradoClaro))
                btnVideo?.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.video_morado, 0, 0)
            }
            web -> {
                btnWeb?.setTextColor(Color.parseColor(morado))
                btnWeb?.setBackgroundColor(Color.parseColor(moradoClaro))
                btnWeb?.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.web_morado, 0, 0)
            }
            boton -> {
                btnCrear?.setTextColor(Color.parseColor(morado))
                btnCrear?.setBackgroundColor(Color.parseColor(moradoClaro))
                btnCrear?.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.crear_morado, 0, 0)
            }
            else -> {
                btnPerfil?.setTextColor(Color.parseColor(morado))
                btnPerfil?.setBackgroundColor(Color.parseColor(moradoClaro))
                btnPerfil?.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.perfil_morado, 0, 0)
            }
        }
    }

}