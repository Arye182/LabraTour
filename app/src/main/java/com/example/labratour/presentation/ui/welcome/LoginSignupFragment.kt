package com.example.labratour.presentation.ui.welcome
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.labratour.R
import kotlinx.android.synthetic.main.fragment_login_signup.*

class LoginSignupFragment : Fragment(R.layout.fragment_login_signup) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button_login.setOnClickListener {
            val action = LoginSignupFragmentDirections.actionLoginSignupFragmentToLoginFragment()
            findNavController().navigate(action)
        }

        button_signup.setOnClickListener {
            val action = LoginSignupFragmentDirections.actionLoginSignupFragmentToSignUpFragmentOne()
            findNavController().navigate(action)
        }
    }
}
