package com.example.labratour.presentation.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.labratour.R
import com.example.labratour.presentation.LabratourApplication
import com.example.labratour.presentation.di.AppContainer
import com.example.labratour.presentation.di.FirebaseContainer
import com.example.labratour.presentation.ui.home.HomeActivity
import com.example.labratour.presentation.viewmodel.UserAuthViewModel
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    // data members 
    private lateinit var navController: NavController
    lateinit var userAuthViewModel: UserAuthViewModel
    private lateinit var appContainer: AppContainer
    private lateinit var firebaseContainer: FirebaseContainer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // arrange the activity instances like view model - using the dependency injection containers
        appContainer = (application as LabratourApplication).appContainer
        firebaseContainer = appContainer.firebaseContainer
        val userAuthViewModelFactory = firebaseContainer.userAuthViewModelFactory
        userAuthViewModel = ViewModelProvider(this, userAuthViewModelFactory).get(UserAuthViewModel::class.java)

        // check if user is logged in already
        val auth = FirebaseAuth.getInstance()
        if (auth.getCurrentUser() != null) {
            val intent =
                Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent.putExtra("user_id", FirebaseAuth.getInstance().currentUser!!.uid)
            intent.putExtra("email_id", FirebaseAuth.getInstance().currentUser!!.email)
            startActivity(intent)
            this.finish()
        }

        setContentView(R.layout.activity_login)
        // navigation container
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.login_nav_host_fragment) as NavHostFragment
        navController = navHostFragment.findNavController()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
