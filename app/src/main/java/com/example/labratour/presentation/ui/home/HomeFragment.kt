package com.example.labratour.presentation.ui.home

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.labratour.R
import com.example.labratour.presentation.model.data.PlaceModel
import com.example.labratour.presentation.ui.adapters.SmallPlaceCardRecyclerAdapter
import com.example.labratour.presentation.utils.GpsUtils
import com.example.labratour.presentation.viewmodel.LocationViewModel
import com.example.labratour.presentation.viewmodel.UserHomeViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.customed_places_list_progress_bar
import kotlinx.android.synthetic.main.fragment_home.customed_places_recycler_view
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.location_card.view.*

const val LOCATION_REQUEST = 100
const val GPS_REQUEST = 101

class HomeFragment : Fragment(R.layout.fragment_home), SmallPlaceCardRecyclerAdapter.OnItemClickListener {

    private var customPlacesLoaded: Boolean = false
    private lateinit var homeViewModel: UserHomeViewModel
    private lateinit var locationViewModel: LocationViewModel
    private var isGPSEnabled = false
    private var nearByPlacesLoaded = false
    private lateinit var pullToRefresh: SwipeRefreshLayout

    // --------------------------------- fragment functions ---------------------------------------
    override fun onStart() {
        super.onStart()
        invokeLocationAction()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.homeViewModel = (activity as HomeActivity?)?.userHomeViewModel!!
        this.locationViewModel = (activity as HomeActivity?)?.locationViewModel!!
        checkGps()
        invokeLocationAction()
        this.homeViewModel.getAllPlacesLists()
    }

    override fun onPause() {
        super.onPause()
        Log.i("Places", "HomeFragment onPause")
    }

    override fun onResume() {
        super.onResume()
        Log.i("Places", "HomeFragment onResume")
        if (!nearByPlacesLoaded) {
            nearby_places_list_progress_bar.visibility = View.VISIBLE
        }
        if (!customPlacesLoaded) {
            customed_places_list_progress_bar.visibility = View.VISIBLE
        }
        return
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // view models observation
        // this.homeViewModel.nearByPlacesListTest.observe(viewLifecycleOwner, { onNearByPlacesListChanged(view) })
        this.locationViewModel.getLocationData()
            .observe(viewLifecycleOwner, { startLocationUpdate(view) })
        this.homeViewModel.nearByPlacesList.observe(
            viewLifecycleOwner,
            { onNearByPlacesListChanged(view) }
        )
        this.homeViewModel.nearByPlacesListTest.observe(
            viewLifecycleOwner,
            { onCustomPlacesListChanged(view) }
        )
        // pull to refresh
        pullToRefresh = home_refresh_layout
        setPullToRefreshListener()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        this.homeViewModel.nearByPlacesList.value?.clear()
        this.homeViewModel.nearByPlacesListTest.value?.clear()
        Log.i("Places", "HomeFragment onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        // in case we destroy the frasgent we want to clear the list
        this.homeViewModel.nearByPlacesList.value?.clear()
        this.homeViewModel.nearByPlacesListTest.value?.clear()
        Log.i("Places", "HomeFragment onDestroy")
    }

    private fun setPullToRefreshListener() {
        pullToRefresh.setOnRefreshListener {
            // update lists!
            this.homeViewModel.nearByPlacesListTest.value?.clear()
            this.homeViewModel.nearByPlacesList.value?.clear()
            this.homeViewModel.getAllPlacesLists()
            checkGps()
            invokeLocationAction()
            places_close_to_you_recycler_view.visibility = View.GONE
            customed_places_recycler_view.visibility = View.GONE
            nearby_places_list_progress_bar.visibility = View.VISIBLE
            customed_places_list_progress_bar.visibility = View.VISIBLE
            nearByPlacesLoaded = false
            customPlacesLoaded = false
            pullToRefresh.isRefreshing = false
        }
    }

    // ----------------------------- On Live Data Changed Handlers --------------------------------
    // update the recycler view when list of nearby places is ready!
    private fun onNearByPlacesListChanged(view: View) {
        places_close_to_you_recycler_view.adapter = this.homeViewModel.nearByPlacesList.value?.let {
            SmallPlaceCardRecyclerAdapter(
                it, this
            )
        }
        places_close_to_you_recycler_view.layoutManager =
            LinearLayoutManager(activity as HomeActivity, LinearLayoutManager.HORIZONTAL, false)
        places_close_to_you_recycler_view.setHasFixedSize(true)
        nearby_places_list_progress_bar.visibility = View.GONE
        places_close_to_you_recycler_view.visibility = View.VISIBLE
        this.nearByPlacesLoaded = true
    }

    // update the recycler view when list of custom places is ready!
    private fun onCustomPlacesListChanged(view: View) {
        customed_places_recycler_view.adapter = this.homeViewModel.nearByPlacesListTest.value?.let {
            SmallPlaceCardRecyclerAdapter(
                it, this
            )
        }
        customed_places_recycler_view.layoutManager =
            LinearLayoutManager(activity as HomeActivity, LinearLayoutManager.HORIZONTAL, false)
        customed_places_recycler_view.setHasFixedSize(true)
        customed_places_list_progress_bar.visibility = View.GONE
        customed_places_recycler_view.visibility = View.VISIBLE
        this.customPlacesLoaded = true
    }

    // ---------------------------------------- gps -----------------------------------------------
    fun checkGps() {
        // gps
        GpsUtils(activity as HomeActivity).turnGPSOn(object : GpsUtils.OnGpsListener {
            override fun gpsStatus(isGPSEnable: Boolean) {
                isGPSEnabled = isGPSEnable
            }
        })
    }

    private fun startLocationUpdate(view: View) {
        location_card.location_ltlng_txt.text = getString(
            R.string.latLong,
            locationViewModel.getLocationData().value?.latitude,
            locationViewModel.getLocationData().value?.longitude
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == GPS_REQUEST) {
                isGPSEnabled = true
                invokeLocationAction()
            }
        }
    }

    private fun invokeLocationAction() {
        when {
            !isGPSEnabled -> location_card.location_ltlng_txt.text = getString(R.string.enable_gps)

            isPermissionsGranted() -> view?.let { startLocationUpdate(it) }

            shouldShowRequestPermissionRationale() ->
                location_card.location_ltlng_txt.text =
                    getString(R.string.permission_request)

            else -> ActivityCompat.requestPermissions(
                (activity as HomeActivity),
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                LOCATION_REQUEST
            )
        }
    }

    private fun isPermissionsGranted() =
        ActivityCompat.checkSelfPermission(
            (activity as HomeActivity),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
            (activity as HomeActivity),
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

    private fun shouldShowRequestPermissionRationale() =
        ActivityCompat.shouldShowRequestPermissionRationale(
            (activity as HomeActivity),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) && ActivityCompat.shouldShowRequestPermissionRationale(
            (activity as HomeActivity),
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            LOCATION_REQUEST -> {
                invokeLocationAction()
            }
        }
    }

    override fun onItemClick(position: Int) {

        val clickedNearbyPlacesItem: PlaceModel =
            homeViewModel.nearByPlacesList.value?.get(position)!!

        val id: String = clickedNearbyPlacesItem.googlePlace.id!!
        view?.let {
            Snackbar.make(it, "item $id Clicked", Snackbar.LENGTH_SHORT)
                .setBackgroundTint(resources.getColor(R.color.success)).show()
        }
    }
}
