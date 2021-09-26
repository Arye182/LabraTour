package com.example.labratour.presentation.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.labratour.R
import com.example.labratour.presentation.ui.login.LoginActivity
import com.example.labratour.presentation.viewmodel.UserHomeViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.header_navigation_drawer.*

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var homeViewModel: UserHomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.homeViewModel = (activity as HomeActivity?)?.userHomeViewModel!!
        // this.homeViewModel.getUser()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        this.homeViewModel.getCurrentUserID()
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
}
