package com.example.labratour.presentation.ui.home

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.labratour.R
import com.example.labratour.presentation.model.data.PlaceModel
import com.example.labratour.presentation.ui.adapters.SmallPlaceCardRecyclerAdapter
import com.example.labratour.presentation.utils.GPS_REQUEST
import com.example.labratour.presentation.utils.GpsUtils
import com.example.labratour.presentation.viewmodel.LocationViewModel
import com.example.labratour.presentation.viewmodel.UserHomeViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.customed_places_list_progress_bar
import kotlinx.android.synthetic.main.fragment_home.customed_places_recycler_view
import kotlinx.android.synthetic.main.header_navigation_drawer.view.*
import kotlinx.android.synthetic.main.location_card.view.*
import java.util.*

const val LOCATION_REQUEST = 100
const val GPS_REQUEST = 101

class HomeFragment : Fragment(R.layout.fragment_home), SmallPlaceCardRecyclerAdapter.OnItemClickListener {

    private var customPlacesLoaded: Boolean = false
    private lateinit var homeViewModel: UserHomeViewModel
    private lateinit var locationViewModel: LocationViewModel
    private var isGPSEnabled = false
    private var nearByPlacesLoaded = false
    private lateinit var pullToRefresh: SwipeRefreshLayout
    private lateinit var frag_view: View

    // --------------------------------- fragment functions ---------------------------------------
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.homeViewModel = (activity as HomeActivity?)?.userHomeViewModel!!
        this.locationViewModel = (activity as HomeActivity?)?.locationViewModel!!
        // this.homeViewModel.invokeGetUser()
        // this.homeViewModel.invokeGetLikedPlacesList()
        checkGps()
        invokeLocationAction()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i("Places", "HomeFragment onViewCreated")

        this.frag_view = view

        // view models observation
        this.homeViewModel.customizedPlaceModelListLiveData.observe(viewLifecycleOwner, { onCustomPlacesListChanged() })
        this.homeViewModel.userModelLiveData.observe(viewLifecycleOwner, { onUserChanged() })
        this.homeViewModel.nearByPlaceModelListLiveData.observe(viewLifecycleOwner, { onNearByPlacesListChanged(frag_view) })
        this.homeViewModel.error.observe(viewLifecycleOwner, { onErrorChanged(frag_view) })
        this.locationViewModel.getLocationData().observe(viewLifecycleOwner, { startLocationUpdate(frag_view) })

        // listeners
        pullToRefresh = home_refresh_layout
        setPullToRefreshListener()
    }

    private fun onErrorChanged(view: View) {
        Snackbar.make(view, this.homeViewModel.error.value.toString(), Snackbar.LENGTH_SHORT)
            .setBackgroundTint(resources.getColor(R.color.error)).show()
    }

    fun updateCityCountry() {
        val geocoder: Geocoder
        val addresses: List<Address>
        geocoder = Geocoder(activity as HomeActivity, Locale.getDefault())
        val lat = this.locationViewModel.getLocationData().value?.latitude
        val long = this.locationViewModel.getLocationData().value?.longitude

        if (lat != null && long != null) {
            addresses = geocoder.getFromLocation(lat, long, 1)

            val address: String =
                addresses[0].getAddressLine(0) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()

            val city: String = addresses[0].getLocality()
            val state: String = addresses[0].getAdminArea()
            val country: String = addresses[0].getCountryName()
            // val postalCode: String = addresses[0].getPostalCode()
            val knownName: String = addresses[0].getFeatureName() // Only if availa

            location_card.country_tv.text = country
            location_card.city_tv.text = city
        } else {
            location_card.country_tv.text = "GPS unavailable!"
            location_card.city_tv.text = "GPS unavailable!"
        }
    }

    private fun setPullToRefreshListener() {
        pullToRefresh.setOnRefreshListener {
            // update lists!
            this.homeViewModel.initializeViewModel()
            checkGps()
            this.invokeLocationAction()
            pullToRefresh.isRefreshing = false
        }
    }

    // ----------------------------- On Live Data Changed Handlers --------------------------------
    private fun onNearByPlacesListChanged(view: View) {
        places_close_to_you_recycler_view.adapter = this.homeViewModel.nearByPlaceModelListLiveData.value?.let {
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

    fun onCustomPlacesListChanged() {
        if (this.homeViewModel.customizedPlaceModelList.size > 0) {
            customed_places_list_progress_bar.visibility = View.GONE
            customed_places_recycler_view.adapter = SmallPlaceCardRecyclerAdapter(this.homeViewModel.customizedPlaceModelList, this)
            customed_places_recycler_view.layoutManager =
                LinearLayoutManager(activity as HomeActivity, LinearLayoutManager.HORIZONTAL, false)
            customed_places_recycler_view.setHasFixedSize(true)
            customed_places_recycler_view.visibility = View.VISIBLE
        } else {
            customed_places_list_progress_bar.visibility = View.VISIBLE
            customed_places_recycler_view.visibility = View.GONE
        }
    }

    // whenever you click on an item from the recylcler - but need to know which one
    override fun onItemClick(position: Int) {

        val clickedNearbyPlacesItem: PlaceModel =
            homeViewModel.nearByPlaceModelListLiveData.value?.get(position)!!

        val id: String = clickedNearbyPlacesItem.googlePlace.id!!
        view?.let {
            Snackbar.make(it, "item $id Clicked", Snackbar.LENGTH_SHORT)
                .setBackgroundTint(resources.getColor(R.color.success)).show()
        }

        // move to fragment of result of place!
        val action = HomeFragmentDirections.actionHomeFragmentToPlaceResultFragment(id)
        findNavController().navigate(action)
    }

    fun onUserChanged() {
        if ((activity as HomeActivity).navigationView.headerCount > 0) {
            // avoid NPE by first checking if there is at least one Header View available
            val headerLayout: View = (activity as HomeActivity).navigationView.getHeaderView(0)
            val user_name: String = homeViewModel.userModelLiveData.value?.userName!!
            headerLayout.username_drawer_header.text = user_name
            headerLayout.email_drawer_header.text = homeViewModel.userModelLiveData.value?.email
            (activity as HomeActivity).toolbar.title = "Welcome Back, $user_name"
        }
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

    override fun onResume() {
        super.onResume()
        checkGps()
    }

    private fun startLocationUpdate(view: View) {
        val lat = this.locationViewModel.getLocationData().value?.latitude
        val long = this.locationViewModel.getLocationData().value?.longitude

        if (lat != null && long != null) {
            location_card.location_ltlng_txt.text = getString(
                R.string.latLong,
                locationViewModel.getLocationData().value?.latitude,
                locationViewModel.getLocationData().value?.longitude
            )
            this.homeViewModel.invokeNearbyPlacesRoutinr(lat.toString(), long.toString())
        } else {
            location_card.location_ltlng_txt.text = "GPS unavailable!"
        }
        updateCityCountry()
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
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION
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
            Manifest.permission.ACCESS_BACKGROUND_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

    private fun shouldShowRequestPermissionRationale() =
        ActivityCompat.shouldShowRequestPermissionRationale(
            (activity as HomeActivity),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) && ActivityCompat.shouldShowRequestPermissionRationale(
            (activity as HomeActivity),
            Manifest.permission.ACCESS_BACKGROUND_LOCATION
        )

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        Log.i("Places", "LOCATION PERMISSION GRANTED")

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            LOCATION_REQUEST -> {
                invokeLocationAction()
                Log.i("Places", "LOCATION PERMISSION GRANTED")
            }
            GPS_REQUEST -> {
                invokeLocationAction()
                Log.i("Places", "LOCATION PERMISSION GRANTED")
            }
        }
    }
}
