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
import com.example.labratour.presentation.ui.adapters.SmallPlaceCardRecyclerAdapter
import com.example.labratour.presentation.utils.GpsUtils
import com.example.labratour.presentation.viewmodel.LocationViewModel
import com.example.labratour.presentation.viewmodel.UserHomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.location_card.view.*

const val LOCATION_REQUEST = 100
const val GPS_REQUEST = 101

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var homeViewModel: UserHomeViewModel
    private lateinit var locationViewModel: LocationViewModel
    private var isGPSEnabled = false
    private var dataLoaded = false
    private lateinit var pullToRefresh: SwipeRefreshLayout

    override fun onStart() {
        super.onStart()
        invokeLocationAction()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.homeViewModel = (activity as HomeActivity?)?.userHomeViewModel!!
        this.locationViewModel = (activity as HomeActivity?)?.locationViewModel!!
        this.homeViewModel.getAllPlacesLists()
        // this.homeViewModel.generatePlacesListForTest()
        checkGps()
    }

    fun checkGps() {
        // gps
        GpsUtils(activity as HomeActivity).turnGPSOn(object : GpsUtils.OnGpsListener {
            override fun gpsStatus(isGPSEnable: Boolean) {
                isGPSEnabled = isGPSEnable
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // view models observation
        // this.homeViewModel.nearByPlacesListTest.observe(viewLifecycleOwner, { onNearByPlacesListChanged(view) })
        this.locationViewModel.getLocationData().observe(viewLifecycleOwner, { startLocationUpdate(view) })
        this.homeViewModel.nearByPlacesList.observe(viewLifecycleOwner, { onNearByPlacesListChanged(view) })
        // pull to refresh
        pullToRefresh = home_refresh_layout
        setPullToRefreshListener()
    }

    private fun setPullToRefreshListener() {
        pullToRefresh.setOnRefreshListener {
            // update lists!
            this.homeViewModel.nearByPlacesListTest.value?.clear()
            this.homeViewModel.generatePlacesListForTest()
            checkGps()
            invokeLocationAction()
            places_close_to_you_recycler_view.visibility = View.GONE
            nearby_places_list_progress_bar.visibility = View.VISIBLE
            dataLoaded = false
            pullToRefresh.isRefreshing = false
        }
    }

    private fun startLocationUpdate(view: View) {
        location_card.location_ltlng_txt.text = getString(R.string.latLong, locationViewModel.getLocationData().value?.latitude, locationViewModel.getLocationData().value?.longitude)
    }

    private fun onNearByPlacesListChanged(view: View) {
        places_close_to_you_recycler_view.adapter = this.homeViewModel.nearByPlacesList.value?.let {
            SmallPlaceCardRecyclerAdapter(
                it
            )
        }
        places_close_to_you_recycler_view.layoutManager = LinearLayoutManager(activity as HomeActivity, LinearLayoutManager.HORIZONTAL, false)
        places_close_to_you_recycler_view.setHasFixedSize(true)
        nearby_places_list_progress_bar.visibility = View.GONE
        places_close_to_you_recycler_view.visibility = View.VISIBLE
        this.dataLoaded = true
    }

    // -- fragment functions --
    override fun onPause() {
        super.onPause()
        Log.i("Places", "HomeFragment onPause")
    }

    override fun onResume() {
        super.onResume()
        Log.i("Places", "HomeFragment onResume")
        if (dataLoaded) {
            return
        } else {
            nearby_places_list_progress_bar.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.i("Places", "HomeFragment onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        // in case we destroy the frasgent we want to clear the list
        this.homeViewModel.nearByPlacesList.value?.clear()
        Log.i("Places", "HomeFragment onDestroy")
    }

    // gps
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

            shouldShowRequestPermissionRationale() -> location_card.location_ltlng_txt.text = getString(R.string.permission_request)

            else -> ActivityCompat.requestPermissions(
                (activity as HomeActivity),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
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
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            LOCATION_REQUEST -> {
                invokeLocationAction()
            }
        }
    }
}
