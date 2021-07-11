package com.example.labratour.presentation.ui.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.labratour.R
import com.example.labratour.presentation.viewmodel.UserAuthViewModel
import kotlinx.android.synthetic.main.fragment_signup_two.*

class SignUpFragmentTwo : Fragment(R.layout.fragment_signup_two) {
    private lateinit var authViewModel: UserAuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // retrieve view model
        authViewModel = (activity as LoginActivity?)?.userAuthViewModel!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button_finish_registration.setOnClickListener {
            onIsLoadingChanged(view)
            val action = SignUpFragmentTwoDirections.actionSignUpFragmentTwoToLoginSignupFragment()
            findNavController().navigate(action)
        }
    }

    private fun onIsLoadingChanged(view: View) {
        if (authViewModel.isLoading.value!!) {
            (activity as LoginActivity).showProgressBar(resources.getString(R.string.please_wait))
        } else if (!authViewModel.isLoading.value!!) {
            (activity as LoginActivity).hideProgressBar()
        }
    }
}
