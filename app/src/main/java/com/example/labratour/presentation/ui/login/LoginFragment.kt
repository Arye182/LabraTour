package com.example.labratour.presentation.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.labratour.R
import com.example.labratour.presentation.ui.base.BaseFragment
import com.example.labratour.presentation.ui.home.HomeActivity
import com.example.labratour.presentation.viewmodel.UserAuthViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_login.*

/**
 * Login fragment
 *
 * @constructor Create empty Login fragment
 */
class LoginFragment : BaseFragment(R.layout.fragment_login) {
    private lateinit var authViewModel: UserAuthViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // create the view model for login fragment manually with factory - we do that in OnCreate Method
        authViewModel = (activity as LoginActivity?)?.userAuthViewModel!!
    }

    /**
     * On view created
     *
     * @param view
     * @param savedInstanceState
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // setting up listeners and observes
        button_login_second.setOnClickListener { onClickLogin(view) }
        register_clickable_text.setOnClickListener { onClickRegister() }
        forgot_password.setOnClickListener { onClickForgotPassword() }
        // observe the view model state - is it loading? act accordingly
        this.authViewModel.isLoading.observe(viewLifecycleOwner, { onIsLoadingChanged(view) })
        // observe if success is true - logging in was successful - move to next activity
        this.authViewModel.logInTaskStatus.observe(viewLifecycleOwner, { onIsLoginSuccessChanged(view) })
        // observe errors
        this.authViewModel.error.observe(viewLifecycleOwner, { onErrorChanged(view) })
    }
    // ----------------------------------- On CLicks ----------------------------------------------
    /**
     * On click forgot password
     *
     */
    private fun onClickForgotPassword() {
        val action = LoginFragmentDirections.actionLoginFragmentToForgotPasswordFragment()
        findNavController().navigate(action)
    }

    /**
     * On click register
     *
     */
    private fun onClickRegister() {
        val action = LoginFragmentDirections.actionLoginFragmentToSignUpFragmentOne()
        findNavController().navigate(action)
    }

    /**
     * Login
     *
     * @param view
     */
    private fun onClickLogin(view: View) {
        val email = login_edit_text_email.text.toString().trim { it <= ' ' }
        val password = login_edit_text_password.text.toString().trim { it <= ' ' }
        when {
            // check if fields are not empty
            TextUtils.isEmpty(email) -> {
                Snackbar.make(view, R.string.missing_email, Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(resources.getColor(R.color.error)).show()
            }
            TextUtils.isEmpty(password) -> {
                Snackbar.make(view, R.string.missing_password, Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(resources.getColor(R.color.error)).show()
            }
            else -> {
                // try to log in to the site
                authViewModel.login(email, password)
            }
        }
    }

    // ----------------------------------- State Handlers -----------------------------------------
    /**
     * Handle progress bar
     *
     * @param view
     */
    private fun onIsLoadingChanged(view: View) {
        if (authViewModel.isLoading.value!!) {
            (activity as LoginActivity).showProgressBar("Logging In...")
        } else if (!authViewModel.isLoading.value!!) {
            (activity as LoginActivity).hideProgressBar()
        }
    }

    /**
     * Login result
     *
     * @param view
     */
    private fun onIsLoginSuccessChanged(view: View) {
        if (authViewModel.logInTaskStatus.value!!) {
            // intent to the home user activity and fragments! this is a crucial part! a lot of needs to be done
            val email = login_edit_text_email.text.toString()
            val password = login_edit_text_password.text.toString()
            val intent = Intent(activity, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent.putExtra("user_id", FirebaseAuth.getInstance().currentUser!!.uid)
            intent.putExtra("email_id", email)
            startActivity(intent)
            (activity as LoginActivity).hideProgressBar()
            activity?.finish()
        }
    }

    private fun onErrorChanged(view: View) {
        Snackbar.make(view, this.authViewModel.error.value.toString(), Snackbar.LENGTH_SHORT)
            .setBackgroundTint(resources.getColor(R.color.error)).show()
    }
}
