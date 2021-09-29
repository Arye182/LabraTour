package com.example.labratour.presentation.ui.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.*
import com.example.labratour.HomeActivityNavGraphDirections
import com.example.labratour.R
import com.example.labratour.presentation.LabratourApplication
import com.example.labratour.presentation.di.AppContainer
import com.example.labratour.presentation.di.GooglePlacesContainer
import com.example.labratour.presentation.ui.base.BaseActivity
import com.example.labratour.presentation.viewmodel.LocationViewModel
import com.example.labratour.presentation.viewmodel.UserHomeViewModel
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : BaseActivity() {
    // data members
    lateinit var userHomeViewModel: UserHomeViewModel

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var appContainer: AppContainer
    private lateinit var googlePlacesContainer: GooglePlacesContainer
    lateinit var searchText: EditText
    lateinit var locationViewModel: LocationViewModel
    lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // arrange the activity instances like view model - using the dependency injection containers
        appContainer = (application as LabratourApplication).appContainer
        googlePlacesContainer = appContainer.googlePlacesContainer

        // view models
        val userDataViewModelFactory = googlePlacesContainer.userHomeViewModelFactory
        userHomeViewModel = ViewModelProvider(this, userDataViewModelFactory).get(UserHomeViewModel::class.java)
        locationViewModel = ViewModelProviders.of(this).get(LocationViewModel::class.java)

        // set view
        setContentView(R.layout.activity_home)

        //
        searchText = edit_text_place_to_search

        // navigation container
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.home_nav_host_fragment) as NavHostFragment
        navController = navHostFragment.findNavController()
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.homeFragment, R.id.searchFragment, R.id.profileFragment, R.id.settingsFragment), home_draw_layout
        )

        // toolbar
        setSupportActionBar(toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)

        // bottom nav bar
        bottom_nav.setupWithNavController(navController)
        drawer_home.setupWithNavController(navController)

        // Lookup navigation view
        navigationView = findViewById<View>(R.id.drawer_home) as NavigationView
        // Inflate the header view at runtime
        navigationView.inflateHeaderView(R.layout.header_navigation_drawer)
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
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
