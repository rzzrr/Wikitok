package com.rzatha.wikitok.presentation

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.net.toUri

class WikiWebViewClient(
    private val activity: Activity
) : WebViewClient() {

    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {

        if (url?.contains("wikipedia.org") == true) {
            return false
        }
        val intent = Intent(Intent.ACTION_VIEW, url?.toUri());
        activity.startActivity(intent);
        return true;

    }
}