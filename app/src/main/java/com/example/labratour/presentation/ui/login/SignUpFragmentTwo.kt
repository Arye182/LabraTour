package com.example.labratour.presentation.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.labratour.R
import com.example.labratour.presentation.ui.home.HomeActivity
import com.example.labratour.presentation.utils.ProgressBar
import kotlinx.android.synthetic.main.fragment_signup_two.*

class SignUpFragmentTwo : Fragment(R.layout.fragment_signup_two) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mypb: ProgressBar = ProgressBar()
        // create firebase instance and try register the user
        button_finish_registration.setOnClickListener {
            val intent =
                Intent(activity, HomeActivity::class.java)
            intent.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            activity?.finish()
        }
    }
}
