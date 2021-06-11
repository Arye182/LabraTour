package com.example.labratour.ui.login
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.labratour.HomeActivity
import com.example.labratour.R
import com.example.labratour.utils.ProgressBar
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.fragment_login.*

import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment(R.layout.fragment_login){

    private val mypb : ProgressBar = ProgressBar()
    private val viewModel: LoginFragmentViewModel by viewModels()
    val email = login_edit_text_email.text.toString()
    val password = login_edit_text_password.text.toString()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // logging in!
        button_login_second.setOnClickListener {login(view)}
        register_clickable_text.setOnClickListener{register()}
        forgot_password.setOnClickListener{forgotPassword()}
        viewModel.isLoading.observe(viewLifecycleOwner,{showProgressBar(view)})
        viewModel.success.observe(viewLifecycleOwner, {loginResult(view)})
    }

    private fun forgotPassword() {
        val action = LoginFragmentDirections.actionLoginFragmentToForgotPasswordFragment()
        findNavController().navigate(action)
    }

    private fun register() {
        val action = LoginFragmentDirections.actionLoginFragmentToSignUpFragmentOne()
        findNavController().navigate(action)
    }

    private fun showProgressBar(view: View){
        if (viewModel.isLoading.value!! == true) {
            activity?.let { it1 -> mypb.showProgressBar(resources.getString(R.string.please_wait), it1, view)}
        } else if (viewModel.isLoading.value!! == false) {
            mypb.hideProgressBar()
        }
    }

    private fun loginResult(view: View) {
        if (viewModel.success.value!! == true) {
            // intent to the home user activity and fragments! this is a crutial part! a lot of needs to be done
            val intent = Intent(activity, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent.putExtra("user_id", FirebaseAuth.getInstance().currentUser!!.uid)
            intent.putExtra("email_id", email)
            startActivity(intent)
            activity?.finish()
        } else if (viewModel.success.value!! == false) {
            Snackbar.make(view, viewModel.error.value.toString(), Snackbar.LENGTH_LONG).setBackgroundTint(resources.getColor(R.color.error)).show()
        }
    }

    private fun login (view: View) {
        when {
            // check if fields are not empty
            TextUtils.isEmpty(email.trim { it <= ' ' }) -> {
                Snackbar.make(view, R.string.missing_email, Snackbar.LENGTH_SHORT).setBackgroundTint(resources.getColor(R.color.error)).show()
            }
            TextUtils.isEmpty(password.trim { it <= ' ' }) -> {
                Snackbar.make(view, R.string.missing_password, Snackbar.LENGTH_SHORT).setBackgroundTint(resources.getColor(R.color.error)).show()
            }
            else -> {
                // try to log in to the site
                // prepare args :)
                val email : String = email.toString().trim { it <= ' ' }
                val password : String = password.trim { it <= ' ' }
                // login with firebase
                viewModel.login(email, password)
            }
        }
    }
}