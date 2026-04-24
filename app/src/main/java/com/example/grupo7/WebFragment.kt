package com.example.grupo7

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment

// a. Fragmento utilizado en la aplicación
class WebFragment : Fragment() {
    // d. Definición de variables
    private lateinit var webViewCalendar: WebView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // b. Creación de la interfaz
        val view = inflater.inflate(R.layout.fragment_web, container, false)
        // d. Vínculo de variables con identificadores
        webViewCalendar = view.findViewById(R.id.webview_calendar)
        // e. Identificación de eventos y métodos
        configurarWebView()
        iniciarCarga()
        return view
    }

    // e. Declaración de métodos
    private fun configurarWebView() {
        val webSettings: WebSettings = webViewCalendar.settings
        webSettings.javaScriptEnabled = true
        webSettings.domStorageEnabled = true
        webSettings.useWideViewPort = true
        webSettings.loadWithOverviewMode = true
        webViewCalendar.webViewClient = WebViewClient()
    }
    private fun iniciarCarga() {
        webViewCalendar.loadUrl("https://calendar.google.com/calendar/u/0/r")
    }
}
