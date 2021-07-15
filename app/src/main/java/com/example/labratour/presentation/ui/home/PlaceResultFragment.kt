package com.example.labratour.presentation.ui.home

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.labratour.R
import com.google.android.gms.common.api.ApiException
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FetchPlaceResponse
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_place.*

class PlaceResultFragment : Fragment(R.layout.fragment_place) {

    // Define a Place ID.
    private lateinit var placeId: String

    // Specify the fields to return.
    private val placeFields = listOf(
        Place.Field.ID,
        Place.Field.NAME,
        Place.Field.ADDRESS,
        Place.Field.LAT_LNG,
        Place.Field.BUSINESS_STATUS,
        Place.Field.OPENING_HOURS,
        Place.Field.PHONE_NUMBER,
        Place.Field.PHOTO_METADATAS,
        Place.Field.PRICE_LEVEL,
        Place.Field.RATING,
        Place.Field.TYPES,
        Place.Field.USER_RATINGS_TOTAL
    )

    // Construct a request object, passing the place ID and fields array.
    private lateinit var request: FetchPlaceRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        val args: PlaceResultFragmentArgs by navArgs()
        super.onCreate(savedInstanceState)
        placeId = args.id
        Log.i("Places", "onCreate with id: $placeId")
        request = placeId.let { FetchPlaceRequest.newInstance(it, placeFields) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchPlaceData()
    }

    private fun fetchPlaceData() {
        (activity as HomeActivity).placesClient.fetchPlace(request)
            .addOnSuccessListener { response: FetchPlaceResponse ->
                val place = response.place
                Log.i("Places", "fetch place data with id: ${place.name}")
                // update place in fragment
                place_id_tv.text = (place.id).toString()
                place_phone_tv.text = (place.phoneNumber).toString()
                place_name_tv.text = (place.name).toString()
                place_address_tv.text = (place.address).toString()
            }.addOnFailureListener { exception: Exception ->
                if (exception is ApiException) {
                    Log.e(TAG, "Place not found: ${exception.message}")
                    val statusCode = exception.statusCode
                    view?.let {
                        exception.message?.let { it1 ->
                            Snackbar.make(it, it1, Snackbar.LENGTH_SHORT)
                                .setBackgroundTint(resources.getColor(R.color.error)).show()
                        }
                    }
                }
            }
    }
}
