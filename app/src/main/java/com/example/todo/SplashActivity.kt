package com.example.todo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import com.example.todo.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySplashBinding
    private lateinit var anim : Animation
    private val SPLASH_DISPLAY_LENGTH = 2090
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_splash)
        anim = AnimationUtils.loadAnimation(this,R.anim.alpha)
        anim.reset()
        binding.toDoSplash.clearAnimation()
        anim = AnimationUtils.loadAnimation(this,R.anim.translate)
        anim.reset()
        binding.welcomeText.clearAnimation()
        binding.welcomeText.startAnimation(anim)

        Handler().postDelayed(object : Runnable{
            override fun run() {
                val intent = Intent(this@SplashActivity, ToDoListActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
                startActivity(intent)
                this@SplashActivity.finish()
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
        }, SPLASH_DISPLAY_LENGTH.toLong())
    }
}