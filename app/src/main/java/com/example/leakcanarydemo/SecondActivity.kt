package com.example.leakcanarydemo

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import leakcanary.AppWatcher
import leakcanary.LeakCanary

class SecondActivity : AppCompatActivity() {

    var mCount=0

    override fun onCreate(savedInstanceState: Bundle?) {
        println("SecondActivity#onCreate")
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_second)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var count=0

        /*
        * 可以手动监控任何对象。
        * 从开始监控算起，一段时间后（默认5秒），如果该对象还未被释放则告警。
        * 所以一般应该在onDestroy里面watch
        * */
//        AppWatcher.objectWatcher.watch(count, "count was detached")
//
        /*
        *  默认leakcanary会监控Activity，不需要手动添加监控。
        * */
//        AppWatcher.objectWatcher.watch(this, "SecondActivity was detached")

        val handler = Handler(Looper.getMainLooper())

        val runable = object:Runnable{
            override fun run() {
                /*
                * 这里定义了一个匿名内部类。会持有一个SecondActivity的引用this$0。
                * */

                // 内部需要使用到SecondActivity对象（或其成员）才能导致SecondActivity对象无法及时释放。
                printHello() // 这一行如果注释掉不会触发leakcanary告警。因为没有引用到SecondActivity的任何成员。
                            // 可以通过"adb shell dumpsys meminfo com.example.leakcanarydemo"查看实际的activity数量，就可以观察到是否泄漏了。
//                println("SecondActivity#dosomething ${count++}")

                handler.postDelayed(this, 5000)
            }
        }

        handler.postDelayed(runable, 5000)

//        val thr = object : Thread() {
//            override fun run() {
//                sleep(60*1000)
//            }
//        }
//        thr.start()
//
//        AppWatcher.objectWatcher.watch(thr, "thr was detached")
    }


    private fun printHello(){
        println("hello!")
    }

    override fun onDestroy() {
        println("SecondActivity#onDestroy")
        super.onDestroy()
    }
}