package com.example.labratour.presentation.ui.home

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.GONE
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.labratour.R
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_search.*
import java.util.*

class SearchFragment : Fragment(R.layout.fragment_search) {

    private lateinit var id: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as HomeActivity).searchText.visibility = 1
        (activity as HomeActivity).toolbar.title = ""

        (activity as HomeActivity).searchText.setOnClickListener {
            var fieldlist: List<Place.Field> = Arrays.asList(
                Place.Field.ADDRESS,
                Place.Field.LAT_LNG,
                Place.Field.NAME,
                Place.Field.ID
            )
            var intent: Intent =
                Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fieldlist)
                    .build(activity as HomeActivity)
            startActivityForResult(intent, 100)
        }
        // TODO set all categories click listeners to new fragments on the same container and listener
        transportation.setOnClickListener(View.OnClickListener { categoryIntent("Transportation") })
        parks.setOnClickListener(View.OnClickListener { categoryIntent("Parks") })
        artandculture.setOnClickListener(View.OnClickListener { categoryIntent("Art & Culture") })
        finance.setOnClickListener(View.OnClickListener { categoryIntent("Finance") })
        foodanddrinks.setOnClickListener(View.OnClickListener { categoryIntent("Food & Drinks") })
        shopping.setOnClickListener(View.OnClickListener { categoryIntent("Shopping") })
        religion.setOnClickListener(View.OnClickListener { categoryIntent("Religion") })
        medical.setOnClickListener(View.OnClickListener { categoryIntent("Medical") })
        fun_sub.setOnClickListener(View.OnClickListener { categoryIntent("Fun") })
        sports.setOnClickListener(View.OnClickListener { categoryIntent("Sports") })
        general.setOnClickListener(View.OnClickListener { categoryIntent("General") })
    }

    override fun onPause() {
        super.onPause()
        (activity as HomeActivity).searchText.visibility = GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as HomeActivity).searchText.visibility = GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as HomeActivity).searchText.visibility = GONE
    }

    override fun onResume() {
        super.onResume()
        (activity as HomeActivity).searchText.visibility = 1
        (activity as HomeActivity).toolbar.title = ""
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK) {
            var place: Place? = data?.let { Autocomplete.getPlaceFromIntent(it) }
            if (place != null) {
                (activity as HomeActivity).searchText.setText(place.address)
                this.id = (place.id).toString()
                Log.i("Places", "search fragment id: ${this.id}")
                // move to result place page
                // move on to next step!
                val action =
                    SearchFragmentDirections.actionSearchFragmentToPlaceResultFragment(this.id)
                findNavController().navigate(action)
            }
        } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
            var status: Status? = data?.let { Autocomplete.getStatusFromIntent(it) }
            if (status != null) {
                view?.let {
                    status.statusMessage?.let { it1 ->
                        Snackbar.make(it, it1, Snackbar.LENGTH_SHORT)
                            .setBackgroundTint(resources.getColor(R.color.error)).show()
                    }
                }
            }
        }
    }

    fun categoryIntent(category_name: String) {
        view?.let {
            Snackbar.make(it, "Button $category_name Clicked", Snackbar.LENGTH_SHORT)
                .setBackgroundTint(resources.getColor(R.color.success)).show()
        }
        // TODO open a dialog of sub categories?
        // or navigate to another screen of sub categories?
        // move to fragment of result of place!
        val action = SearchFragmentDirections.actionSearchFragmentToSubCategoryFragment(category_name)
        findNavController().navigate(action)
    }
}
