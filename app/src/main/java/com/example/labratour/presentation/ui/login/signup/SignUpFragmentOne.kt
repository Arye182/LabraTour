package com.example.labratour.presentation.ui.login.signup

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.labratour.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_signup_one.*

class SignUpFragmentOne : Fragment(R.layout.fragment_signup_one) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // onClickListeners
        button_signup_second.setOnClickListener { moveToSecondRegistrationPage(view) }
        log_in_clickable_text.setOnClickListener { moveTologIn(view) }
    }

    private fun validateRegisterDetails(view: View): Boolean {
        return when {
            // check first name not empty
            TextUtils.isEmpty(sign_up_edit_text_first_name.text.toString().trim { it <= ' ' }) -> {
                Snackbar.make(view, R.string.missing_first_name, Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(resources.getColor(R.color.error)).show()
                false
            }
            // check last name not empty
            TextUtils.isEmpty(sign_up_edit_text_Last_name.text.toString().trim { it <= ' ' }) -> {
                Snackbar.make(view, R.string.missing_last_name, Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(resources.getColor(R.color.error)).show()
                false
            }
            // check user name not empty
            TextUtils.isEmpty(sign_up_edit_text_user_name.text.toString().trim { it <= ' ' }) -> {
                Snackbar.make(view, R.string.missing_user_name, Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(resources.getColor(R.color.error)).show()
                false
            }
            // check email not empty
            TextUtils.isEmpty(sign_up_edit_text_email.text.toString().trim { it <= ' ' }) -> {
                Snackbar.make(view, R.string.missing_email, Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(resources.getColor(R.color.error)).show()
                false
            }
            // check password not empty
            TextUtils.isEmpty(sign_up_edit_text_password.text.toString().trim { it <= ' ' }) -> {
                Snackbar.make(view, R.string.missing_password, Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(resources.getColor(R.color.error)).show()
                false
            }
            // check confirmation password not empty
            TextUtils.isEmpty(
                sign_up_edit_text_password_confirmation.text.toString().trim { it <= ' ' }
            ) -> {
                Snackbar.make(
                    view,
                    R.string.missing_confirmation_password,
                    Snackbar.LENGTH_SHORT
                ).setBackgroundTint(resources.getColor(R.color.error)).show()
                false
            }
            // check passwords
            sign_up_edit_text_password.text.toString()
                .trim { it <= ' ' } != sign_up_edit_text_password_confirmation.text.toString()
                .trim { it <= ' ' } -> {
                Snackbar.make(
                    view,
                    R.string.password_confirmation_failed,
                    Snackbar.LENGTH_SHORT
                ).setBackgroundTint(resources.getColor(R.color.error)).show()
                false
            }
            else -> {
                Snackbar.make(view, R.string.registration_validated, Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(resources.getColor(R.color.success)).show()
                true
            }
        }
    }

    private fun moveToSecondRegistrationPage(view: View) {
        // check if the user entered something :D
        when {
            validateRegisterDetails(view) -> {
                val first_name: String =
                    sign_up_edit_text_first_name.text.toString().trim { it <= ' ' }
                val last_name: String =
                    sign_up_edit_text_Last_name.text.toString().trim { it <= ' ' }
                val user_name: String =
                    sign_up_edit_text_user_name.text.toString().trim { it <= ' ' }
                val email: String = sign_up_edit_text_email.text.toString().trim { it <= ' ' }
                val password: String = sign_up_edit_text_password.text.toString().trim { it <= ' ' }
                // move on to next step!
                val action = SignUpFragmentOneDirections.actionSignUpFragmentOneToSignUpFragmentTwo(
                    first_name,
                    last_name,
                    user_name,
                    email,
                    password
                )
                findNavController().navigate(action)
            }
        }
    }

    private fun moveTologIn(view: View) {
        val action = SignUpFragmentOneDirections.actionSignUpFragmentOneToLoginFragment()
        findNavController().navigate(action)
    }
}
