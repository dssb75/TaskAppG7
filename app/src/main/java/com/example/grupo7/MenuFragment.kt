package com.example.grupo7
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import java.lang.ClassCastException
import android.widget.Button

class MenuFragment : Fragment(R.layout.fragment_menu) {

    interface OnOptionClickListener {
        fun onOptionClicked(option: String)
    }

    private var listener: OnOptionClickListener? = null

    override fun onViewCreated(view: View  , savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (context is OnOptionClickListener) {
            listener = context as OnOptionClickListener
        } else {
            throw ClassCastException("$context must implement OnOptionClickListener")
        }
        view.findViewById<Button>(R.id.btn_button).setOnClickListener {listener?.onOptionClicked(option = "Boton")}
        view.findViewById<Button>(R.id.btn_web).setOnClickListener {listener?.onOptionClicked(option = "web")}
        view.findViewById<Button>(R.id.btn_video).setOnClickListener {listener?.onOptionClicked(option = "video")}
        view.findViewById<Button>(R.id.btn_photos).setOnClickListener {listener?.onOptionClicked(option = "photos")}
        view.findViewById<Button>(R.id.btn_profile).setOnClickListener {listener?.onOptionClicked(option = "profile")}
    }
}