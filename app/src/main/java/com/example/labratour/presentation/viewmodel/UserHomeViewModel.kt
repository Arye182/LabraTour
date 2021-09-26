package com.example.labratour.presentation.viewmodel

import android.content.ContentValues
import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.labratour.domain.useCases.DefaultObserver
import com.example.labratour.domain.useCases.GetNearbyPlacesUseCase
import com.example.labratour.presentation.model.data.PlaceModel
import com.example.labratour.presentation.model.data.UserModel
import com.example.labratour.presentation.model.repositories.UserRepository
import com.google.android.gms.common.api.ApiException
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.*
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class UserHomeViewModel(
    private val placesClient: PlacesClient,
    private val getNearbyPlacesUseCase: GetNearbyPlacesUseCase,
    private val userRepository: UserRepository
) : ViewModel() {



    lateinit var user: UserModel
    // Construct a request object, passing the place ID and fields array.
    private lateinit var request: FetchPlaceRequest
    private var testPlacesList = ArrayList<PlaceModel>()
    private var nearbyPlacesStringList = ArrayList<String>()
    private var nearbyPlacesList = ArrayList<PlaceModel>()
    var firstLoaded: Boolean = false

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

    val userModel: MutableLiveData<UserModel> by lazy {
        MutableLiveData<UserModel>()
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

    val categoryPlacesList: MutableLiveData<ArrayList<PlaceModel>> by lazy {
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
            Log.i("Places", "NearbyPlacesStringListFetcherObserver Observer - On Next... Value Size = ${value.size}")
            Log.i("Places", "NearbyPlacesStringListFetcherObserver Observer - On Next... list = $value")

            // fetchingStringListNearByComplete.postValue(true)
            generateNearByPlacesList()
            // logInTaskStatus.postValue(true)
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
        viewModelScope.launch() {
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
        this.categoryPlacesList.setValue(testPlacesList)
    }

    // this is getting the places list with the string list that came from domain
    fun generateNearByPlacesList() {
        viewModelScope.launch() {
            // execute the use case
            nearByPlacesListCoRoutine()
        }
    }

    suspend fun nearByPlacesListCoRoutine() {
        for (place_id in this.nearbyPlacesStringList) {
            var bitmap: Bitmap
            var googlePlace: Place
            request = place_id.let { FetchPlaceRequest.newInstance(it, placeFields) }
            Log.i("Places", "nearByPlaces : trying to fetch place by id.... with id:{$place_id}")
            suspendCoroutine<Unit> { continuation ->
                placesClient.fetchPlace(request)
                    .addOnSuccessListener { response: FetchPlaceResponse ->
                        Log.i("Places", "nearByPlaces : fetch place data success!")
                        val place = response.place
                        googlePlace = place
                        Log.i("Places", "nearByPlaces : trying to fetch photo!")
                        // Get the photo metadata.
                        val metada = googlePlace.photoMetadatas
                        if (metada == null || metada.isEmpty()) {
                            Log.i("Places", "nearByPlaces : No photo metadata.")
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
                                    Log.i("Places", "nearByPlaces : fetching photo success!")
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
                                        Log.i("Places", "nearByPlaces : fetching photo failed")
                                        error.postValue(exception.message)
                                    }
                                }
                        } else {
                            Log.i("Places", "nearByPlaces : fetching photo failed")
                            val w: Int = 150
                            val h: Int = 150
                            val conf = Bitmap.Config.ARGB_8888 // see other conf types
                            val bmp =
                                Bitmap.createBitmap(w, h, conf) // this creates a MUTABLE bitmap
                            bitmap = bmp
                            this.nearbyPlacesList.add(PlaceModel(googlePlace, true, 5, bitmap))
                            continuation.resume(Unit)
                        }
                    }.addOnFailureListener { exception: Exception ->
                        if (exception is ApiException) {
                            Log.e(ContentValues.TAG, "nearByPlaces : Place not found: ${exception.message}")
                            // val statusCode = exception.statusCode
                            error.postValue(exception.message)
                        }
                    }
            }
        }
        Log.i("Places", "nearByPlaces : size of list:" + testPlacesList.size)
        this.nearByPlacesList.setValue(nearbyPlacesList)
    }

    // get all places list started
    fun getAllPlacesLists(lat: String, long: String) {
        Log.i("Places", "Starting To Update Places Lists (vm)")
        viewModelScope.launch() {
            // execute the use case
            getNearbyPlacesUseCase.execute(NearbyPlacesStringListFetcherObserver(), lat, long)
            generatePlacesListForTest()
        }
    }

    fun updateCategoryList(category: String, lat: String, long: String) {
        generatePlacesListForTest()
    }

    fun rankPlace(user_id: String, place_id: String, rank: Int) {
    }

    // user related functions
    fun getCurrentUserID(): String {
        val currentUser = FirebaseAuth.getInstance().currentUser
        var currentUserID = ""
        if (currentUser != null) {
            currentUserID = currentUser.uid
            this.currentUserId.postValue(currentUserID)
            viewModelScope.launch {
                getUserRoutine()
            }
        }
        return currentUserID
    }

    suspend fun getUserRoutine() = withContext(Dispatchers.IO) {
        val user = userRepository.getUser(getCurrentUserID())
        userModel.postValue(user)
    }

//    init {
//        viewModelScope.launch {
//            getCurrentUserID()
//            getUserRoutine()
//        }
//    }
}
