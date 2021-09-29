package com.example.labratour.presentation.ui.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.labratour.R
import com.example.labratour.presentation.ui.adapters.SmallPlaceCardRecyclerAdapter
import com.example.labratour.presentation.viewmodel.UserHomeViewModel
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment(R.layout.fragment_profile), SmallPlaceCardRecyclerAdapter.OnItemClickListener {

    private lateinit var homeViewModel: UserHomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.homeViewModel = (activity as HomeActivity?)?.userHomeViewModel!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateUser()
        Log.i("Places", "ProfileFragment onViewCreated")
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
//        username_drawer_header.text = homeViewModel.userModel.value?.userName
        val size: Int = this.homeViewModel.likedPlaceModelListLiveData.value?.size!!
        Log.i("Places", "ProfileFragment $size")

        liked_places_recycler_view.adapter = this.homeViewModel.likedPlaceModelListLiveData.value?.let {
            SmallPlaceCardRecyclerAdapter(it, this)
        }
        liked_places_recycler_view.layoutManager =
            LinearLayoutManager(activity as HomeActivity, LinearLayoutManager.HORIZONTAL, false)
        liked_places_recycler_view.setHasFixedSize(true)
        liked_places_no_places_message.visibility = View.GONE
        liked_places_list_progress_bar.visibility = View.GONE
        liked_places_recycler_view.visibility = View.VISIBLE
    }

    override fun onItemClick(position: Int) {
        TODO("Not yet implemented")
    }
}
