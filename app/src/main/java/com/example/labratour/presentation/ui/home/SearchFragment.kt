package com.example.labratour.presentation.ui.home

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.labratour.R
import com.example.labratour.presentation.ui.login.SignUpFragmentOneDirections
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_search.*
import java.util.*

class SearchFragment : Fragment(R.layout.fragment_search) {

    private lateinit var id : String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        edit_text_place_to_search.setOnClickListener {
            var fieldlist: List<Place.Field> = Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.NAME, Place.Field.ID)
            var intent: Intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fieldlist).build(activity as HomeActivity)
            startActivityForResult(intent, 100)
        }
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
                edit_text_place_to_search.setText(place.address)
                this.id = (place.id).toString()
                Log.i("Places", "search fragment id: ${this.id}")
                // move to result place page
                // move on to next step!
                val action = SearchFragmentDirections.actionSearchFragmentToPlaceResultFragment(this.id)
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
}
