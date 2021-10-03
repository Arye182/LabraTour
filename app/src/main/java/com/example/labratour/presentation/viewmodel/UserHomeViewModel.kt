package com.example.labratour.presentation.viewmodel

import android.content.ContentValues
import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.labratour.domain.Entity.NearbyPlaceEntity
import com.example.labratour.domain.Results
import com.example.labratour.domain.useCases.DefaultObserver
import com.example.labratour.domain.useCases.GetNearbyPlacesAllTypesUseCase
import com.example.labratour.domain.useCases.GetNearbyPlacesByTypeUseCase
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
import kotlinx.coroutines.*
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class UserHomeViewModel(
    private val placesClient: PlacesClient,
    private val updateUseProfileByRateUseCase: UpdateUserProfileByRateUseCase,
    private val getNearbyPlacesUseCase: GetNearbyPlacesAllTypesUseCase,
    private val getNearbyPlacesByTypeUseCase: GetNearbyPlacesByTypeUseCase,
    private val userRepository: UserRepository,
    private val placeCacheRepository: PlacesRepository,
    private val savedRankedPlacesRepository: SavedRankedPlacesRepository
) : ViewModel() {
    //  ----------------------------------- fetch single place ------------------------------------
    private lateinit var request: FetchPlaceRequest
    val place: MutableLiveData<Place> by lazy {
        MutableLiveData<Place>()
    }
    val photoBitmap: MutableLiveData<Bitmap> by lazy {
        MutableLiveData<Bitmap>()
    }
    val photoLoading: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    private var temp_list = ArrayList<PlaceModel>()
    val placeFields = listOf(
        Place.Field.ADDRESS,
        Place.Field.ADDRESS_COMPONENTS,
        Place.Field.BUSINESS_STATUS,
        Place.Field.ID,
        Place.Field.LAT_LNG,
        Place.Field.NAME,
        Place.Field.OPENING_HOURS,
        Place.Field.PHONE_NUMBER,
        Place.Field.PHOTO_METADATAS,
        Place.Field.PLUS_CODE,
        Place.Field.PRICE_LEVEL,
        Place.Field.RATING,
        Place.Field.TYPES,
        Place.Field.USER_RATINGS_TOTAL,
        Place.Field.VIEWPORT,
        Place.Field.UTC_OFFSET,
        Place.Field.WEBSITE_URI,
    )
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
    private fun fetchPhoto() {
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
                .setMaxHeight(750) // Optional.
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

    //  ----------------------------------- customized list ---------------------------------------
    private val _customizedPlaceModelListLiveData: MutableLiveData<ArrayList<PlaceModel>> by lazy {
        MutableLiveData<ArrayList<PlaceModel>>()
    }
    var customizedPlaceModelListLiveData: LiveData<ArrayList<PlaceModel>> = _customizedPlaceModelListLiveData
    private suspend fun customizedPlacesListCoRoutine() {
        val list = ArrayList<String>()
        for (i in 0 until 10) {
            val id = "ChIJcURkgp492jERS4A65dNZuts"
            list.add(id)
        }
        Log.i("Places", "size of nearby ids list:" + list.size)
        this._customizedPlaceModelListLiveData.postValue(idsToPlaceModelCoRoutine(list))
    }

    //  ----------------------------------- nearby list -------------------------------------------
    private val _nearByPlaceModelListLiveData: MutableLiveData<ArrayList<PlaceModel>> by lazy {
        MutableLiveData<ArrayList<PlaceModel>>()
    }
    var nearByPlaceModelListLiveData: LiveData<ArrayList<PlaceModel>> = _nearByPlaceModelListLiveData
    private inner class NearbyPlacesObserver : DefaultObserver<NearbyPlaceEntity>() {
        override fun onComplete() {
            Log.i("Places", "NearbyPlacesStringListFetcherObserver Observer - On Complete...")
        }
        override fun onError(exception: Throwable) {
            Log.i("Places", "NearbyPlacesStringListFetcherObserver Observer - On Error: " + exception.message)
            error.postValue("NearbyPlacesStringListFetcherObserver Observer - On Error: " + exception.message)
        }
        override fun onNext(value: NearbyPlaceEntity) {
            Log.i("Places", "NearbyPlacesStringListFetcherObserver Observer - On Next...")
            Log.i("Places", "NearbyPlacesStringListFetcherObserver Observer - On Next... Value Size = ${value.results.size}")
            Log.i("Places", "NearbyPlacesStringListFetcherObserver Observer - On Next... list = $value")
            viewModelScope.launch {
                _nearByPlaceModelListLiveData.postValue(nearbyPlaceEntityToPlaceModelCoRoutine(value))
            }
        }
    }
    fun nearbyPlacesCoRoutine(lat: String, long: String) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.i("Places", "Co-Routine - Starting To Update Near By Places List")
            getNearbyPlacesUseCase.execute(NearbyPlacesObserver(), lat, long)
        }
    }

    //  ------------------------------- category places list --------------------------------------
    private val _categoryPlacesListLiveData: MutableLiveData<ArrayList<PlaceModel>> by lazy {
        MutableLiveData<ArrayList<PlaceModel>>()
    }
    var categoryPlacesListLiveData: LiveData<ArrayList<PlaceModel>> = _categoryPlacesListLiveData
    private inner class NearbyPlacesByTypeObserver : DefaultObserver<NearbyPlaceEntity>() {
        override fun onComplete() {
            Log.i("Places", "NearbyPlacesByTypeObserver  - On Complete...")
        }
        override fun onError(exception: Throwable) {
            Log.i("Places", "NearbyPlacesByTypeObserver  - On Error: " + exception.message)
            error.postValue("NearbyPlacesByTypeObserver  - On Error: " + exception.message)
        }
        override fun onNext(value: NearbyPlaceEntity) {
            Log.i("Places", "NearbyPlacesByTypeObserver - On Next...")
            Log.i("Places", "NearbyPlacesByTypeObserver - On Next... Value Size = ${value.results.size}")
            Log.i("Places", "NearbyPlacesByTypeObserver - On Next... list = $value")
            viewModelScope.launch {
                _categoryPlacesListLiveData.postValue(nearbyPlaceEntityToPlaceModelCoRoutine(value))
            }
        }
    }
    fun categoryPlacesCoRoutine(category: String, lat: String, long: String) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.i("Places", "Co-Routine - Starting To Update Category Places List")
            getNearbyPlacesByTypeUseCase.execute(NearbyPlacesByTypeObserver(), lat, long, category)
        }
    }

    //  ----------------------------------- userLiked list ----------------------------------------
    private val _likedPlaceModelListLiveData: MutableLiveData<ArrayList<PlaceModel>> by lazy {
        MutableLiveData<ArrayList<PlaceModel>>()
    }
    var likedPlaceModelListLiveData: LiveData<ArrayList<PlaceModel>> = _likedPlaceModelListLiveData
    private suspend fun getLikedPlacesStringList() = withContext(Dispatchers.IO) {
        val user_id = FirebaseAuth.getInstance().currentUser?.uid
        Log.i("Places", "get - likedPlacesStringList : user id: $user_id")
        val list = user_id?.let { savedRankedPlacesRepository.getLikedPlaces(user_id) } as ArrayList<String>?
        if (list != null) {
            Log.i("Places", "get - likedPlacesStringList : size of list:" + list.size)
            _likedPlaceModelListLiveData.postValue(idsToPlaceModelCoRoutine(list))
        }
    }

    // ------------------------------------ USER ----------------------------------------------
    private val _userModelLiveData: MutableLiveData<UserModel> by lazy {
        MutableLiveData<UserModel>()
    }
    var userModelLiveData: LiveData<UserModel> = _userModelLiveData
    private suspend fun getUserRoutine() = withContext(Dispatchers.IO) {
        val user = FirebaseAuth.getInstance().currentUser?.uid?.let { userRepository.getUser(it) }
        if (user != null) {
            _userModelLiveData.postValue(user)
        }
    }
    fun saveLikedPlace(place_id: String, rank: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val user_id = FirebaseAuth.getInstance().currentUser?.uid
            Log.i("Places", "likedPlacesStringList : user id: $user_id")
            Log.i("Places", "Saving user id: $user_id, at place id: $place_id")
            user_id?.let { SavedRankedPlaceModel(saved_id = 0, user_id = it, place_id = place_id, liked = 1, rank = rank) }?.let {
                savedRankedPlacesRepository.insertSavedPlace(
                    it
                )
            }
            getLikedPlacesStringList()
        }
    }
    fun deleteUserLikedPlaces() {
        viewModelScope.launch(Dispatchers.IO) {
            _likedPlaceModelListLiveData.value?.clear()
            userModelLiveData.value?.let { savedRankedPlacesRepository.deleteAllSavedPlaces(it.id) }
            getLikedPlacesStringList()
        }
    }
    fun rankPlace(user_id: String, place_id: String, rank: Int) {
        this.updateUseProfileByRateUseCase.execute(RankPlaceObserver(), user_id, place_id, rank)
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
    val place_ranked: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    // ------------------------------------ General ----------------------------------------------
    // fetch place by id
    val error: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    fun getPhoneNumber(): String? {
        return this.place.value?.phoneNumber
    }
    suspend fun idsToPlaceModelCoRoutine(ids: List<String>): ArrayList<PlaceModel> {
        var temp_list_temp = ArrayList<PlaceModel>()

        for (place_id in ids) {
            var bitmap: Bitmap
            var googlePlace: Place
            request = place_id.let { FetchPlaceRequest.newInstance(it, placeFields) }
            // Log.i("Places", "iD's To Places Routine : trying to fetch place by id.... with id:{$place_id}")
            suspendCoroutine<Unit> { continuation ->
                placesClient.fetchPlace(request)
                    .addOnSuccessListener { response: FetchPlaceResponse ->
                        // Log.i("Places", "iD's To Places Routine : fetch place data success!")
                        val place = response.place

                        googlePlace = place
                        // Log.i("Places", "iD's To Places Routine : trying to fetch photo!")
                        // Get the photo metadata.
                        val metada = googlePlace.photoMetadatas
                        if (metada == null || metada.isEmpty()) {
                            Log.i("Places", "iD's To Places Routine : No photo metadata.")
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
                                    // Log.i("Places", "iD's To Places Routine : fetching photo success!")
                                    bitmap = bitmapw
                                    temp_list_temp.add(PlaceModel(place_id, googlePlace, true, 0, bitmap))
                                    continuation.resume(Unit)
                                }.addOnFailureListener { exception: Exception ->
                                    if (exception is ApiException) {
                                        photoLoading.postValue(false)
                                        Log.e(
                                            ContentValues.TAG,
                                            "Place not found: ${exception.message}"
                                        )
                                        Log.i("Places", "iD's To Places Routine : fetching photo failed")
                                        error.postValue(exception.message)
                                    }
                                }
                        } else {
                            Log.i("Places", "iD's To Places Routine : fetching photo failed")
                            val w: Int = 150
                            val h: Int = 150
                            val conf = Bitmap.Config.ARGB_8888 // see other conf types
                            val bmp =
                                Bitmap.createBitmap(w, h, conf) // this creates a MUTABLE bitmap
                            bitmap = bmp
                            continuation.resume(Unit)
                        }
                    }.addOnFailureListener { exception: Exception ->
                        if (exception is ApiException) {
                            Log.e(ContentValues.TAG, "iD's To Places Routine : Place not found: ${exception.message}")
                            // val statusCode = exception.statusCode
                            error.postValue(exception.message)
                        }
                    }
            }
        }
        Log.i("Places", "iD's To Places Routine : size of list:" + temp_list.size)
        return temp_list_temp
    }
    suspend fun nearbyPlaceEntityToPlaceModelCoRoutine(entities: NearbyPlaceEntity): ArrayList<PlaceModel> {
        var temp_list_temp = ArrayList<PlaceModel>()
        for ((index, api_place) in entities.results.withIndex()) {
            var bitmap: Bitmap
            var googlePlace: Place
            request = FetchPlaceRequest.newInstance(api_place.placeId, placeFields)
            // Log.i("Places", "iD's To Places Routine : trying to fetch place by id.... with id:{$place_id}")
            suspendCoroutine<Unit> { continuation ->
                placesClient.fetchPlace(request)
                    .addOnSuccessListener { response: FetchPlaceResponse ->
                        // Log.i("Places", "iD's To Places Routine : fetch place data success!")
                        val place = response.place
                        googlePlace = place
                        // Log.i("Places", "iD's To Places Routine : trying to fetch photo!")
                        // Get the photo metadata.
                        val metada = googlePlace.photoMetadatas
                        if (metada == null || metada.isEmpty()) {
                            Log.i("Places", "iD's To Places Routine : No photo metadata.")
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
                                    // Log.i("Places", "iD's To Places Routine : fetching photo success!")
                                    bitmap = bitmapw
                                    temp_list_temp.add(PlaceModel(api_place.placeId, googlePlace, false, 0, bitmap))
                                    continuation.resume(Unit)
                                }.addOnFailureListener { exception: Exception ->
                                    if (exception is ApiException) {
                                        photoLoading.postValue(false)
                                        Log.e(
                                            ContentValues.TAG,
                                            "Place not found: ${exception.message}"
                                        )
                                        Log.i("Places", "iD's To Places Routine : fetching photo failed")
                                        error.postValue(exception.message)
                                    }
                                }
                        } else {
                            Log.i("Places", "iD's To Places Routine : fetching photo failed")
                            val w: Int = 150
                            val h: Int = 150
                            val conf = Bitmap.Config.ARGB_8888 // see other conf types
                            val bmp =
                                Bitmap.createBitmap(w, h, conf) // this creates a MUTABLE bitmap
                            bitmap = bmp
                            continuation.resume(Unit)
                        }
                    }.addOnFailureListener { exception: Exception ->
                        if (exception is ApiException) {
                            Log.e(ContentValues.TAG, "iD's To Places Routine : Place not found: ${exception.message}")
                            // val statusCode = exception.statusCode
                            error.postValue(exception.message)
                        }
                    }
            }
        }
        Log.i("Places", "iD's To Places Routine : size of list:" + temp_list.size)
        return temp_list_temp
    }

    // ----------------------------------- initialize ---------------------------------------------
    init {
        initializeViewModel()
    }
    private fun initializeViewModel() {
        // clear lists
        _customizedPlaceModelListLiveData.value?.clear()
        _nearByPlaceModelListLiveData.value?.clear()
        _likedPlaceModelListLiveData.value?.clear()

        // run updates
        viewModelScope.launch(Dispatchers.IO) {
            getUserRoutine()
        }
        viewModelScope.launch(Dispatchers.IO) {
            getLikedPlacesStringList()
        }
        viewModelScope.launch(Dispatchers.IO) {
            customizedPlacesListCoRoutine()
        }
    }
}
