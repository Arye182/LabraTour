package com.example.labratour.ui

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.example.labratour.LoginActivity
import com.example.labratour.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        Handler().postDelayed(
            {
                // launch main activity
                startActivity(Intent(this, LoginActivity::class.java))
                // close splash activity
                finish()
            },
            1500
        )

    }
}