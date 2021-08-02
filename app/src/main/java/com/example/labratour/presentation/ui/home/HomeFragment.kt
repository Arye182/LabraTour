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

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var homeViewModel: UserHomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.homeViewModel = (activity as HomeActivity?)?.userHomeViewModel!!
        this.homeViewModel.generatePlacesListForTest()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // logout button on click
        button_logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(activity, LoginActivity::class.java))
            activity?.finish()
        }
        this.homeViewModel.nearByPlacesList.observe(viewLifecycleOwner, { onNearByPlacesListChanged(view) })
    }

    private fun onNearByPlacesListChanged(view: View) {
        places_close_to_you_recycler_view.adapter = this.homeViewModel.nearByPlacesList.value?.let {
            SmallPlaceCardRecyclerAdapter(
                it
            )
        }
        places_close_to_you_recycler_view.layoutManager = LinearLayoutManager(activity as HomeActivity, LinearLayoutManager.HORIZONTAL, false)
        places_close_to_you_recycler_view.setHasFixedSize(true)
        nearby_places_list_progress_bar.visibility = View.GONE
    }

    // -- fragment functions --
    override fun onPause() {
        super.onPause()
        Log.i("Places", "HomeFragment onPause")
    }

    override fun onResume() {
        super.onResume()
        Log.i("Places", "HomeFragment onResume")
        nearby_places_list_progress_bar.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.i("Places", "HomeFragment onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        // in case we destroy the frasgent we want to clear the list
        this.homeViewModel.nearByPlacesList.value?.clear()
        Log.i("Places", "HomeFragment onDestroy")
    }
}
