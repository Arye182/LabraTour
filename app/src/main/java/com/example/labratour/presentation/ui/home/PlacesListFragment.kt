package com.example.labratour.presentation.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.labratour.R
import com.example.labratour.presentation.model.data.PlaceModel
import com.example.labratour.presentation.ui.adapters.BigPlaceCardRecyclerAdapter
import com.example.labratour.presentation.utils.Constants
import com.example.labratour.presentation.viewmodel.LocationViewModel
import com.example.labratour.presentation.viewmodel.UserHomeViewModel
import kotlinx.android.synthetic.main.activity_home.*
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
        if (category == "liked_places") {
            (activity as HomeActivity).toolbar.title = "Your Liked Places"
        } else if (Constants.typesMap[category] != null) {
            (activity as HomeActivity).toolbar.title = Constants.typesMap[category]
        } else {
            (activity as HomeActivity).toolbar.title = category
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
        when (this.category) {
            "liked_places" -> {
                loadLikedPlacesList()
            }
            "Customized" -> {
                loadCustomizedPlacesList()
            }
            "Nearby" -> {
                loadNearbyPlacesList()
            }
            else -> {
                this.homeViewModel.categoryPlacesListLiveData.observe(
                    viewLifecycleOwner,
                    { onCategoryPlacesListChanged(frag_view) }
                )
                this.homeViewModel.categoryPlacesCoRoutine(
                    this.category,
                    this.locationViewModel.getLocationData().value?.latitude.toString(),
                    this.locationViewModel.getLocationData().value?.longitude.toString(),
                )
            }
        }
    }

    fun loadLikedPlacesList() {
        if (this.homeViewModel.likedPlaceModelListLiveData.value?.size!! > 0) {
            category_places_list_recycler_view.adapter = BigPlaceCardRecyclerAdapter(this.homeViewModel.likedPlaceModelListLiveData.value!!, this, LIKED_LIST_CODE)
            category_places_list_recycler_view.layoutManager =
                LinearLayoutManager(activity as HomeActivity, LinearLayoutManager.VERTICAL, false)
            category_places_list_recycler_view.setHasFixedSize(true)
            category_places_list_progress_bar.visibility = View.GONE
            category_places_list_recycler_view.visibility = View.VISIBLE
            this.categoryPlacesLoaded = true
        }
    }

    fun loadNearbyPlacesList() {
        if (this.homeViewModel.nearByPlaceModelListLiveData.value?.size!! > 0) {
            category_places_list_recycler_view.adapter = BigPlaceCardRecyclerAdapter(this.homeViewModel.nearByPlaceModelListLiveData.value!!, this, NEARBY_LIST_CODE)
            category_places_list_recycler_view.layoutManager =
                LinearLayoutManager(activity as HomeActivity, LinearLayoutManager.VERTICAL, false)
            category_places_list_recycler_view.setHasFixedSize(true)
            category_places_list_progress_bar.visibility = View.GONE
            category_places_list_recycler_view.visibility = View.VISIBLE
            this.categoryPlacesLoaded = true
        }
    }

    fun loadCustomizedPlacesList() {
        if (this.homeViewModel.customizedPlaceModelListLiveData.value?.size!! > 0) {
            category_places_list_recycler_view.adapter = BigPlaceCardRecyclerAdapter(this.homeViewModel.customizedPlaceModelListLiveData.value!!, this, CUSTOMIZED_LIST_CODE)
            category_places_list_recycler_view.layoutManager =
                LinearLayoutManager(activity as HomeActivity, LinearLayoutManager.VERTICAL, false)
            category_places_list_recycler_view.setHasFixedSize(true)
            category_places_list_progress_bar.visibility = View.GONE
            category_places_list_recycler_view.visibility = View.VISIBLE
            this.categoryPlacesLoaded = true
        }
    }

    fun onCategoryPlacesListChanged(fragView: View) {
        if (this.homeViewModel.categoryPlacesListLiveData.value?.size!! > 0) {
            category_places_list_recycler_view.adapter =
                BigPlaceCardRecyclerAdapter(this.homeViewModel.categoryPlacesListLiveData.value!!, this, CATEGORY_LIST_CODE)
        }
        category_places_list_recycler_view.layoutManager =
            LinearLayoutManager(activity as HomeActivity, LinearLayoutManager.VERTICAL, false)
        category_places_list_recycler_view.setHasFixedSize(true)
        category_places_list_progress_bar.visibility = View.GONE
        category_places_list_recycler_view.visibility = View.VISIBLE
    }

    override fun onItemClick(position: Int, code: Int) {
        var clickedPlaceItem: PlaceModel? = null
        var id: String = ""
        when (code) {
            NEARBY_LIST_CODE -> {
                clickedPlaceItem = homeViewModel.nearByPlaceModelListLiveData.value?.get(position)!!
                id = clickedPlaceItem.id
            }
            CUSTOMIZED_LIST_CODE -> {
                clickedPlaceItem = homeViewModel.customizedPlaceModelListLiveData.value?.get(position)!!
                id = clickedPlaceItem.id
            }
            LIKED_LIST_CODE -> {
                clickedPlaceItem = homeViewModel.likedPlaceModelListLiveData.value?.get(position)!!
                id = clickedPlaceItem.id
            }
        }
        // move to fragment of result of place!
        val action = PlacesListFragmentDirections.actionPlacesListFragmentToPlaceResultFragment(id)
        findNavController().navigate(action)
    }
}
