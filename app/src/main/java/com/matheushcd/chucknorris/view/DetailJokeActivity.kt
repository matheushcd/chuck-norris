package com.matheushcd.chucknorris.view

import android.annotation.TargetApi
import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.matheushcd.chucknorris.R


class DetailJokeActivity: AppCompatActivity() {

    var URL: String = ""
    var webView: WebView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailjoke)

        URL = intent.getStringExtra("URL") as String

        setupView()
    }

    private fun setupView() {
        webView = findViewById(R.id.webView)
        webView!!.settings.javaScriptEnabled = true
        if (!URL.isNullOrBlank()) {
            webView!!.loadUrl(URL)
        }
    }

}