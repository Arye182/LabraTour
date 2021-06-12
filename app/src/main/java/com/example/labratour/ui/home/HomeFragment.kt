package com.example.labratour.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.labratour.ui.login.LoginActivity
import com.example.labratour.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment  : Fragment(R.layout.fragment_home) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // check if args are not empty
        // args from registration
        val userId = activity?.intent?.getStringExtra("user_id")
        val emailId = activity?.intent?.getStringExtra("email_id")

        home_user_text.text = "User ID :: $userId"
        home_password_text.text = "Email ID :: $emailId"

        button_logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(activity, LoginActivity::class.java))
            activity?.finish()
        }
    }
}