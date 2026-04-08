package com.example.grupo7// java/com/ejemplo/portafoliopersonal/MainActivity.kt
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment


class MainActivity : AppCompatActivity(), MenuFragment.OnOptionClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            onOptionClicked("profile")
        }
    }

    override fun onOptionClicked(option: String) {
        val fragment: Fragment = when (option) {
            "photos" -> PhotosFragment()
            "video" -> VideoFragment()
            "web" -> WebFragment()
            "Boton" -> ButtonFragment()
            else -> ProfileFragment() // Caso por defecto
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.content_fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}
