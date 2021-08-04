package com.example.labratour.presentation.ui.welcome

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.labratour.R
import com.example.labratour.presentation.ui.login.LoginActivity

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
