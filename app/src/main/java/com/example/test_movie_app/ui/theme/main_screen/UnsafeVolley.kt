package com.example.test_movie_app.ui.theme.main_screen

import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.toolbox.BasicNetwork
import com.android.volley.toolbox.HurlStack
import com.android.volley.toolbox.NoCache
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

// проблема с апи коллом Volley , ошибка с сертификатами , совет от ГПТ как обойти проблему - кастомная настройка для принятия всех ССЛ сертификатов
object UnsafeVolley {

    fun newRequestQueue(context: Context): RequestQueue {
        val trustAllCerts = arrayOf<TrustManager>(
            object : X509TrustManager {
                override fun checkClientTrusted(
                    chain: Array<X509Certificate>,
                    authType: String
                ) {
                    // ничего не проверяем
                }

                override fun checkServerTrusted(
                    chain: Array<X509Certificate>,
                    authType: String
                ) {
                    // ничего не проверяем
                }


                override fun getAcceptedIssuers(): Array<X509Certificate> = emptyArray()
            }
        )

        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(null, trustAllCerts, SecureRandom())
        val sslSocketFactory: SSLSocketFactory = sslContext.socketFactory

        val hostnameVerifier = HostnameVerifier { _, _ -> true }

        val hurlStack = object : HurlStack() {
            override fun createConnection(url: java.net.URL): java.net.HttpURLConnection {
                val connection = super.createConnection(url)
                if (connection is HttpsURLConnection) {
                    connection.sslSocketFactory = sslSocketFactory
                    connection.hostnameVerifier = hostnameVerifier
                }
                return connection
            }
        }

        return RequestQueue(
            NoCache(),
            BasicNetwork(hurlStack)
        ).apply {
            start()
        }
    }
}