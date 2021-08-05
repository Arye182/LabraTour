package com.example.labratour.presentation.viewmodel

import android.content.ContentValues
import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.labratour.domain.useCases.DefaultObserver
import com.example.labratour.domain.useCases.GetNearbyPlacesUseCase
import com.example.labratour.domain.useCases.LogInUseCase
import com.example.labratour.presentation.model.data.PlaceModel
import com.example.labratour.presentation.model.data.UserModel
import com.google.android.gms.common.api.ApiException
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.*
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class UserHomeViewModel(private val placesClient: PlacesClient, private val getNearbyPlacesUseCase: GetNearbyPlacesUseCase,) : ViewModel() {

    lateinit var user: UserModel
    // Construct a request object, passing the place ID and fields array.
    private lateinit var request: FetchPlaceRequest
    private var testPlacesList = ArrayList<PlaceModel>()
    private var nearbyPlacesStringList = ArrayList<String>()
    private var nearbyPlacesList = ArrayList<PlaceModel>()

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

    val fetchingStringListNearByComplete: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val photoBitmap: MutableLiveData<Bitmap> by lazy {
        MutableLiveData<Bitmap>()
    }

    val nearByPlacesListTest: MutableLiveData<ArrayList<PlaceModel>> by lazy {
        MutableLiveData<ArrayList<PlaceModel>>()
    }

    val nearByPlacesList: MutableLiveData<ArrayList<PlaceModel>> by lazy {
        MutableLiveData<ArrayList<PlaceModel>>()
    }

    private inner class NearbyPlacesStringListFetcherObserver : DefaultObserver<ArrayList<String>>() {
        override fun onComplete() {
            Log.i("Places", "NearbyPlacesStringListFetcherObserver Observer - On Complete...")
            // isLoading.postValue(false)
        }
        override fun onError(exception: Throwable) {
            Log.i("Places", "NearbyPlacesStringListFetcherObserver Observer - On Error: " + exception.message)
            // isLoading.postValue(false)
            // logInTaskStatus.postValue(false)
            error.postValue(exception.message)
        }
        override fun onNext(value: ArrayList<String>) {
            Log.i("Places", "NearbyPlacesStringListFetcherObserver Observer - On Next...")
            nearbyPlacesStringList = value
            // fetchingStringListNearByComplete.postValue(true)
            generateNearByPlacesList()
            // logInTaskStatus.postValue(true)
        }
    }

    // TODO - this function should be in the data area
    private fun getCurrentUserID(): String {
        val currentUser = FirebaseAuth.getInstance().currentUser
        var currentUserID = ""
        if (currentUser != null) {
            currentUserID = currentUser.uid
        }
        this.currentUserId.setValue(currentUserID)
        return currentUserID
    }

    // fetch place by id
    fun getPlaceById(id: String) {
        request = id.let { FetchPlaceRequest.newInstance(it, placeFields) }
        Log.i("Places", "trying to fetch place by id.... with id:{$id}")
        placesClient.fetchPlace(request)
            .addOnSuccessListener { response: FetchPlaceResponse ->
                Log.i("Places", "fetch place data success!")
                val place = response.place
                this.place.setValue(place)
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
        // val metada = currentPlace?.photoMetadatas
        val metada = this.place.value?.photoMetadatas
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
                    // this.testBitmap = bitmap
                    this.photoBitmap.setValue(bitmap)
                    // this.photoBitmapp.postValue(bitmap)
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

    // this is a testing part on preowned place id
    fun generatePlacesListForTest() {
        viewModelScope.launch {
            // Coroutine that will be canceled when the ViewModel is cleared.
            placesListCoRoutine()
        }
    }
    suspend fun placesListCoRoutine() {
        for (i in 0 until 10) {
            var id = "ChIJcURkgp492jERS4A65dNZuts"
            var bitmap: Bitmap
            var googlePlace: Place
            request = id.let { FetchPlaceRequest.newInstance(it, placeFields) }
            Log.i("Places", "trying to fetch place by id.... with id:{$id}")
            suspendCoroutine<Unit> { continuation ->
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
                                    this.testPlacesList.add(PlaceModel(googlePlace, true, 5, bitmap))
                                    continuation.resume(Unit)
                                }.addOnFailureListener { exception: Exception ->
                                    if (exception is ApiException) {
                                        photoLoading.postValue(false)
                                        Log.e(
                                            ContentValues.TAG,
                                            "Place not found: ${exception.message}"
                                        )
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
        }
        Log.i("Places", "size of list:" + testPlacesList.size)
        this.nearByPlacesListTest.setValue(testPlacesList)
    }

    // this is getting the places list with the string list that came from domain
    fun generateNearByPlacesList() {
        viewModelScope.launch {
            // execute the use case
            getNearbyPlacesUseCase.execute(NearbyPlacesStringListFetcherObserver(), "32", "34")
            // Coroutine that will be canceled when the ViewModel is cleared.
            nearByPlacesListCoRoutine()
        }
    }
    suspend fun nearByPlacesListCoRoutine() {
        for (place_id in this.nearbyPlacesStringList) {
            var id = "ChIJcURkgp492jERS4A65dNZuts"
            var bitmap: Bitmap
            var googlePlace: Place
            request = place_id.let { FetchPlaceRequest.newInstance(it, placeFields) }
            Log.i("Places", "trying to fetch place by id.... with id:{$place_id}")
            suspendCoroutine<Unit> { continuation ->
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
                                    this.nearbyPlacesList.add(PlaceModel(googlePlace, true, 5, bitmap))
                                    continuation.resume(Unit)
                                }.addOnFailureListener { exception: Exception ->
                                    if (exception is ApiException) {
                                        photoLoading.postValue(false)
                                        Log.e(
                                            ContentValues.TAG,
                                            "Place not found: ${exception.message}"
                                        )
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
        }
        Log.i("Places", "size of list:" + testPlacesList.size)
        this.nearByPlacesList.setValue(nearbyPlacesList)
    }

    fun getAllPlacesLists() {
        generateNearByPlacesList()
        // generatePlacesListForTest()
    }
}
