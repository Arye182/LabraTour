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
        homewViewModel = (activity as HomeActivity?)?.userHomeViewModel!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homewViewModel.getPlaceById(placeId)
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
        this.homewViewModel.photoLoading.observe(viewLifecycleOwner, { onPhotoLoadingChanged(view) })
        this.homewViewModel.place.observe(viewLifecycleOwner, { onPlaceChanged(view) })
        this.homewViewModel.photoBitmap.observe(viewLifecycleOwner, { onBitampChanged(view) })
        this.homewViewModel.error.observe(viewLifecycleOwner, { onErrorChanged(view) })

        // TODO - get place from db to update like and rank
        // ...
    }

    private fun rankPlace() {
        val place_id = (homewViewModel.place.value?.id).toString()
        // val user_id =
        // homewViewModel.rankPlace()
    }

    private fun onClickStar(i: Int) {
        when (i) {
            1 -> {
                if (star_pressed_one) {
                    clearStars()
                } else {
                    rank_place_one_star.setImageResource(R.drawable.ic_baseline_star_rate_24)
                    star_pressed_one = true
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
        place_title_name.text = (homewViewModel.place.value?.name).toString()
        place_address_tv.text = (homewViewModel.place.value?.address).toString()
        place_website_tv.text = (homewViewModel.place.value?.websiteUri).toString()
        place_type_tv.text = (homewViewModel.place.value?.types).toString()

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
        homewViewModel.saveLikedPlace(placeId, rank)
        like_place_button.setImageResource(R.drawable.ic_baseline_favorite_44)
        view?.let {
            Snackbar.make(it, "saved", Snackbar.LENGTH_SHORT)
                .setBackgroundTint(resources.getColor(R.color.success)).show()
        }
    }

    // user click call and forward to phone on android
    private fun onClickCall() {
        val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", homewViewModel.getPhoneNumber(), null))
        startActivity(intent)
    }

    // what happens when user click on url button
    private fun onClickUrl(view: View) {
        val url: Uri? = homewViewModel.place.value?.websiteUri
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
