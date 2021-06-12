package com.example.labratour.ui.login.login
import com.utils.JobExecutor
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.labratour.ui.home.HomeActivity
import com.example.labratour.R
import com.example.labratour.utils.UIThread
import com.example.labratour.utils.ProgressBar
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_login.*
import com.repositories.UserRepositoryImpl
import com.example.useCases.LogInUseCase
import com.datasource.CloudUserDataSource
import com.datasource.UserDataSourceFactory

class LoginFragment : Fragment(R.layout.fragment_login){

    private val mypb : ProgressBar = ProgressBar()
    private lateinit var viewModel: LoginFragmentViewModel
    private lateinit var email : String
    private lateinit var password: String
    private lateinit var thread : UIThread


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // create the view model for login fragment manualy with factory - we do that in OnCreate Method
        thread = UIThread()
        val data : CloudUserDataSource = UserDataSourceFactory(
            FirebaseAuth.getInstance()
        ).createCloudDataSource()
        val userRepo : UserRepositoryImpl = UserRepositoryImpl(data)
        val useCase : LogInUseCase = LogInUseCase(userRepo,
            JobExecutor(), UIThread()
        )
        val viewModelFactory = LoginFragmentViewModelFactory(useCase)
        viewModel = ViewModelProvider(this, viewModelFactory).get(LoginFragmentViewModel::class.java)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // logging in!
        button_login_second.setOnClickListener {login(view)}
        register_clickable_text.setOnClickListener{onClickRegister()}
        forgot_password.setOnClickListener{onClickForgotPassword()}
        this.viewModel.isLoading.observe(viewLifecycleOwner,{showProgressBar(view)})
        this.viewModel.success.observe(viewLifecycleOwner, {loginResult(view)})
    }

    private fun onClickForgotPassword() {
        val action = LoginFragmentDirections.actionLoginFragmentToForgotPasswordFragment()
        findNavController().navigate(action)
    }

    private fun onClickRegister() {
        val action = LoginFragmentDirections.actionLoginFragmentToSignUpFragmentOne()
        findNavController().navigate(action)
    }

    private fun showProgressBar(view: View){
        if (viewModel.isLoading.value!!) {
            activity?.let { it1 -> this.mypb.showProgressBar(resources.getString(R.string.please_wait), it1, view)}
        } else if (!viewModel.isLoading.value!!) {
            this.mypb.hideProgressBar()
        }
    }

    private fun loginResult(view: View) {
        if (viewModel.success.value!!) {
            // intent to the home user activity and fragments! this is a crucial part! a lot of needs to be done
            val intent = Intent(activity, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent.putExtra("user_id", FirebaseAuth.getInstance().currentUser!!.uid)
            intent.putExtra("email_id", email)
            startActivity(intent)
            activity?.finish()
        } else if (!viewModel.success.value!!) {
            Snackbar.make(view, viewModel.error.value.toString(), Snackbar.LENGTH_LONG).setBackgroundTint(resources.getColor(R.color.error)).show()
        }
    }

    private fun login (view: View) {
        val email = login_edit_text_email.text.toString()
        val password = login_edit_text_password.text.toString()
        this.email = email
        this.password = password
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