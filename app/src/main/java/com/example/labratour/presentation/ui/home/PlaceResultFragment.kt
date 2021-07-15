package com.example.labratour.presentation.ui.home

import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.labratour.R
import com.google.android.gms.common.api.ApiException
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPhotoRequest
import com.google.android.libraries.places.api.net.FetchPhotoResponse
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FetchPlaceResponse
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_place.*

class PlaceResultFragment : Fragment(R.layout.fragment_place) {
    private lateinit var place: Place

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
        Place.Field.USER_RATINGS_TOTAL,
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
        url_place_button.setOnClickListener { onClickUrl() }
        call_place_button.setOnClickListener { onClickCall() }
        like_place_button.setOnClickListener { onClickLike() }
        share_place_button.setOnClickListener { onClickShare() }
    }

    // TODO - this code might be transfered to a usecase!
    private fun fetchPlaceData() {
        (activity as HomeActivity).placesClient.fetchPlace(request)
            .addOnSuccessListener { response: FetchPlaceResponse ->
                val place = response.place
                this.place = place
                Log.i("Places", "fetch place data with id: ${place.name}")
                // update place in fragment
                place_id_tv.text = (place.id).toString()
                place_phone_tv.text = (place.phoneNumber).toString()
                place_name_tv.text = (place.name).toString()
                place_address_tv.text = (place.address).toString()

                // Get the photo metadata.
                val metada = place.photoMetadatas
                if (metada == null || metada.isEmpty()) {
                    Log.w(TAG, "No photo metadata.")
                    return@addOnSuccessListener
                }
                val photoMetadata = metada.first()

                // Get the attribution text.
                val attributions = photoMetadata?.attributions

                // Create a FetchPhotoRequest.
                val photoRequest = FetchPhotoRequest.builder(photoMetadata)
                    .setMaxWidth(1500) // Optional.
                    .setMaxHeight(600) // Optional.
                    .build()
                (activity as HomeActivity).placesClient.fetchPhoto(photoRequest)
                    .addOnSuccessListener { fetchPhotoResponse: FetchPhotoResponse ->
                        val bitmap = fetchPhotoResponse.bitmap
                        place_img.setImageBitmap(bitmap)
                        place_progress_bar.visibility = View.GONE

                        // place_opening_hours_tv.text = (place.openingHours).toString()
                        if (place.isOpen == true) {
                            place_is_open_tv.text = "Opened!"
                            place_is_open_tv.setTextColor(Color.GREEN)
                        } else {
                            place_is_open_tv.text = "Closed!"
                            place_is_open_tv.setTextColor(Color.RED)
                        }
                    }.addOnFailureListener { exception: Exception ->
                        if (exception is ApiException) {
                            place_progress_bar.visibility = View.GONE
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

    private fun onClickShare() {
        TODO("Not yet implemented")
    }

    private fun onClickLike() {
        TODO("Not yet implemented")
    }

    private fun onClickCall() {
        val intent =
            Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", this.place.phoneNumber, null))
        startActivity(intent)
    }

    private fun onClickUrl() {
        TODO("Not yet implemented")
    }
}
