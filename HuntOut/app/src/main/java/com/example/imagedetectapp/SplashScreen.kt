package com.example.imagedetectapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.view.WindowManager.LayoutParams.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SplashScreen : AppCompatActivity() {

    val SPLASH_SCREEN = 3000
    private lateinit var topAnimation: Animation
    private lateinit var bottomAnimation: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

//        topAnimation = AnimationUtils.loadAnimation(this,R.anim.top_animation)
//
//        bottomAnimation = AnimationUtils.loadAnimation(this,R.anim.botton_animation)
//
//
//        var imageView = findViewById<ImageView>(R.id.SplashImage)
//
//        var appname_txt = findViewById<TextView>(R.id.appname)
//
//        var myname_txt = findViewById<TextView>(R.id.myname)
//
//        imageView.animation = bottomAnimation
//        appname_txt.animation = topAnimation
//        myname_txt.animation = bottomAnimation

//        Handler().postDelayed({
//
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//            finish()
//        }, SPLASH_SCREEN.toLong())

        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 4000) // 3000 is the delayed time in milliseconds.


    }
}
