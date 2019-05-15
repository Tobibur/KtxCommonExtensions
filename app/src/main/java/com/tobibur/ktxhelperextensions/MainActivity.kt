package com.tobibur.ktxhelperextensions

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.tobibur.ktxcommonextensions.addJWT
import com.tobibur.ktxcommonextensions.capitalizeAll

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val str = "djshdsd".addJWT()
        Log.d("MainActivity", str.capitalizeAll())
    }
}
