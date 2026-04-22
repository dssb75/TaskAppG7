package com.example.grupo7// java/com/ejemplo/portafoliopersonal/MainActivity.kt
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment


class MainActivity : AppCompatActivity(), MenuFragment.OnOptionClickListener {
    //DEFINICION DE VARIABLES
    private val boton = "Boton"
    private val web = "Web"
    private val video = "Video"
    private val fotos = "Photos"
    private val perfil = "Profile"

    //METODOS PRINCIPALES
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            onOptionClicked(perfil)
        }
    }

    override fun onOptionClicked(option: String) {
        val fragment: Fragment = when (option) {
            fotos -> PhotosFragment()
            video -> VideoFragment()
            web -> WebFragment()
            boton -> ButtonFragment()
            else -> ProfileFragment()
        }
        cambioColor(option)
        supportFragmentManager.beginTransaction()
            .replace(R.id.content_fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    //METODOS SECUNDARIOS
    private fun cambioColor(option: String) {
        val menuFragment = supportFragmentManager
            .findFragmentById(R.id.menu_fragment_container) as? MenuFragment
        menuFragment?.cambioColor(option)
    }
}
