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
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_place.*

class PlaceResultFragment : Fragment(R.layout.fragment_place) {
    private lateinit var homeViewModel: UserHomeViewModel
    // Define a Place ID.
    private lateinit var placeId: String
    private var rank: Int = 0
    private var star_pressed_one: Boolean = false
    private var star_pressed_two: Boolean = false
    private var star_pressed_three: Boolean = false
    private var star_pressed_four: Boolean = false
    private var star_pressed_five: Boolean = false
    private var liked: Boolean = false
    private var user_id: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        val args: PlaceResultFragmentArgs by navArgs()
        super.onCreate(savedInstanceState)
        placeId = args.id
        Log.i("Places", "onCreate with id: $placeId")
        homeViewModel = (activity as HomeActivity?)?.userHomeViewModel!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel.getPlaceById(placeId)
        url_place_button.setOnClickListener { onClickUrl(view) }
        call_place_button.setOnClickListener { onClickCall() }
        like_place_button.setOnClickListener { onClickLike() }
        share_place_button.setOnClickListener { onClickShare() }

        // stars
        rank_place_one_star.setOnClickListener { onClickStar(1) }
        rank_place_two_star.setOnClickListener { onClickStar(2) }
        rank_place_three_star.setOnClickListener { onClickStar(3) }
        rank_place_four_star.setOnClickListener { onClickStar(4) }
        rank_place_five_star.setOnClickListener { onClickStar(5) }

        // rank buttons
        button_clear_rank.setOnClickListener { clearStars() }
        button_rank.setOnClickListener { rankPlace() }

        // vm
        this.homeViewModel.photoLoading.observe(viewLifecycleOwner, { onPhotoLoadingChanged(view) })
        this.homeViewModel.place.observe(viewLifecycleOwner, { onPlaceChanged(view) })
        this.homeViewModel.photoBitmap.observe(viewLifecycleOwner, { onBitampChanged(view) })
        this.homeViewModel.error.observe(viewLifecycleOwner, { onErrorChanged(view) })
        this.homeViewModel.place_ranked.observe(viewLifecycleOwner, { onPlaceRankedChanged(view) })
    }

    private fun onPlaceRankedChanged(view: View) {
        Snackbar.make(view, this.homeViewModel.place_ranked.value.toString(), Snackbar.LENGTH_SHORT)
            .setBackgroundTint(resources.getColor(R.color.success)).show()
    }

    private fun rankPlace() {
        val place_id = (homeViewModel.place.value?.id).toString()
        val user_id = FirebaseAuth.getInstance().currentUser?.uid
        // val user_id =
        Log.i("Places", "RANKPLACE:  $place_id, $user_id, $rank ")

        if (user_id != null) {
            homeViewModel.rankPlace(user_id, place_id, rank)
        }
    }

    private fun onClickStar(i: Int) {
        when (i) {
            1 -> {
                if (star_pressed_one) {
                    clearStars()
                } else {
                    rank_place_one_star.setImageResource(R.drawable.ic_baseline_star_rate_24)
                    star_pressed_one = true
                    rank = 1
                }
            }

            2 -> {
                if (star_pressed_two) {
                    clearStars()
                } else {
                    rank_place_one_star.setImageResource(R.drawable.ic_baseline_star_rate_24)
                    rank_place_two_star.setImageResource(R.drawable.ic_baseline_star_rate_24)
                    star_pressed_one = true
                    star_pressed_two = true
                    rank = 2
                }
            }

            3 -> {
                if (star_pressed_three) {
                    clearStars()
                } else {
                    rank_place_one_star.setImageResource(R.drawable.ic_baseline_star_rate_24)
                    rank_place_two_star.setImageResource(R.drawable.ic_baseline_star_rate_24)
                    rank_place_three_star.setImageResource(R.drawable.ic_baseline_star_rate_24)
                    rank_place_two_star.setImageResource(R.drawable.ic_baseline_star_rate_24)
                    star_pressed_one = true
                    star_pressed_two = true
                    star_pressed_three = true
                    rank = 3
                }
            }

            4 -> {
                if (star_pressed_four) {
                    clearStars()
                } else {
                    rank_place_one_star.setImageResource(R.drawable.ic_baseline_star_rate_24)
                    rank_place_two_star.setImageResource(R.drawable.ic_baseline_star_rate_24)
                    rank_place_three_star.setImageResource(R.drawable.ic_baseline_star_rate_24)
                    rank_place_four_star.setImageResource(R.drawable.ic_baseline_star_rate_24)
                    star_pressed_one = true
                    star_pressed_two = true
                    star_pressed_three = true
                    star_pressed_four = true
                    rank = 4
                }
            }

            5 -> {
                if (star_pressed_five) {
                    clearStars()
                } else {
                    rank_place_one_star.setImageResource(R.drawable.ic_baseline_star_rate_24)
                    rank_place_two_star.setImageResource(R.drawable.ic_baseline_star_rate_24)
                    rank_place_three_star.setImageResource(R.drawable.ic_baseline_star_rate_24)
                    rank_place_four_star.setImageResource(R.drawable.ic_baseline_star_rate_24)
                    rank_place_five_star.setImageResource(R.drawable.ic_baseline_star_rate_24)
                    star_pressed_one = true
                    star_pressed_two = true
                    star_pressed_three = true
                    star_pressed_four = true
                    star_pressed_five = true
                    rank = 5
                }
            }
        }
    }

    private fun clearStars() {
        rank_place_one_star.setImageResource(R.drawable.ic_baseline_star_outline_24)
        rank_place_two_star.setImageResource(R.drawable.ic_baseline_star_outline_24)
        rank_place_three_star.setImageResource(R.drawable.ic_baseline_star_outline_24)
        rank_place_four_star.setImageResource(R.drawable.ic_baseline_star_outline_24)
        rank_place_five_star.setImageResource(R.drawable.ic_baseline_star_outline_24)
        star_pressed_one = false
        star_pressed_two = false
        star_pressed_three = false
        star_pressed_four = false
        star_pressed_five = false
    }

    private fun onErrorChanged(view: View) {
        Snackbar.make(view, this.homeViewModel.error.value.toString(), Snackbar.LENGTH_SHORT)
            .setBackgroundTint(resources.getColor(R.color.error)).show()
    }

    private fun onBitampChanged(view: View) {
        if (this.homeViewModel.photoBitmap.value != null) {
            place_img.setImageBitmap(this.homeViewModel.photoBitmap.value)
            place_progress_bar.visibility = View.GONE
        } else {
            place_progress_bar.visibility = View.VISIBLE
        }
    }

    private fun onPlaceChanged(view: View) {
        // update place in fragment
        place_id_tv.text = (homeViewModel.place.value?.id).toString()
        Log.i("Places", "phone of place: " + (homeViewModel.place.value?.phoneNumber).toString())
        if (homeViewModel.place.value?.phoneNumber == null) {
            place_phone_tv.setTextColor(Color.RED)
            place_phone_tv.text = "Not Available"
        } else {
            place_phone_tv.text = (homeViewModel.place.value?.phoneNumber).toString()
        }

        place_name_tv.text = (homeViewModel.place.value?.name).toString()

        place_title_name.text = (homeViewModel.place.value?.name).toString()

        if (homeViewModel.place.value?.address != null) {
            place_address_tv.text = (homeViewModel.place.value?.address).toString()
        } else {
            place_address_tv.setTextColor(Color.RED)
            place_address_tv.text = "Not Available"
        }

        if (homeViewModel.place.value?.websiteUri != null) {
            place_website_tv.text = (homeViewModel.place.value?.websiteUri).toString()
        } else {
            place_website_tv.setTextColor(Color.RED)
            place_website_tv.text = "Not Available"
        }

        place_type_tv.text = ((homeViewModel.place.value?.types)?.get(0)).toString()

        if (homeViewModel.place.value?.openingHours != null) {
            var todays_open_hour = homeViewModel.place.value?.openingHours!!.periods[0].open?.time?.hours.toString()
            var todays_open_min = homeViewModel.place.value?.openingHours!!.periods[0].open?.time?.minutes.toString()
            var todays_close_hour = homeViewModel.place.value?.openingHours!!.periods[0].close?.time?.hours.toString()
            var todays_close_min = homeViewModel.place.value?.openingHours!!.periods[0].close?.time?.minutes.toString()
            if (todays_open_hour == "0") {
                todays_open_hour = "00"
            }
            if (todays_open_min == "0") {
                todays_open_min = "00"
            }
            if (todays_close_hour == "0") {
                todays_close_hour = "00"
            }
            if (todays_close_min == "0") {
                todays_close_min = "00"
            }
            place_opening_hours_tv.setTextColor(Color.BLUE)
            place_opening_hours_tv.text = "Today From: $todays_open_hour:$todays_open_min Until $todays_close_hour:$todays_close_min"
        } else {
            place_opening_hours_tv.setTextColor(Color.RED)
            place_opening_hours_tv.text = "Not Available"
        }

        if (homeViewModel.place.value?.isOpen == true) {
            place_is_open_tv.setTextColor(Color.GREEN)
            place_is_open_tv.text = "Opened Now"
        } else if (homeViewModel.place.value?.isOpen == false) {
            place_is_open_tv.setTextColor(Color.RED)
            place_is_open_tv.text = "Closed"
        } else {
            place_is_open_tv.setTextColor(Color.RED)
            place_is_open_tv.text = "Not Available"
        }
    }

    private fun onPhotoLoadingChanged(view: View) {
        if (homeViewModel.photoLoading.value == false) {
            // hide the photo progress bar!
            place_progress_bar.visibility = View.GONE
        }
    }

    private fun onClickShare() {
        view?.let {
            Snackbar.make(it, "Not Available in this version", Snackbar.LENGTH_SHORT)
                .setBackgroundTint(resources.getColor(R.color.error)).show()
        }
    }

    private fun onClickLike() {
        // open dialog that allows user to rank the place from 1-5
        homeViewModel.saveLikedPlace(placeId, rank)
        like_place_button.setImageResource(R.drawable.ic_baseline_favorite_44)
        view?.let {
            Snackbar.make(it, "saved", Snackbar.LENGTH_SHORT)
                .setBackgroundTint(resources.getColor(R.color.success)).show()
        }
    }

    // user click call and forward to phone on android
    private fun onClickCall() {
        if (homeViewModel.getPhoneNumber() != null) {
            val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", homeViewModel.getPhoneNumber(), null))
            startActivity(intent)
        } else {
            view?.let {
                Snackbar.make(it, "Phone Number Not Available", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(resources.getColor(R.color.error)).show()
            }
        }
    }

    // what happens when user click on url button
    private fun onClickUrl(view: View) {
        val url: Uri? = homeViewModel.place.value?.websiteUri
        if (url != null) {
            // TODO fix uri bug http://
            Log.i("Places", url.toString())
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url.toString()))
            startActivity(browserIntent)
        } else {
            Snackbar.make(view, "Url Not Available, Sorry!", Snackbar.LENGTH_SHORT)
                .setBackgroundTint(resources.getColor(R.color.error)).show()
            Log.i("Places", url.toString())
        }
    }

    // -- fragment functions --
    override fun onPause() {
        super.onPause()
        this.homeViewModel.photoBitmap.postValue(null)
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
