package com.example.labratour.presentation.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.labratour.R
import com.example.labratour.presentation.ui.home.HomeActivity
import com.example.labratour.presentation.utils.ProgressBar
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.fragment_signup_two.*

class SignUpFragmentTwo : Fragment(R.layout.fragment_signup_two) {

    private val args: SignUpFragmentTwoArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mypb: ProgressBar = ProgressBar()
        // create firebase instance and try register the user
        button_finish_registration.setOnClickListener {
            activity?.let { it1 ->
                mypb.showProgressBar(
                    resources.getString(R.string.please_wait),
                    it1, view
                )
            }
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(args.email, args.password)
                .addOnCompleteListener { task ->
                    // if registration succeede
                    if (task.isSuccessful) {
                        val firebaseUser: FirebaseUser = task.result!!.user!!
                        mypb.hideProgressBar()
                        Snackbar.make(view, R.string.registration_success, Snackbar.LENGTH_SHORT)
                            .setBackgroundTint(resources.getColor(R.color.success)).show()
                        // intent to the home user activity and fragments! this is a crutial part! a lot of needs to be done
                        val intent =
                            Intent(activity, HomeActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        intent.putExtra("user_id", firebaseUser.uid)
                        intent.putExtra("email_id", args.email)
                        startActivity(intent)
                        activity?.finish()
                    } else {
                        // registration failed
                        Snackbar.make(
                            view,
                            task.exception!!.message.toString(),
                            Snackbar.LENGTH_SHORT
                        ).setBackgroundTint(resources.getColor(R.color.error)).show()
                    }
                }
        }
    }
}
