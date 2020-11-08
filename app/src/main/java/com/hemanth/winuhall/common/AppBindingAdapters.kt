package com.hemanth.winuhall.common

import android.app.Activity
import android.util.Log
import android.view.View
import android.webkit.*
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter

object AppBindingAdapters {

    @BindingAdapter("htmlText")
    @JvmStatic
    fun setHtmlText(textView: TextView, source: String?) {
        textView.text = source?.let { HtmlCompat.fromHtml(it, HtmlCompat.FROM_HTML_MODE_LEGACY) }
    }

    @JvmStatic
    @BindingAdapter("htmlString")
    fun loadHTML(view: WebView, htmlString: String?) {
        htmlString?.let {
            if (it.isEmpty())
                return
            try {
                view.settings.domStorageEnabled = true
                view.settings.setAppCacheEnabled(true)
                view.settings.loadsImagesAutomatically = true
                view.settings.cacheMode = WebSettings.LOAD_DEFAULT
                view.settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                view.settings.javaScriptEnabled = true
                view.loadDataWithBaseURL(null, it, "text/html", "UTF-8", null)

            } catch (ex: Exception) {
                Log.e("TAG", "Error loading html string")
            }
        }
    }

    @BindingAdapter("onBackClick")
    @JvmStatic
    fun setOnBackPress(view: View, value: Boolean) {
        view.setOnClickListener {
            val activity: Activity = view.context as Activity
            activity.onBackPressed()
        }
    }
}