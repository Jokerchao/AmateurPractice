package com.kraos.querycalendar.activity

import android.net.http.SslError
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.SslErrorHandler
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.kraos.querycalendar.R
import com.kraos.querycalendar.databinding.ActivityTestKeyBoardBinding

class TestKeyBoardActivity : BaseActivity() {

    val binding: ActivityTestKeyBoardBinding by lazy {
        ActivityTestKeyBoardBinding.inflate(layoutInflater)
    }

    companion object {
        const val TAG = "TestKeyBoardActivity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        Log.d(TAG, "onCreate: ")

        binding.webView.apply {
            val webSettings = settings
            webSettings.domStorageEnabled = true
            webViewClient = object: WebViewClient() {
                override fun onReceivedSslError(
                    view: WebView?,
                    handler: SslErrorHandler?,
                    error: SslError?
                ) {
                    Log.e(TAG, "onReceivedSslError: $error")
                    //证书信任
                    handler?.proceed()
                }
            }
            webSettings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            webSettings.useWideViewPort = true

            loadUrl("https://www.baidu.com")
        }
    }
}