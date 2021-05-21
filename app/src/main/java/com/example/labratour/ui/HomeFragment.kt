package com.example.labratour.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.labratour.R
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment  : Fragment(R.layout.fragment_home) {

    //private val args : HomeFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // check if args are not empty

//        if (args != null) {
//            home_password_text.text = args.password
//            home_user_text.text = args.username
//        }
    }
}