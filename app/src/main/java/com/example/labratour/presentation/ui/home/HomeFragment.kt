package com.example.labratour.presentation.ui.home

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.labratour.R
import com.example.labratour.presentation.model.data.PlaceModel
import com.example.labratour.presentation.ui.adapters.SmallPlaceCardRecyclerAdapter
import com.example.labratour.presentation.ui.adapters.SmallWeatherCardRecyclerAdapter
import com.example.labratour.presentation.utils.GPS_REQUEST
import com.example.labratour.presentation.utils.GpsUtils
import com.example.labratour.presentation.viewmodel.LocationViewModel
import com.example.labratour.presentation.viewmodel.UserHomeViewModel
import com.example.labratour.presentation.viewmodel.WeatherViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.customed_places_list_progress_bar
import kotlinx.android.synthetic.main.fragment_home.customed_places_recycler_view
import kotlinx.android.synthetic.main.fragment_home.weather_card
import kotlinx.android.synthetic.main.header_navigation_drawer.view.*
import kotlinx.android.synthetic.main.location_card.view.*
import kotlinx.android.synthetic.main.weather_card.*
import kotlinx.android.synthetic.main.weather_card.view.*
import kotlinx.android.synthetic.main.weather_card.view.current_degrees_tv
import kotlinx.android.synthetic.main.weather_card.view.current_weather_icon
import kotlinx.android.synthetic.main.weather_card.view.date_tv
import kotlinx.android.synthetic.main.weather_card.view.description_tv
import kotlinx.android.synthetic.main.weather_card.view.feels_like_tv
import kotlinx.coroutines.flow.collect
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.floor

const val LOCATION_REQUEST = 100
const val GPS_REQUEST = 101
// lists codes
const val NEARBY_LIST_CODE = 15
const val LIKED_LIST_CODE = 16
const val CUSTOMIZED_LIST_CODE = 17
const val CATEGORY_LIST_CODE = 18

class HomeFragment : Fragment(R.layout.fragment_home), SmallPlaceCardRecyclerAdapter.OnItemClickListener {
    // viewmodels
    private lateinit var homeViewModel: UserHomeViewModel
    private lateinit var locationViewModel: LocationViewModel
    private lateinit var weatherViewModel: WeatherViewModel
    // gps
    private var isGPSEnabled = false
    // pull to refresh
    private lateinit var pullToRefresh: SwipeRefreshLayout
    // view
    private lateinit var frag_view: View
    //
    private lateinit var sp: SharedPreferences

    private var distance_disabled: Boolean = false

    private var km: Double = 0.3

    // --------------------------------- fragment functions ---------------------------------------
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // get references to view models on creation
        this.homeViewModel = (activity as HomeActivity?)?.userHomeViewModel!!
        this.locationViewModel = (activity as HomeActivity?)?.locationViewModel!!
        this.weatherViewModel = (activity as HomeActivity?)?.weatherViewModel!!
        // load settings
        loadSettings()
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
        onWeatherForecastChanged()

        // set on listeners
        customized_places_button.setOnClickListener { onCustomizedPlacesClicked() }
        places_near_by_button.setOnClickListener { onPlacesNearByClicked() }

        // pull to refresh define
        pullToRefresh = home_refresh_layout
        setPullToRefreshListener()

        // invoke location routines (fetch location, weather, nearby, recommended...)
        checkGps()
        invokeLocationAction()
    }

    override fun onResume() {
        super.onResume()
        invokeLocationAction()
        loadSettings()
    }

    fun loadSettings() {
        sp = PreferenceManager.getDefaultSharedPreferences((context))
        distance_disabled = sp.getBoolean("refresh_disabled", true)
        km = (sp.getInt("distance", 500).toDouble() / 1000)
    }

    // ----------------------------------- On Click Listeners--------------------------------------
    private fun setPullToRefreshListener() {
        pullToRefresh.setOnRefreshListener {
            // update lists!
            if (!this.distance_disabled) {
                checkGps()
                this.invokeLocationAction()
            }
            pullToRefresh.isRefreshing = false
        }
    }

    private fun onCustomizedPlacesClicked() {
        val action = HomeFragmentDirections.actionHomeFragmentToPlacesListFragment("Customized")
        findNavController().navigate(action)
    }

    private fun onPlacesNearByClicked() {
        val action = HomeFragmentDirections.actionHomeFragmentToPlacesListFragment("Nearby")
        findNavController().navigate(action)
    }

    // ----------------------------- On Live Data Changed Handlers --------------------------------
    private fun onErrorChanged(view: View) {
        Snackbar.make(view, this.homeViewModel.error.value.toString(), Snackbar.LENGTH_SHORT)
            .setBackgroundTint(resources.getColor(R.color.error)).show()
    }

    private fun onNearByPlacesListChanged(view: View) {
        if (this.homeViewModel.nearByPlaceModelListLiveData.value != null) {
            if (this.homeViewModel.nearByPlaceModelListLiveData.value?.size!! > 0) {
                places_close_to_you_recycler_view.adapter = SmallPlaceCardRecyclerAdapter(this.homeViewModel.nearByPlaceModelListLiveData.value!!, this, NEARBY_LIST_CODE)
                places_close_to_you_recycler_view.layoutManager =
                    LinearLayoutManager(activity as HomeActivity, LinearLayoutManager.HORIZONTAL, false)
                places_close_to_you_recycler_view.setHasFixedSize(true)
                nearby_places_list_progress_bar.visibility = View.GONE
                places_close_to_you_recycler_view.visibility = View.VISIBLE
            }
        } else {
            nearby_places_list_progress_bar.visibility = View.VISIBLE
            places_close_to_you_recycler_view.visibility = View.GONE
        }
    }

    private fun onCustomPlacesListChanged() {
        if (this.homeViewModel.customizedPlaceModelListLiveData.value != null) {
            if (this.homeViewModel.customizedPlaceModelListLiveData.value?.size!! > 0) {
                customed_places_list_progress_bar.visibility = View.GONE
                customed_places_recycler_view.adapter = SmallPlaceCardRecyclerAdapter(this.homeViewModel.customizedPlaceModelListLiveData.value!!, this, CUSTOMIZED_LIST_CODE)
                customed_places_recycler_view.layoutManager =
                    LinearLayoutManager(activity as HomeActivity, LinearLayoutManager.HORIZONTAL, false)
                customed_places_recycler_view.setHasFixedSize(true)
                customed_places_recycler_view.visibility = View.VISIBLE
            }
        } else {
            customed_places_list_progress_bar.visibility = View.VISIBLE
            customed_places_recycler_view.visibility = View.GONE
        }
    }

    private fun onWeatherForecastChanged() {
        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            weatherViewModel.weather.collect {
                event ->
                when (event) {
                    is WeatherViewModel.WeatherEvent.Success -> {
                        // update visibility
                        weather_card_progress_bar.isVisible = false
                        weather_card.isVisible = true
                        // current weather temperature
                        val current_temp: Double = floor(event.forecast[0].main.temp)
                        weather_card.current_degrees_tv.text = current_temp.toString() + "\u2103"
                        // update current icon
                        val current_condition: String = event.forecast[0].weather[0].main
                        when (current_condition) {
                            "Clear" -> {
                                weather_card.current_weather_icon.setImageResource(R.drawable.ic_day)
                            }
                            "Clouds" -> {
                                weather_card.current_weather_icon.setImageResource(R.drawable.ic_cloudy_day_1)
                            }
                            "Snow" -> {
                                weather_card.current_weather_icon.setImageResource(R.drawable.ic_snowy_1)
                            }
                            "Rain" -> {
                                weather_card.current_weather_icon.setImageResource(R.drawable.ic_rainy_1)
                            }
                            "Drizzle" -> {
                            }
                            "Thunderstorm" -> {
                                weather_card.current_weather_icon.setImageResource(R.drawable.ic_thunder)
                            }
                        }
                        //
                        weather_card.feels_like_tv.text = event.forecast[0].main.feels_like.toString() + "\u2103"
                        weather_card.description_tv.text = event.forecast[0].weather[0].description
                        weather_card.date_tv.text = event.forecast[0].dt_txt

                        // list
                        weather_recycler_view.adapter = SmallWeatherCardRecyclerAdapter(event.forecast)
                        weather_recycler_view.layoutManager =
                            LinearLayoutManager(activity as HomeActivity, LinearLayoutManager.HORIZONTAL, false)
                        weather_recycler_view.setHasFixedSize(true)

                        // log
                        Log.i("Places", "Weather Collector Success")
                    }
                    is WeatherViewModel.WeatherEvent.Failure -> {
//                            progressBar.isVisible = false
//                            tvResult.setTextColor(Color.RED)
//                            tvResult.text = event.errorText
                        Log.i("Places", "Weather Collector Failure " + event.errorText)
                    }
                    is WeatherViewModel.WeatherEvent.Loading -> {
                        weather_card_progress_bar.isVisible = true
                        weather_card.isVisible = false
                    }
                    else -> Unit
                }
            }
        }
    }

    private fun getDateTime(s: String): String? {
        try {
            val sdf = SimpleDateFormat("MM/dd/yyyy")
            val netDate = Date(s.toLong() * 1000)
            return sdf.format(netDate)
        } catch (e: Exception) {
            return e.toString()
        }
    }

    private fun onUserChanged() {
        if ((activity as HomeActivity).navigationView.headerCount > 0) {
            // avoid NPE by first checking if there is at least one Header View available
            val headerLayout: View = (activity as HomeActivity).navigationView.getHeaderView(0)
            val user_name: String = homeViewModel.userModelLiveData.value?.userName!!
            headerLayout.username_drawer_header.text = user_name
            headerLayout.email_drawer_header.text = homeViewModel.userModelLiveData.value?.email
            (activity as HomeActivity).toolbar.title = "Welcome Back, $user_name"
        }
    }

    override fun onItemClick(position: Int, code: Int) {

        var clickedPlaceItem: PlaceModel? = null
        var id: String = ""
        when (code) {
            NEARBY_LIST_CODE -> {
                clickedPlaceItem = homeViewModel.nearByPlaceModelListLiveData.value?.get(position)!!
                id = clickedPlaceItem.googlePlaceSdk.id!!
            }
            CUSTOMIZED_LIST_CODE -> {
                clickedPlaceItem = homeViewModel.customizedPlaceModelListLiveData.value?.get(position)!!
                id = clickedPlaceItem.googlePlaceSdk.id!!
            }
            LIKED_LIST_CODE -> {
                clickedPlaceItem = homeViewModel.likedPlaceModelListLiveData.value?.get(position)!!
                id = clickedPlaceItem.googlePlaceSdk.id!!
            }
        }
        // move to fragment of result of place!
        val action = HomeFragmentDirections.actionHomeFragmentToPlaceResultFragment(id)
        findNavController().navigate(action)
    }

    // ---------------------------------------- gps -----------------------------------------------
    private fun checkGps() {
        // gps
        GpsUtils(activity as HomeActivity).turnGPSOn(object : GpsUtils.OnGpsListener {
            override fun gpsStatus(isGPSEnable: Boolean) {
                isGPSEnabled = isGPSEnable
            }
        })
    }

    private fun startLocationUpdate(view: View) {
        loadSettings()
        val lat = this.locationViewModel.getLocationData().value?.latitude
        val long = this.locationViewModel.getLocationData().value?.longitude
        if (lat != null && long != null) {
            // update prev first time
            if (!this.locationViewModel.started) {
                this.locationViewModel.prevLat = lat
                this.locationViewModel.prevLong = long
                this.locationViewModel.started = true
                updateUI()
            }
            // update coordinates
            location_card.location_ltlng_txt.text = getString(
                R.string.latLong,
                locationViewModel.getLocationData().value?.latitude,
                locationViewModel.getLocationData().value?.longitude
            )
            // SINCE THE LAT LONG IS GOOD WE CAN FORECAST THINGS !!!
            // check if current location against prev location
            val dif: Double = distanceInKm(long, lat, this.locationViewModel.prevLong, this.locationViewModel.prevLat)
            if (dif > (km)) {
                Log.i("Places", "startLocationUpdate, PREV COORDINATES: ${this.locationViewModel.prevLat} / ${this.locationViewModel.prevLong}")
                Log.i("Places", "startLocationUpdate, CURRENT COORDINATES:  $lat / $long")
                Log.i("Places", "startLocationUpdate, DIFFERENCE DISTANCE IS: $dif")

                Log.i("Places", "startLocationUpdate, KM PARAMETER IS: $km")

                this.locationViewModel.started = false
                updateUI()
            }
            updateCityCountry()
        } else {
            location_card.location_ltlng_txt.text = "GPS unavailable!"
        }
    }

    private fun updateUI() {
        val lat = this.locationViewModel.getLocationData().value?.latitude
        val long = this.locationViewModel.getLocationData().value?.longitude
        // updates
        this.homeViewModel.nearbyPlacesCoRoutine(lat.toString(), long.toString())
        this.homeViewModel.customizedPlacesListCoRoutine()
        this.weatherViewModel.forecast(lat.toString(), long.toString())
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
            !isGPSEnabled -> {
                location_card.location_ltlng_txt.text = "GPS unavailable!"
                location_card.country_tv.text = "GPS unavailable!"
                location_card.city_tv.text = "GPS unavailable!"
            }

            isPermissionsGranted() -> view?.let { startLocationUpdate(it) }

            shouldShowRequestPermissionRationale() ->
                location_card.location_ltlng_txt.text =
                    getString(R.string.permission_request)

            else -> {
                requestPermiision()
            }
        }
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

    fun requestPermiision() {
        ActivityCompat.requestPermissions(
            (activity as HomeActivity),
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            LOCATION_REQUEST
        )
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

    fun distanceInKm(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val theta = lon1 - lon2
        var dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta))
        dist = Math.acos(dist)
        dist = rad2deg(dist)
        dist = dist * 60 * 1.1515
        dist = dist * 1.609344
        return dist
    }

    private fun deg2rad(deg: Double): Double {
        return deg * Math.PI / 180.0
    }

    private fun rad2deg(rad: Double): Double {
        return rad * 180.0 / Math.PI
    }
}
