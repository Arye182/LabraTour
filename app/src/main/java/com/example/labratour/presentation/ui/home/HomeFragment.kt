package com.example.labratour.presentation.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.labratour.R
import com.example.labratour.presentation.model.data.PlaceModel
import com.example.labratour.presentation.ui.adapters.SmallPlaceCardRecyclerAdapter
import com.example.labratour.presentation.ui.login.LoginActivity
import com.example.labratour.presentation.viewmodel.UserHomeViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var homeViewModel: UserHomeViewModel
    private lateinit var testList: List<PlaceModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.homeViewModel = (activity as HomeActivity?)?.userHomeViewModel!!
        this.testList = this.homeViewModel.generatePlacesListForTest()
        Log.i("Places", "size of list:" + testList.size)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        places_close_to_you_recycler_view.adapter = SmallPlaceCardRecyclerAdapter(this.testList)
        places_close_to_you_recycler_view.layoutManager = LinearLayoutManager(activity as HomeActivity, LinearLayoutManager.HORIZONTAL, false)
        places_close_to_you_recycler_view.setHasFixedSize(true)
        // logout button on click
        button_logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(activity, LoginActivity::class.java))
            activity?.finish()
        }
    }
}
