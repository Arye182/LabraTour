package com.example.labratour.presentation.ui.login

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.labratour.R
import com.example.labratour.presentation.LabratourApplication
import com.example.labratour.presentation.models.UserModel
import com.example.labratour.presentation.utils.Constants
import com.example.labratour.presentation.utils.ProgressBar
import com.example.labratour.presentation.viewmodel.UserAuthViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.SetOptions
import kotlinx.android.synthetic.main.fragment_signup_one.*

class SignUpFragmentOne : Fragment(R.layout.fragment_signup_one) {

    private val mypb: ProgressBar = ProgressBar()
    private lateinit var authViewModel: UserAuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // retrieve view model
        authViewModel = (activity as LoginActivity?)?.userAuthViewModel!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // onClickListeners
        button_signup_second.setOnClickListener { onClickSignUp(view) }
        log_in_clickable_text.setOnClickListener { moveTologIn(view) }
        // observing view model
        // this.authViewModel.signUpFirestoreTaskStatus.observe(viewLifecycleOwner, { onSignUpTaskStatusChanged(view) })
        this.authViewModel.registerNewUserTaskStatus.observe(viewLifecycleOwner, { onRegisterNewUserTaskStatusChanged(view) })
        // observe the view model state - is it loading? act accordingly
        this.authViewModel.isLoading.observe(viewLifecycleOwner, { onIsLoadingChanged(view) })
        // observe errors
        this.authViewModel.error.observe(viewLifecycleOwner, { onErrorChanged(view) })
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
//                Snackbar.make(view, R.string.registration_validated, Snackbar.LENGTH_SHORT)
//                    .setBackgroundTint(resources.getColor(R.color.success)).show()
                true
            }
        }
    }

    private fun moveTologIn(view: View) {
        val action = SignUpFragmentOneDirections.actionSignUpFragmentOneToLoginFragment()
        findNavController().navigate(action)
    }

    private fun onClickSignUp(view: View) {
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
                Log.i("Firebase", "Sign Up - Details Validated")
                // authViewModel.signUpToFireStore(email, password, first_name, last_name, user_name)
                Log.i("Firebase", "Sign Up - Fragment One - registering successful - registering new user to firebase Auth")
                authViewModel.registerNewUser(email, password)
            }
        }
    }

    private fun onRegisterNewUserTaskStatusChanged(view: View) {
        if (authViewModel.registerNewUserTaskStatus.value!!) {
            Log.i("Firebase", "Sign Up - Fragment One - registering successful - moving to fragment two")
            // move on to next step!
            val action = SignUpFragmentOneDirections.actionSignUpFragmentOneToSignUpFragmentTwo()
            findNavController().navigate(action)
        }
    }

//    private fun onSignUpTaskStatusChanged(view: View) {
//        if (authViewModel.signUpFirestoreTaskStatus.value!!) {
//            Log.i("Firebase", "Sign Up - Fragment One - sign up successful - registrating the user to cloud firestore")
//            registerUserToFireStore(authViewModel.userModel)
//            Log.i("Firebase", "Sign Up - Fragment One - sign up successful - moving to fragment two")
//            // move on to next step!
//            val action = SignUpFragmentOneDirections.actionSignUpFragmentOneToSignUpFragmentTwo()
//            findNavController().navigate(action)
//        }
//    }

    private fun onIsLoadingChanged(view: View) {
        if (authViewModel.isLoading.value!!) {
            activity?.let { it1 ->
                this.mypb.showProgressBar(
                    resources.getString(R.string.please_wait),
                    it1,
                    view
                )
            }
        } else if (!authViewModel.isLoading.value!!) {
            this.mypb.hideProgressBar()
        }
    }

    private fun onErrorChanged(view: View) {
        Snackbar.make(view, this.authViewModel.error.value.toString(), Snackbar.LENGTH_SHORT)
            .setBackgroundTint(resources.getColor(R.color.error)).show()
    }

    // TODO: this function is supposed to be in the data with usecase
//    private fun registerUserToFireStore(userModel: UserModel) {
//        Log.i("Firebase", "Sign Up - Fragment One - sign up successful - trying to register user to cloud....")
//        val firesStore = (((activity as LoginActivity).application) as LabratourApplication).appContainer.firebaseContainer.firebaseFirestore
//        firesStore.collection(Constants.USERS).document(userModel.id).set(userModel, SetOptions.merge()).addOnSuccessListener {
//            view?.let { it1 ->
//                Snackbar.make(it1, "user saved", Snackbar.LENGTH_SHORT)
//                    .setBackgroundTint(resources.getColor(R.color.success)).show()
//            }
//        }.addOnFailureListener {
//            view?.let { it1 ->
//                Snackbar.make(it1, "failed to save user", Snackbar.LENGTH_SHORT)
//                    .setBackgroundTint(resources.getColor(R.color.error)).show()
//            }
//        }
//    }
}
