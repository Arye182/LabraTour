package com.example.labratour.presentation.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.labratour.R
import com.example.labratour.presentation.ui.adapters.SmallPlaceCardRecyclerAdapter
import com.example.labratour.presentation.ui.login.LoginActivity
import com.example.labratour.presentation.viewmodel.UserHomeViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment(R.layout.fragment_profile), SmallPlaceCardRecyclerAdapter.OnItemClickListener {

    private lateinit var homeViewModel: UserHomeViewModel
    private var likedPlacesLoaded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.homeViewModel = (activity as HomeActivity?)?.userHomeViewModel!!
        // this.homeViewModel.getUser()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!likedPlacesLoaded) {
            liked_places_recycler_view.visibility = View.GONE
            liked_places_list_progress_bar.visibility = View.VISIBLE
        }
        Log.i("Places", "ProfileFragment onViewCreated")
    }

    override fun onStart() {
        super.onStart()
        // logout button on click listener
        button_logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(activity, LoginActivity::class.java))
            activity?.finish()
        }
        // observe the relevant user model from cache
        homeViewModel.userModel.observe(
            viewLifecycleOwner,
            { onUserModelChanged() }
        )
        homeViewModel.likedPlacesStringListLive.observe(
            viewLifecycleOwner,
            { onLikedPlacesStringListChange() }
        )
        homeViewModel.likedPlacesFinalList.observe(
            viewLifecycleOwner,
            { onLikedPlacesListChanged() }
        )
        // this.homeViewModel.invokeGetUser()
        // this.homeViewModel.invokeGetLikedPlacesList()
        updatePlacesRoutine()
    }

    override fun onResume() {
        super.onResume()
        Log.i("Places", "ProfileFragment onResume")
        if (liked_places_no_places_message.visibility == View.VISIBLE && this.homeViewModel.likedPlacesStringListLive.value?.size == 0) {
            return
        } else {
            liked_places_no_places_message.visibility = View.GONE
            liked_places_list_progress_bar.visibility = View.VISIBLE
            liked_places_recycler_view.visibility = View.GONE
            if (this.homeViewModel.likedListFirstLoaded) {
                updatePlacesRoutine()
            }
            this.homeViewModel.nearByListFirstLoaded = true
        }
        return
    }

    private fun onLikedPlacesStringListChange() {
        if (this.homeViewModel.likedPlacesStringListLive.value?.size == 0) run {
            liked_places_no_places_message.visibility = View.VISIBLE
            liked_places_list_progress_bar.visibility = View.GONE
            return
        } else {
            this.homeViewModel.getLikedPlacesList()
        }
    }

    private fun onLikedPlacesListChanged() {
        liked_places_recycler_view.adapter = this.homeViewModel.likedPlacesFinalList.value?.let {
            SmallPlaceCardRecyclerAdapter(it, this)
        }
        liked_places_recycler_view.layoutManager =
            LinearLayoutManager(activity as HomeActivity, LinearLayoutManager.HORIZONTAL, false)
        liked_places_recycler_view.setHasFixedSize(true)
        liked_places_no_places_message.visibility = View.GONE
        liked_places_list_progress_bar.visibility = View.GONE
        liked_places_recycler_view.visibility = View.VISIBLE
        this.likedPlacesLoaded = true
    }

    private fun onUserModelChanged() {
        // first name
        profile_first_name_tv.text = homeViewModel.userModel.value?.firstName
        profile_email_tv.text = homeViewModel.userModel.value?.email
        // last name
        profile_last_name_tv.text = homeViewModel.userModel.value?.lastName
        // user name -> details
        profile_username_tv.text = homeViewModel.userModel.value?.userName
        // user name -> title
        user_profile_title.text = homeViewModel.userModel.value?.userName
        // phone
        if (homeViewModel.userModel.value?.mobile.toString() == "0") {
            profile_phone_tv.text = "Phone Not Updated Yet"
        } else {
            profile_phone_tv.text = homeViewModel.userModel.value?.mobile.toString()
        }
        if (homeViewModel.userModel.value?.gender.toString() == "") {
            profile_gender_tv.text = "Gender Not Updated Yet"
        } else {
            profile_gender_tv.text = homeViewModel.userModel.value?.gender.toString()
        }
//        username_drawer_header.text = homeViewModel.userModel.value?.userName
    }

    fun updatePlacesRoutine() {
        // update lists!
        this.homeViewModel.likedPlacesFinalList.value?.clear()
        likedPlacesLoaded = false
        //
        this.homeViewModel.invokeGetLikedPlacesList()
        liked_places_recycler_view.visibility = View.GONE
        liked_places_list_progress_bar.visibility = View.VISIBLE
        likedPlacesLoaded = false
    }

    override fun onItemClick(position: Int) {
        TODO("Not yet implemented")
    }

    // --------------------        stoping and pausing destroying ---------------------------------
    override fun onPause() {
        super.onPause()
        clearLists()
        Log.i("Places", "ProfileFragment onPause")
    }

    override fun onStop() {
        super.onStop()
        clearLists()
        Log.i("Places", "ProfileFragment onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        clearLists()
        Log.i("Places", "ProfileFragment onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        // in case we destroy the frasgent we want to clear the list
        this.homeViewModel.likedListFirstLoaded = false
        clearLists()
        Log.i("Places", "ProfileFragment onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        clearLists()
        Log.i("Places", "ProfileFragment onDetach")
    }

    fun clearLists() {
        this.homeViewModel.likedPlacesFinalList.value?.clear()
        this.homeViewModel.likedPlacesStringListLive.value?.clear()
        likedPlacesLoaded = false
        this.homeViewModel.likedListFirstLoaded = false
    }
}
