package com.example.labratour.ui
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.labratour.R
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment(R.layout.fragment_login){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // logging in!
        button_login_second.setOnClickListener {
            val username = edit_text_username.text.toString()
            val password = edit_text_password.text.toString()
            val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment(username, password)
            findNavController().navigate(action)
        }
    }
}