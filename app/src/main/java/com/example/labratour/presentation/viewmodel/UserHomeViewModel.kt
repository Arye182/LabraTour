package com.example.labratour.presentation.viewmodel

import android.content.ContentValues
import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.labratour.domain.useCases.DefaultObserver
import com.example.labratour.domain.useCases.GetNearbyPlacesUseCase
import com.example.labratour.domain.useCases.UpdateUserProfileByRateUseCase
import com.example.labratour.presentation.model.data.PlaceModel
import com.example.labratour.presentation.model.data.SavedRankedPlaceModel
import com.example.labratour.presentation.model.data.UserModel
import com.example.labratour.presentation.model.repositories.PlacesRepository
import com.example.labratour.presentation.model.repositories.SavedRankedPlacesRepository
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
    private val updateUseProfileByRateUseCase: UpdateUserProfileByRateUseCase,
    private val getNearbyPlacesUseCase: GetNearbyPlacesUseCase,
    private val userRepository: UserRepository,
    private val placeCacheRepository: PlacesRepository,
    private val savedRankedPlacesRepository: SavedRankedPlacesRepository
) : ViewModel() {
    // ------------------------------------- members ----------------------------------------------
    // user
    lateinit var user: UserModel
    val userModel: MutableLiveData<UserModel> by lazy {
        MutableLiveData<UserModel>()
    }

    // generic list utils
    private lateinit var request: FetchPlaceRequest
    val place: MutableLiveData<Place> by lazy {
        MutableLiveData<Place>()
    }
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
    val photoBitmap: MutableLiveData<Bitmap> by lazy {
        MutableLiveData<Bitmap>()
    }
    val photoLoading: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    // test list
    private var testPlacesList = ArrayList<PlaceModel>()
    val nearByPlacesListTest: MutableLiveData<ArrayList<PlaceModel>> by lazy {
        MutableLiveData<ArrayList<PlaceModel>>()
    }

    // nearby list
    private var nearbyPlacesStringList = ArrayList<String>()
    private var nearbyPlacesList = ArrayList<PlaceModel>()
    val nearByPlacesList: MutableLiveData<ArrayList<PlaceModel>> by lazy {
        MutableLiveData<ArrayList<PlaceModel>>()
    }
    var nearByListFirstLoaded: Boolean = false

    // user liked list
    private var likedPlacesStringList = ArrayList<String>()
    val likedPlacesStringListLive: MutableLiveData<ArrayList<String>> by lazy {
        MutableLiveData<ArrayList<String>>()
    }
    private var likedPlacesList = ArrayList<PlaceModel>()
    val likedPlacesFinalList: MutableLiveData<ArrayList<PlaceModel>> by lazy {
        MutableLiveData<ArrayList<PlaceModel>>()
    }
    var likedListFirstLoaded: Boolean = false

    // category list
    val categoryPlacesList: MutableLiveData<ArrayList<PlaceModel>> by lazy {
        MutableLiveData<ArrayList<PlaceModel>>()
    }

    // messages
    val error: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val place_ranked: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    // ------------------------------------ PLACES LISTS ------------------------------------------
    private inner class NearbyPlacesStringListFetcherObserver : DefaultObserver<ArrayList<String>>() {
        override fun onComplete() {
            Log.i("Places", "NearbyPlacesStringListFetcherObserver Observer - On Complete...")
            // isLoading.postValue(false)
        }
        override fun onError(exception: Throwable) {
            Log.i("Places", "NearbyPlacesStringListFetcherObserver Observer - On Error: " + exception.message)
            // isLoading.postValue(false)
            // logInTaskStatus.postValue(false)
            error.postValue("NearbyPlacesStringListFetcherObserver Observer - On Error: " + exception.message)
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

    private inner class RankPlaceObserver : DefaultObserver<String>() {
        override fun onComplete() {
            Log.i("Places", "NearbyPlacesStringListFetcherObserver Observer - On Complete...")
        }
        override fun onError(exception: Throwable) {
            Log.i("Places", "RankPlace Observer - On Error: " + exception.message)
            error.postValue("RankPlace Observer - On Error: " + exception.message)
        }
        override fun onNext(value: String) {
            Log.i("Places", "RankPlace Observer - On Next message: $value")
            place_ranked.postValue(value)
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
            // Log.i("Places", "trying to fetch place by id.... with id:{$id}")
            suspendCoroutine<Unit> { continuation ->
                placesClient.fetchPlace(request)
                    .addOnSuccessListener { response: FetchPlaceResponse ->
                        // Log.i("Places", "fetch place data success!")
                        val place = response.place
                        googlePlace = place
                        // Log.i("Places", "trying to fetch photo!")
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
                                    // Log.i("Places", "fetching photo success!")
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
        this.updateUseProfileByRateUseCase.execute(RankPlaceObserver(), user_id, place_id, rank)
    }

    // ------------------------------------ USER ----------------------------------------------
    fun invokeGetUser() {
        viewModelScope.launch {
            getUserRoutine()
        }
    }

    suspend fun getUserRoutine() = withContext(Dispatchers.IO) {
        val user = FirebaseAuth.getInstance().currentUser?.uid?.let { userRepository.getUser(it) }
        userModel.postValue(user)
        //getLikedPlacesStringList()
    }

    fun saveLikedPlace(place_id: String, rank: Int) {
        viewModelScope.launch {
            val user_id = FirebaseAuth.getInstance().currentUser?.uid
            Log.i("Places", "likedPlacesStringList : user id: $user_id")
            Log.i("Places", "Saving user id: $user_id, at place id: $place_id")
            user_id?.let { SavedRankedPlaceModel(saved_id = 0, user_id = it, place_id = place_id, liked = 1, rank = rank) }?.let {
                savedRankedPlacesRepository.insertSavedPlace(
                    it
                )
            }
        }
    }

    fun invokeGetLikedPlacesList() {
        viewModelScope.launch {
            getLikedPlacesStringList()
        }
    }

    suspend fun getLikedPlacesStringList() = withContext(Dispatchers.IO) {
        val user_id = FirebaseAuth.getInstance().currentUser?.uid
        Log.i("Places", "get - likedPlacesStringList : user id: $user_id")
        val list = user_id?.let { savedRankedPlacesRepository.getLikedPlaces(user_id) } as ArrayList<String>?
        if (list != null) {
            Log.i("Places", "get - likedPlacesStringList : size of list:" + list.size)
        }
        // Log.i("Places", "get - likedPlacesStringList : list: $list")
        likedPlacesStringListLive.postValue(list)
    }

    fun getLikedPlacesList() {
        viewModelScope.launch {
            likedPlacesListCoRoutine()
        }
    }

    suspend fun likedPlacesListCoRoutine() {
        Log.i("Places", "likedPlacesListRoutine : starting to fetch liked places by id")

        for (place_id in this.likedPlacesStringListLive.value!!) {
            var bitmap: Bitmap
            var googlePlace: Place
            request = place_id.let { FetchPlaceRequest.newInstance(it, placeFields) }
            Log.i("Places", "likedPlacesListRoutine : trying to fetch place by id.... with id:{$place_id}")
            suspendCoroutine<Unit> { continuation ->
                placesClient.fetchPlace(request)
                    .addOnSuccessListener { response: FetchPlaceResponse ->
                        Log.i("Places", "likedPlacesListRoutine : fetch place data success!")
                        val place = response.place
                        googlePlace = place
                        Log.i("Places", "likedPlacesListRoutine : trying to fetch photo!")
                        // Get the photo metadata.
                        val metada = googlePlace.photoMetadatas
                        if (metada == null || metada.isEmpty()) {
                            Log.i("Places", "likedPlacesListRoutine : No photo metadata.")
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
                                    Log.i("Places", "likedPlacesListRoutine : fetching photo success!")
                                    // TODO test!
                                    bitmap = bitmapw
                                    this.likedPlacesList.add(PlaceModel(googlePlace, true, 5, bitmap))
                                    continuation.resume(Unit)
                                }.addOnFailureListener { exception: Exception ->
                                    if (exception is ApiException) {
                                        photoLoading.postValue(false)
                                        Log.e(
                                            ContentValues.TAG,
                                            "Place not found: ${exception.message}"
                                        )
                                        Log.i("Places", "likedPlacesListRoutine : fetching photo failed")
                                        error.postValue(exception.message)
                                    }
                                }
                        } else {
                            Log.i("Places", "likedPlacesListRoutine : fetching photo failed")
                            val w: Int = 150
                            val h: Int = 150
                            val conf = Bitmap.Config.ARGB_8888 // see other conf types
                            val bmp =
                                Bitmap.createBitmap(w, h, conf) // this creates a MUTABLE bitmap
                            bitmap = bmp
                            this.likedPlacesList.add(PlaceModel(googlePlace, true, 5, bitmap))
                            continuation.resume(Unit)
                        }
                    }.addOnFailureListener { exception: Exception ->
                        if (exception is ApiException) {
                            Log.e(ContentValues.TAG, "likedPlacesListRoutine : Place not found: ${exception.message}")
                            // val statusCode = exception.statusCode
                            error.postValue(exception.message)
                        }
                    }
            }
        }
        Log.i("Places", "likedPlacesListRoutine : size of list:" + likedPlacesList.size)
        this.likedPlacesFinalList.postValue(likedPlacesList)
    }
}
