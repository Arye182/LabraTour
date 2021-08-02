package com.example.labratour.presentation.ui.home

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.labratour.R
import com.example.labratour.presentation.viewmodel.UserHomeViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_place.*

class PlaceResultFragment : Fragment(R.layout.fragment_place) {
    private lateinit var homewViewModel: UserHomeViewModel
    // Define a Place ID.
    private lateinit var placeId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        val args: PlaceResultFragmentArgs by navArgs()
        super.onCreate(savedInstanceState)
        placeId = args.id
        Log.i("Places", "onCreate with id: $placeId")
        homewViewModel = (activity as HomeActivity?)?.userHomeViewModel!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homewViewModel.getPlaceById(placeId)
        url_place_button.setOnClickListener { onClickUrl(view) }
        call_place_button.setOnClickListener { onClickCall() }
        like_place_button.setOnClickListener { onClickLike() }
        share_place_button.setOnClickListener { onClickShare() }

        this.homewViewModel.photoLoading.observe(viewLifecycleOwner, { onPhotoLoadingChanged(view) })
        this.homewViewModel.place.observe(viewLifecycleOwner, { onPlaceChanged(view) })
        this.homewViewModel.photoBitmap.observe(viewLifecycleOwner, { onBitampChanged(view) })
        this.homewViewModel.error.observe(viewLifecycleOwner, { onErrorChanged(view) })
    }

    private fun onErrorChanged(view: View) {
        Snackbar.make(view, this.homewViewModel.error.value.toString(), Snackbar.LENGTH_SHORT)
            .setBackgroundTint(resources.getColor(R.color.error)).show()
    }

    private fun onBitampChanged(view: View) {
        place_img.setImageBitmap(this.homewViewModel.photoBitmap.value)
        place_progress_bar.visibility = View.GONE
    }

    private fun onPlaceChanged(view: View) {
        // update place in fragment
        place_id_tv.text = (homewViewModel.place.value?.id).toString()
        Log.i("Places", "phone of place: " + (homewViewModel.place.value?.phoneNumber).toString())
        if (homewViewModel.place.value?.phoneNumber == null) {
            place_phone_tv.text = "phone not available"
        } else {
            place_phone_tv.text = (homewViewModel.place.value?.phoneNumber).toString()
        }
        // place_phone_tv.text = (homewViewModel.place.value?.phoneNumber).toString()
        place_name_tv.text = (homewViewModel.place.value?.name).toString()
        place_address_tv.text = (homewViewModel.place.value?.address).toString()
        // place_opening_hours_tv.text = (place.openingHours).toString()
        if (homewViewModel.place.value?.isOpen == true) {
            place_is_open_tv.text = "Opened!"
            place_is_open_tv.setTextColor(Color.GREEN)
        } else if (homewViewModel.place.value?.isOpen == false) {
            place_is_open_tv.text = "Closed!"
            place_is_open_tv.setTextColor(Color.RED)
        } else {
            place_is_open_tv.text = "Not Available"
        }
    }

    private fun onPhotoLoadingChanged(view: View) {
        if (homewViewModel.photoLoading.value == false) {
            // hide the photo progress bar!
            place_progress_bar.visibility = View.GONE
        }
    }

    private fun onClickShare() {
        TODO("Not yet implemented")
    }

    private fun onClickLike() {
        // open dialog that allows user to rank the place from 1-5
    }

    // user click call and forward to phone on android
    private fun onClickCall() {
        val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", homewViewModel.getPhoneNumber(), null))
        startActivity(intent)
    }

    // what happens when user click on url button
    private fun onClickUrl(view: View) {
        val url: String = homewViewModel.place.value?.websiteUri.toString()
        if (url != null) {
            // TODO fix uri bug http://
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"))
            startActivity(browserIntent)
        } else {
            Snackbar.make(view, "Url Not Available, Sorry!", Snackbar.LENGTH_SHORT)
                .setBackgroundTint(resources.getColor(R.color.error)).show()
        }
    }

    // -- fragment functions --
    override fun onPause() {
        super.onPause()
        Log.i("Places", "onPause")
    }

    override fun onResume() {
        super.onResume()
        Log.i("Places", "onResume")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("Places", "onDestroy")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.i("Places", "onDestroyView")
    }
}
