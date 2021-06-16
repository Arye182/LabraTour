package com.example.labratour.presentation.ui.login.login

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.labratour.R
import com.example.labratour.presentation.LabratourApplication
import com.example.labratour.presentation.ui.home.HomeActivity
import com.example.labratour.presentation.ui.login.LoginActivity
import com.example.labratour.presentation.utils.ProgressBar
import com.example.labratour.presentation.viewmodel.UserViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_login.*

/**
 * Login fragment
 *
 * @constructor Create empty Login fragment
 */
class LoginFragment : Fragment(R.layout.fragment_login) {
    private val mypb: ProgressBar = ProgressBar()
    private lateinit var viewModel: UserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // create the view model for login fragment manually with factory - we do that in OnCreate Method
        viewModel = (activity as LoginActivity?)?.userViewModel!!
        val k = ((LabratourApplication.instance) as LabratourApplication).bla()
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
        button_login_second.setOnClickListener { login(view) }
        register_clickable_text.setOnClickListener { onClickRegister() }
        forgot_password.setOnClickListener { onClickForgotPassword() }
        // observe the view model state - is it loading? act accordingly
        this.viewModel.isLoading.observe(viewLifecycleOwner, { handleProgressBar(view) })
        // observe if success is true - logging in was successful - move to next activity
        this.viewModel.success.observe(viewLifecycleOwner, { loginResult(view) })
    }

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
     * Handle progress bar
     *
     * @param view
     */
    private fun handleProgressBar(view: View) {
        if (viewModel.isLoading.value!!) {
            activity?.let { it1 ->
                this.mypb.showProgressBar(
                    resources.getString(R.string.please_wait),
                    it1,
                    view
                )
            }
        } else if (!viewModel.isLoading.value!!) {
            this.mypb.hideProgressBar()
        }
    }

    /**
     * Login result
     *
     * @param view
     */
    private fun loginResult(view: View) {
        if (viewModel.success.value!!) {
            // intent to the home user activity and fragments! this is a crucial part! a lot of needs to be done
            val email = login_edit_text_email.text.toString()
            val password = login_edit_text_password.text.toString()
            val intent = Intent(activity, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent.putExtra("user_id", FirebaseAuth.getInstance().currentUser!!.uid)
            intent.putExtra("email_id", email)
            startActivity(intent)
            activity?.finish()
        } else if (!viewModel.success.value!!) {
            Snackbar.make(view, viewModel.error.value.toString(), Snackbar.LENGTH_LONG)
                .setBackgroundTint(resources.getColor(R.color.error)).show()
        }
    }

    /**
     * Login
     *
     * @param view
     */
    private fun login(view: View) {
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
                viewModel.login(email, password)
            }
        }
    }
}
