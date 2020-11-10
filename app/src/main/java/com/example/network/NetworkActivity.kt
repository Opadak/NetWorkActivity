package com.example.network

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.nio.Buffer

class NetworkActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_network)

        NetworkTask().execute()
    }
}

class NetworkTask():AsyncTask<Any?,Any?,Any?>() {
    override fun doInBackground(vararg params: Any?): Any? {
        //요청 보내기.
        val urlString : String = "http://mellowcode.org/json/students/"
        val url : URL = URL(urlString)
        val connection :HttpURLConnection = url.openConnection() as HttpURLConnection

        //요청 확인하는 설정
        connection.requestMethod = "GET"
        connection.setRequestProperty("Content-Type", "application/json") //내가 보내는 속성은 Content- type이고 application/json이다. // Header 작성.

        var buffer = ""
        if (connection.responseCode == HttpURLConnection.HTTP_OK) {
            val reader = BufferedReader(
                InputStreamReader( //InputStream을 읽을 수 있다.
                    connection.inputStream,// 무엇을 읽을 것이냐? = > connection.inputStream을!
                    "UTF-8"
                //컴퓨터 와 컴퓨터 간에 통신에서는 사람의 언어가 아니라 바이트 형식으로 오고가기 때문에
                //이 구간에서는 사람이 읽을 수 있는 언어가 아님!
                )
            )
            buffer = reader.readLine() //줄을 읽겠다.  => 사람이 알아들을 수 있는 언어로 바꿔주겠다.
        }
        val data = Gson().fromJson(buffer, Array<PersonFromServer>::class.java) //String을 넣어주고 String을 부어넣을 틀을 넣어준다.
        // Expected BEGIN_OBJECT but was BEGIN_ARRAY at line 1 column 2 path $
        // 나는 객체를 기다리고 있었는데 어레이가 왔다라는 오류 문구이다.
        // PersonFromServer라는 클래스는 객체를 받는 클래스 인데, 네트워크에 있는 무수히 많은 객체를 받으려면
        //위와 같은 오류가 생긴다.
        // 즉, PersonFromServer라는 클래스를 Array 형식으로(List) 맏아야 하기 때문에 Array<>를 감싸준다.

        return null
    }

}
