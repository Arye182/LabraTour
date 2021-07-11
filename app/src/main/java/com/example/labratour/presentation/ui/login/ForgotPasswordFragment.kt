package com.example.labratour.presentation.ui.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.labratour.R
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_forgot_password.*

class ForgotPasswordFragment : Fragment(R.layout.fragment_forgot_password) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button_submit.setOnClickListener { submit(view) }
    }

    private fun submit(view: View) {

        // check email input
        val email: String = email_forgot_password_edit_text_email.text.toString().trim { it <= ' ' }
        // check if email is empty
        if (email.isEmpty()) {
            Snackbar.make(view, R.string.missing_email, Snackbar.LENGTH_SHORT)
                .setBackgroundTint(resources.getColor(R.color.error)).show()
        } else {
            // show progress bar
            (activity as LoginActivity).showProgressBar("Sending Reset Password Email...")
        }
        FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener { task ->
            (activity as LoginActivity).hideProgressBar()
            if (task.isSuccessful) {
                Snackbar.make(view, "Email Sent Successfully", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(resources.getColor(R.color.success)).show()
                val action =
                    ForgotPasswordFragmentDirections.actionForgotPasswordFragmentToLoginFragment()
                findNavController().navigate(action)
            } else {
                Snackbar.make(view, task.exception!!.message.toString(), Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(resources.getColor(R.color.error)).show()
            }
        }
    }
}
