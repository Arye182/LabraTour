package com.example.labratour.presentation.ui.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.labratour.HomeActivityNavGraphDirections
import com.example.labratour.R
import com.example.labratour.presentation.LabratourApplication
import com.example.labratour.presentation.di.AppContainer
import com.example.labratour.presentation.di.FirebaseContainer
import com.example.labratour.presentation.ui.base.BaseActivity
import com.example.labratour.presentation.viewmodel.UserHomeViewModel
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : BaseActivity() {
    // data members
    lateinit var userHomeViewModel: UserHomeViewModel
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var appContainer: AppContainer
    private lateinit var firebaseContainer: FirebaseContainer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // arrange the activity instances like view model - using the dependency injection containers
        appContainer = (application as LabratourApplication).appContainer
        firebaseContainer = appContainer.firebaseContainer
        val userDataViewModelFactory = firebaseContainer.userDataViewModelFactory
        userHomeViewModel = ViewModelProvider(this, userDataViewModelFactory).get(UserHomeViewModel::class.java)
        setContentView(R.layout.activity_home)
        // navigation container
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.home_nav_host_fragment) as NavHostFragment
        navController = navHostFragment.findNavController()
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.homeFragment, R.id.searchFragment, R.id.profileFragment, R.id.settingsFragment)
        )
        // toolbar
        setSupportActionBar(toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)
        // bottom nav bar
        bottom_nav.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_activity_options_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.termsAndConditions) {
            val action = HomeActivityNavGraphDirections.actionGlobalTermsFragment()
            navController.navigate(action)
            true
        } else {
            item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
