package com.example.labratour.ui.login

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.labratour.R
import com.google.android.gms.tasks.OnCompleteListener
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_signup_one.*


class SignUpFragmentOne : Fragment(R.layout.fragment_signup_one){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // pressing continue on the sign up first window - will pass args to second fragment
        // and continue the registration process
        button_signup_second.setOnClickListener{

            // check if the user entered something :D
            when {
                TextUtils.isEmpty(sign_up_edit_text_email.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        activity, "Please Enter Email!!", Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(sign_up_edit_text_password.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        activity, "Please Enter Password!!", Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    // prepare args :)
                    val email : String = sign_up_edit_text_email.text.toString().trim { it <= ' ' }
                    val password : String = sign_up_edit_text_password.text.toString().trim { it <= ' ' }
                    // move on to next step!
                    val action = SignUpFragmentOneDirections.actionSignUpFragmentOneToSignUpFragmentTwo(email, password)
                    findNavController().navigate(action)
                }
            }
        }
    }
}