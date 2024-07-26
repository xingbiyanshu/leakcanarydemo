package com.example.leakcanarydemo

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    var mCount=0
    override fun onCreate(savedInstanceState: Bundle?) {
        println("MainActivity#onCreate")
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        Handler(Looper.getMainLooper()).postDelayed( {
            startActivity(Intent(this, SecondActivity::class.java))
        }, 3000)

//        val handler = Handler(Looper.getMainLooper())
//
//        val runable = object:Runnable{
//            override fun run() {
//                println("SecondActivity#dosomething ${mCount++}")
//                handler.postDelayed(this, 5000)
//            }
//        }
//
//        handler.postDelayed(runable, 5000)

    }

    override fun onDestroy() {
        println("MainActivity#onDestroy")
        super.onDestroy()
    }
}