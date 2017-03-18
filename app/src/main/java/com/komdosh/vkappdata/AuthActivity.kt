package com.komdosh.vkappdata

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import com.komdosh.vkappdata.utils.Settings
import com.komdosh.vkappdata.utils.VKUtil
import kotlinx.android.synthetic.main.auth_activity.*
import java.net.URLEncoder


class AuthActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.auth_activity)

        webAuth.settings.javaScriptEnabled = true
        webAuth.isVerticalScrollBarEnabled = false
        webAuth.isHorizontalScrollBarEnabled = false
        webAuth.clearCache(true)
        webAuth.setWebViewClient(VkWebViewClient())

        val url = "https://oauth.vk.com/authorize?client_id=" + Settings.API_VK_ID + "&display=page&redirect_uri=" + URLEncoder.encode(Settings.REDIRECTED_URL, "UTF-8") + "&scope=friends&response_type=token&v=5.62"
        webAuth.loadUrl(url)
        webAuth.visibility = View.VISIBLE
    }

    internal inner class VkWebViewClient : WebViewClient() {
        override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            progress.visibility = View.VISIBLE
            parseUrl(url)
        }

        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
            if (url.startsWith("http://oauth.vk.com/authorize") || url.startsWith("http://oauth.vk.com/oauth/authorize")
                    || url.startsWith("https://oauth.vk.com/authorize") || url.startsWith("https://oauth.vk.com/oauth/authorize")) {
                progress.visibility = View.GONE
                finish()
            }
        }
    }

    fun parseUrl(url: String) {
        try {
            if( url.startsWith(Settings.REDIRECTED_URL) ) {
                if( !url.contains("error") ) {
                    val vkUtil = VKUtil()
                    val auth = vkUtil.parseRedirectUrl(url)
                    webAuth.visibility = View.GONE
                    progress.visibility = View.VISIBLE


                    val intent = Intent()
                    intent.putExtra("token",  auth[0])
                    intent.putExtra("uid", auth[1])

                    setResult(Activity.RESULT_OK, intent)
                    finish()
                } else {
                    setResult(RESULT_CANCELED)
                    finish()
                }
            } else if( url.contains("error?err") ) {
                setResult(RESULT_CANCELED)
                finish()
            }
        } catch( e: Exception ) {
            e.printStackTrace()
            setResult(RESULT_CANCELED)
            finish()
        }
    }

}
