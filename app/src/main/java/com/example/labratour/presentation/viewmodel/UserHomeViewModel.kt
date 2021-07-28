package com.example.labratour.presentation.viewmodel

import android.content.ContentValues
import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.labratour.presentation.model.data.PlaceModel
import com.example.labratour.presentation.model.data.UserModel
import com.example.labratour.presentation.utils.Constants
import com.google.android.gms.common.api.ApiException
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class UserHomeViewModel(private val placesClient: PlacesClient) : ViewModel() {

    lateinit var user: UserModel

    // Construct a request object, passing the place ID and fields array.
    private lateinit var request: FetchPlaceRequest

    // test only
    lateinit var testBitmap: Bitmap
    lateinit var currentPlace: Place
    private var list = ArrayList<PlaceModel>()

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

    val currentUserId: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val place: MutableLiveData<Place> by lazy {
        MutableLiveData<Place>()
    }

    val error: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val photoLoading: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val photoBitmapp: MutableLiveData<Bitmap> by lazy {
        MutableLiveData<Bitmap>()
    }

    // TODO - this function should be in the data area
    private fun getCurrentUserID(): String {
        val currentUser = FirebaseAuth.getInstance().currentUser
        var currentUserID = ""
        if (currentUser != null) {
            currentUserID = currentUser.uid
        }
        this.currentUserId.postValue(currentUserID)
        return currentUserID
    }

    // TODO - this function should be in the data area
    fun getUserDetails() {
        FirebaseFirestore.getInstance().collection(Constants.USERS).document(getCurrentUserID())
            .get().addOnSuccessListener { document ->
                val User = document.toObject(UserModel::class.java)!!
            }
    }

    // fetch place by id
    fun getPlaceById(id: String) {
        request = id.let { FetchPlaceRequest.newInstance(it, placeFields) }
        Log.i("Places", "trying to fetch place by id.... with id:{$id}")
        placesClient.fetchPlace(request)
            .addOnSuccessListener { response: FetchPlaceResponse ->
                Log.i("Places", "fetch place data success!")
                val place = response.place
                this.currentPlace = place
                // this.place.postValue(place)
                this.fetchPhoto()
            }.addOnFailureListener { exception: Exception ->
                if (exception is ApiException) {
                    Log.e(ContentValues.TAG, "Place not found: ${exception.message}")
                    // val statusCode = exception.statusCode
                    error.postValue(exception.message)
                }
            }
    }

    fun fetchPhoto() {
        Log.i("Places", "trying to fetch photo!")
        // Get the photo metadata.
        val metada = currentPlace?.photoMetadatas
        if (metada == null || metada.isEmpty()) {
            Log.i("Places", "No photo metadata.")
        }
        val photoMetadata = metada?.first()
        // Get the attribution text.
        val attributions = photoMetadata?.attributions
        // Create a FetchPhotoRequest.
        val photoRequest = photoMetadata?.let {
            FetchPhotoRequest.builder(it)
                .setMaxWidth(1500) // Optional.
                .setMaxHeight(600) // Optional.
                .build()
        }
        if (photoRequest != null) {
            placesClient.fetchPhoto(photoRequest)
                .addOnSuccessListener { fetchPhotoResponse: FetchPhotoResponse ->
                    val bitmap = fetchPhotoResponse.bitmap
                    Log.i("Places", "fetching photo success!")
                    // TODO test!
                    this.testBitmap = bitmap
                    this.photoBitmapp.postValue(bitmap)
                    photoLoading.postValue(false)
                }.addOnFailureListener { exception: Exception ->
                    if (exception is ApiException) {
                        photoLoading.postValue(false)
                        Log.e(ContentValues.TAG, "Place not found: ${exception.message}")
                        Log.i("Places", "fetching photo failed")
                        val statusCode = exception.statusCode
                        error.postValue(exception.message)
                    }
                }
        }
    }

    fun getPhoneNumber(): String? {
        return this.place.value?.phoneNumber
    }

    fun generatePlacesListForTest(): List<PlaceModel> {
        for (i in 0 until 10) {
            var id = "ChIJcURkgp492jERS4A65dNZuts"
            var bitmap: Bitmap
            var googlePlace: Place
            request = id.let { FetchPlaceRequest.newInstance(it, placeFields) }
            Log.i("Places", "trying to fetch place by id.... with id:{$id}")
            placesClient.fetchPlace(request)
                .addOnSuccessListener { response: FetchPlaceResponse ->
                    Log.i("Places", "fetch place data success!")
                    val place = response.place
                    googlePlace = place
                    Log.i("Places", "trying to fetch photo!")
                    // Get the photo metadata.
                    val metada = googlePlace.photoMetadatas
                    if (metada == null || metada.isEmpty()) {
                        Log.i("Places", "No photo metadata.")
                    }
                    val photoMetadata = metada?.first()
                    // Get the attribution text.
                    val attributions = photoMetadata?.attributions
                    // Create a FetchPhotoRequest.
                    val photoRequest = photoMetadata?.let {
                        FetchPhotoRequest.builder(it)
                            .setMaxWidth(1500) // Optional.
                            .setMaxHeight(600) // Optional.
                            .build()
                    }
                    if (photoRequest != null) {
                        placesClient.fetchPhoto(photoRequest)
                            .addOnSuccessListener { fetchPhotoResponse: FetchPhotoResponse ->
                                var bitmapw = fetchPhotoResponse.bitmap
                                Log.i("Places", "fetching photo success!")
                                // TODO test!
                                bitmap = bitmapw
                                this.list.add(PlaceModel(googlePlace, true, 5, bitmap))
                            }.addOnFailureListener { exception: Exception ->
                                if (exception is ApiException) {
                                    photoLoading.postValue(false)
                                    Log.e(ContentValues.TAG, "Place not found: ${exception.message}")
                                    Log.i("Places", "fetching photo failed")
                                    error.postValue(exception.message)
                                }
                            }
                    }
                }.addOnFailureListener { exception: Exception ->
                    if (exception is ApiException) {
                        Log.e(ContentValues.TAG, "Place not found: ${exception.message}")
                        // val statusCode = exception.statusCode
                        error.postValue(exception.message)
                    }
                }
        }

        Log.i("Places", "size of list:" + list.size)
        return list
    }
}
