package com.example.labratour.presentation.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.labratour.R
import com.example.labratour.presentation.model.data.PlaceModel
import com.example.labratour.presentation.ui.adapters.SmallPlaceCardRecyclerAdapter
import com.example.labratour.presentation.ui.login.LoginActivity
import com.example.labratour.presentation.viewmodel.UserHomeViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment(R.layout.fragment_profile), SmallPlaceCardRecyclerAdapter.OnItemClickListener {

    private lateinit var homeViewModel: UserHomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.homeViewModel = (activity as HomeActivity?)?.userHomeViewModel!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button_logout.setOnClickListener { logOut() }
        button_delete_saved_places.setOnClickListener { deleteSavedPlaces() }
        this.homeViewModel.likedPlaceModelListLiveData.observe(viewLifecycleOwner, { updateUser() })
        updateUser()
        Log.i("Places", "ProfileFragment onViewCreated")
    }

    fun logOut() {
        FirebaseAuth.getInstance().signOut()
        val intent = Intent(activity, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        activity?.finish()
    }

    fun deleteSavedPlaces() {
        liked_places_list_progress_bar.visibility = View.VISIBLE
        this.homeViewModel.deleteUserLikedPlaces()
    }

    private fun updateUser() {
        // first name
        profile_first_name_tv.text = homeViewModel.userModelLiveData.value?.firstName
        profile_email_tv.text = homeViewModel.userModelLiveData.value?.email
        // last name
        profile_last_name_tv.text = homeViewModel.userModelLiveData.value?.lastName
        // user name -> details
        profile_username_tv.text = homeViewModel.userModelLiveData.value?.userName
        // user name -> title
        user_profile_title.text = homeViewModel.userModelLiveData.value?.userName
        // phone
        if (homeViewModel.userModelLiveData.value?.mobile.toString() == "0") {
            profile_phone_tv.text = "Phone Not Updated Yet"
        } else {
            profile_phone_tv.text = homeViewModel.userModelLiveData.value?.mobile.toString()
        }
        if (homeViewModel.userModelLiveData.value?.gender.toString() == "") {
            profile_gender_tv.text = "Gender Not Updated Yet"
        } else {
            profile_gender_tv.text = homeViewModel.userModelLiveData.value?.gender.toString()
        }

        if (this.homeViewModel.likedPlaceModelList.size > 0) {
            val size: Int = this.homeViewModel.likedPlaceModelListLiveData.value?.size!!
            Log.i("Places", "ProfileFragment $size")
            liked_places_recycler_view.adapter = SmallPlaceCardRecyclerAdapter(this.homeViewModel.likedPlaceModelList, this, LIKED_LIST_CODE)
            liked_places_recycler_view.layoutManager =
                LinearLayoutManager(activity as HomeActivity, LinearLayoutManager.HORIZONTAL, false)
            liked_places_recycler_view.setHasFixedSize(true)
            liked_places_no_places_message.visibility = View.GONE
            liked_places_list_progress_bar.visibility = View.GONE
            liked_places_recycler_view.visibility = View.VISIBLE
        } else if (this.homeViewModel.likedPlaceModelList.isEmpty()) {
            liked_places_no_places_message.visibility = View.VISIBLE
            liked_places_list_progress_bar.visibility = View.GONE
            liked_places_recycler_view.visibility = View.GONE
        } else {
            liked_places_no_places_message.visibility = View.GONE
            liked_places_list_progress_bar.visibility = View.VISIBLE
            liked_places_recycler_view.visibility = View.GONE
        }
    }

    override fun onItemClick(position: Int, code: Int) {
        var clickedPlaceItem: PlaceModel? = null
        var id: String = ""
        when (code) {
            NEARBY_LIST_CODE -> {
                clickedPlaceItem = homeViewModel.nearByPlaceModelListLiveData.value?.get(position)!!
                id = clickedPlaceItem.googlePlace.id!!
            }
            CUSTOMIZED_LIST_CODE -> {
                clickedPlaceItem = homeViewModel.customizedPlaceModelListLiveData.value?.get(position)!!
                id = clickedPlaceItem.googlePlace.id!!
            }
            LIKED_LIST_CODE -> {
                clickedPlaceItem = homeViewModel.likedPlaceModelListLiveData.value?.get(position)!!
                id = clickedPlaceItem.googlePlace.id!!
            }
        }
        // move to fragment of result of place!
        val action = ProfileFragmentDirections.actionProfileFragmentToPlaceResultFragment(id)
        findNavController().navigate(action)
    }
}
