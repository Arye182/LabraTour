package com.example.labratour.presentation.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.labratour.R
import com.example.labratour.presentation.ui.adapters.BigPlaceCardRecyclerAdapter
import com.example.labratour.presentation.utils.Constants
import com.example.labratour.presentation.viewmodel.LocationViewModel
import com.example.labratour.presentation.viewmodel.UserHomeViewModel
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_places_full_list.*

class PlacesListFragment : Fragment(R.layout.fragment_places_full_list), BigPlaceCardRecyclerAdapter.OnItemClickListener {
    private lateinit var frag_view: View
    private lateinit var category: String
    private var categoryPlacesLoaded: Boolean = false
    private lateinit var homeViewModel: UserHomeViewModel
    private lateinit var locationViewModel: LocationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args: PlacesListFragmentArgs by navArgs()
        category = args.subCategory
        this.homeViewModel = (activity as HomeActivity?)?.userHomeViewModel!!
        this.locationViewModel = (activity as HomeActivity?)?.locationViewModel!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // update the fragment title to the category's title
        if (category != "liked_places") {
            (activity as HomeActivity).toolbar.title = Constants.typesMap[category]
        } else {
            (activity as HomeActivity).toolbar.title = "Your Liked Places"
        }
        this.frag_view = view
        // view models observation
        if (!categoryPlacesLoaded) {
            category_places_list_recycler_view.visibility = View.GONE
            category_places_list_progress_bar.visibility = View.VISIBLE
        }
    }

    override fun onStart() {
        super.onStart()
        if (this.category != "liked_places") {
            this.homeViewModel.categoryPlacesList.observe(
                viewLifecycleOwner,
                { onCategoryPlacesListChanged(frag_view) }
            )
        } else {
            loadLikedPlacesList()
        }
    }

    override fun onResume() {
        super.onResume()
        //updatePlacesRoutine()
    }

    fun loadLikedPlacesList() {

        if (this.homeViewModel.likedPlaceModelList.size > 0) {
            category_places_list_recycler_view.adapter = BigPlaceCardRecyclerAdapter(this.homeViewModel.likedPlaceModelList, this)
            category_places_list_recycler_view.layoutManager =
                LinearLayoutManager(activity as HomeActivity, LinearLayoutManager.VERTICAL, false)
            category_places_list_recycler_view.setHasFixedSize(true)
            category_places_list_progress_bar.visibility = View.GONE
            category_places_list_recycler_view.visibility = View.VISIBLE
            this.categoryPlacesLoaded = true
        }

    }

    private fun onCategoryPlacesListChanged(fragView: View) {
        category_places_list_recycler_view.adapter = this.homeViewModel.categoryPlacesList.value?.let {
            BigPlaceCardRecyclerAdapter(
                it, this
            )
        }
        category_places_list_recycler_view.layoutManager =
            LinearLayoutManager(activity as HomeActivity, LinearLayoutManager.VERTICAL, false)
        category_places_list_recycler_view.setHasFixedSize(true)
        category_places_list_progress_bar.visibility = View.GONE
        category_places_list_recycler_view.visibility = View.VISIBLE
        this.categoryPlacesLoaded = true
    }

    override fun onItemClick(position: Int) {
        TODO("Not yet implemented")
    }

//    // TODO - viewmodel call to retrieve a category list of places
//    fun updatePlacesRoutine() {
//        // update lists!
//        this.homeViewModel.categoryPlacesList.value?.clear()
//        categoryPlacesLoaded = false
//        // get current lat long
//        val lat = this.locationViewModel.getLocationData().value?.latitude.toString()
//        val long = this.locationViewModel.getLocationData().value?.longitude.toString()
//        //this.homeViewModel.updateCategoryList(this.category, lat, long)
//        //
//        category_places_list_recycler_view.visibility = View.GONE
//        category_places_list_progress_bar.visibility = View.VISIBLE
//        categoryPlacesLoaded = false
//    }
}
