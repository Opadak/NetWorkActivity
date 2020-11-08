package com.example.network

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.nio.Buffer

class NetworkActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_network)

        //요청 보내기.
        val urlString : String = "http://mellowcode.org/json/students/"
        val url : URL = URL(urlString)
        val connection :HttpURLConnection = url.openConnection() as HttpURLConnection

        //요청 확인하는 설정
        connection.requestMethod = "GET"
        connection.setRequestProperty("Content-Type", "application/json") //내가 보내는 속성은 Content- type이고 application/json이다. // Header 작성.

        if (connection.responseCode == HttpURLConnection.HTTP_OK) {
            val reader = BufferedReader(
                InputStreamReader(
                    connection.inputStream,
                "UTF-8"
                )
            )
            buffer = reader.readLine()
        }

    }
}