package com.example.labratour.ui.login
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.labratour.HomeActivity
import com.example.labratour.R
import com.example.labratour.utils.ProgressBar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment(R.layout.fragment_login){
    val mypb : ProgressBar = ProgressBar()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // logging in!
        button_login_second.setOnClickListener {
            activity?.let { it1 ->
                mypb.showProgressBar(resources.getString(R.string.please_wait),
                    it1, view)
            }
            val email = login_edit_text_email.text.toString()
            val password = login_edit_text_password.text.toString()
            when {
                // check if fields are not empty
                TextUtils.isEmpty(email.trim { it <= ' ' }) -> {
                    Toast.makeText(
                        activity, "Please Enter Email!!", Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(password.trim { it <= ' ' }) -> {
                    Toast.makeText(
                        activity, "Please Enter Password!!", Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    // try to log in to the site
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                        // prepare args :)
                        val email : String = email.toString().trim { it <= ' ' }
                        val password : String = password.trim { it <= ' ' }
                        // login with firebase
                        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                            // if registration succeed
                            if (task.isSuccessful) {
                                mypb.hideProgressBar()
                                val firebaseUser: FirebaseUser = task.result!!.user!!
                                // intent to the home user activity and fragments! this is a crutial part! a lot of needs to be done
                                val intent =
                                    Intent(activity, HomeActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                intent.putExtra("user_id", FirebaseAuth.getInstance().currentUser!!.uid)
                                intent.putExtra("email_id", email)
                                startActivity(intent)
                                activity?.finish()
                            } else {

                                // login failed
                                mypb.hideProgressBar()
                                Toast.makeText(
                                    activity, task.exception!!.message.toString(), Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
            }
        }
    }
}