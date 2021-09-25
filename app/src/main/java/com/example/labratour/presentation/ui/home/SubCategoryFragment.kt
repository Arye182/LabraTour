package com.example.labratour.presentation.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.labratour.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_sub_category_artandculture.*
import kotlinx.android.synthetic.main.fragment_sub_category_finance.*
import kotlinx.android.synthetic.main.fragment_sub_category_foodanddrinks.*
import kotlinx.android.synthetic.main.fragment_sub_category_fun.*
import kotlinx.android.synthetic.main.fragment_sub_category_general.*
import kotlinx.android.synthetic.main.fragment_sub_category_medical.*
import kotlinx.android.synthetic.main.fragment_sub_category_parks.*
import kotlinx.android.synthetic.main.fragment_sub_category_parks.park
import kotlinx.android.synthetic.main.fragment_sub_category_parks.zoo
import kotlinx.android.synthetic.main.fragment_sub_category_religion.*
import kotlinx.android.synthetic.main.fragment_sub_category_shopping.*
import kotlinx.android.synthetic.main.fragment_sub_category_sports.*
import kotlinx.android.synthetic.main.fragment_sub_category_transportation.*

class SubCategoryFragment : Fragment() {

    private lateinit var category: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args: SubCategoryFragmentArgs by navArgs()
        category = args.category
        Log.i("Places", "SubCategoryFragment onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // TODO SWITCH CASE ON CATEGORY STRING -> LAYOUT OF BUTTONS - DISPLAY VISIBLE
        Log.i("Places", "SubCategoryFragment onCreateView")

        return when (this.category) {
            "Transportation" -> inflater.inflate(R.layout.fragment_sub_category_transportation, container, false)
            "Parks" -> inflater.inflate(R.layout.fragment_sub_category_parks, container, false)
            "Art & Culture" -> inflater.inflate(R.layout.fragment_sub_category_artandculture, container, false)
            "Finance" -> inflater.inflate(R.layout.fragment_sub_category_finance, container, false)
            "Food & Drinks" -> inflater.inflate(R.layout.fragment_sub_category_foodanddrinks, container, false)
            "Shopping" -> inflater.inflate(R.layout.fragment_sub_category_shopping, container, false)
            "Religion" -> inflater.inflate(R.layout.fragment_sub_category_religion, container, false)
            "Medical" -> inflater.inflate(R.layout.fragment_sub_category_medical, container, false)
            "Fun" -> inflater.inflate(R.layout.fragment_sub_category_fun, container, false)
            "Sports" -> inflater.inflate(R.layout.fragment_sub_category_sports, container, false)
            "General" -> inflater.inflate(R.layout.fragment_sub_category_general, container, false)

            else -> {
                inflater.inflate(R.layout.fragment_search, container, false)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i("Places", "SubCategoryFragment onViewCreated")
    }

    override fun onResume() {
        super.onResume()
        Log.i("Places", "SubCategoryFragment onResume")
    }

    override fun onStart() {
        super.onStart()
        Log.i("Places", "SubCategoryFragment onStart")

        // TODO LISTENERS TO ALL SUBCATEGORIES
        when (this.category) {
            "Transportation" -> {
                airport.setOnClickListener(View.OnClickListener { SubCategoryIntent("airport") })
                bus.setOnClickListener(View.OnClickListener { SubCategoryIntent("bus_station") })
                car_rental.setOnClickListener(View.OnClickListener { SubCategoryIntent("car_rental") })
                gas_station.setOnClickListener(View.OnClickListener { SubCategoryIntent("gas_station") })
                light_train.setOnClickListener(View.OnClickListener { SubCategoryIntent("light_rail_station") })
                subway.setOnClickListener(View.OnClickListener { SubCategoryIntent("subway_station") })
                taxi.setOnClickListener(View.OnClickListener { SubCategoryIntent("taxi_stand") })
                train_station.setOnClickListener(View.OnClickListener { SubCategoryIntent("train_station") })
            }
            "Parks" -> {
                // Parks
                amusement_park.setOnClickListener(View.OnClickListener { SubCategoryIntent("amusement_park") })
                aquarium.setOnClickListener(View.OnClickListener { SubCategoryIntent("aquarium") })
                zoo.setOnClickListener(View.OnClickListener { SubCategoryIntent("zoo") })
                park.setOnClickListener(View.OnClickListener { SubCategoryIntent("park") })
            }
            "Art & Culture" -> {
                // Art & Culture
                art_gallery.setOnClickListener(View.OnClickListener { SubCategoryIntent("art_gallery") })
                library.setOnClickListener(View.OnClickListener { SubCategoryIntent("library") })
                museum.setOnClickListener(View.OnClickListener { SubCategoryIntent("museum") })
            }
            "Finance" -> {
                // Finance
                atm.setOnClickListener(View.OnClickListener { SubCategoryIntent("atm") })
                bank.setOnClickListener(View.OnClickListener { SubCategoryIntent("bank") })
            }
            "Food & Drinks" -> {
                // Food & Drinks
                bakery.setOnClickListener { SubCategoryIntent("bakery") }
                bar.setOnClickListener { SubCategoryIntent("bar") }
                cafe.setOnClickListener { SubCategoryIntent("cafe") }
                liquor_store.setOnClickListener { SubCategoryIntent("liquor_store") }
                restaurant.setOnClickListener { SubCategoryIntent("restaurant") }
                food.setOnClickListener { SubCategoryIntent("food") }
                supermarket.setOnClickListener { SubCategoryIntent("supermarket") }
            }
            "Shopping" -> {
                // Shopping
                clothing_store.setOnClickListener { SubCategoryIntent("clothing_store") }
                convenience_store.setOnClickListener { SubCategoryIntent("convenience_store") }
                book_store.setOnClickListener { SubCategoryIntent("book_store") }
                electronics_store.setOnClickListener { SubCategoryIntent("electronics_store") }
                jewelry_store.setOnClickListener { SubCategoryIntent("jewelry_store") }
                department_store.setOnClickListener { SubCategoryIntent("department_store") }
                home_goods_store.setOnClickListener { SubCategoryIntent("home_goods_store") }
                shopping_mall.setOnClickListener { SubCategoryIntent("shopping_mall") }
                shoe_store.setOnClickListener { SubCategoryIntent("shoe_store") }
                store.setOnClickListener { SubCategoryIntent("store") }
            }
            "Religion" -> {
                // Religon
                cemetery.setOnClickListener { SubCategoryIntent("cemetery") }
                church.setOnClickListener { SubCategoryIntent("church") }
                hindu_temple.setOnClickListener { SubCategoryIntent("hindu_temple") }
                synagogue.setOnClickListener { SubCategoryIntent("synagogue") }
                mosque.setOnClickListener { SubCategoryIntent("mosque") }
            }
            "Medical" -> {
                // Medical
                doctor.setOnClickListener { SubCategoryIntent("doctor") }
                drugstore.setOnClickListener { SubCategoryIntent("drugstore") }
                hospital.setOnClickListener { SubCategoryIntent("hospital") }
                pharmacy.setOnClickListener { SubCategoryIntent("pharmacy") }
            }
            "Fun" -> {
                // Fun
                casino.setOnClickListener(View.OnClickListener { SubCategoryIntent("casino") })
                movie_theater.setOnClickListener(View.OnClickListener { SubCategoryIntent("movie_theater") })
                night_club.setOnClickListener(View.OnClickListener { SubCategoryIntent("night_club") })
            }
            "Sports" -> {
                // Sports
                gym.setOnClickListener(View.OnClickListener { SubCategoryIntent("gym") })
                spa.setOnClickListener(View.OnClickListener { SubCategoryIntent("spa") })
                stadium.setOnClickListener(View.OnClickListener { SubCategoryIntent("stadium") })
            }
            "General" -> {
                // General
                point_of_interest.setOnClickListener(View.OnClickListener { SubCategoryIntent("point_of_interest") })
                tourist_attraction.setOnClickListener(View.OnClickListener { SubCategoryIntent("tourist_attraction") })
            }
        }
    }

    fun SubCategoryIntent(sub_category_name: String) {
        view?.let {
            Snackbar.make(it, "Button $sub_category_name Clicked", Snackbar.LENGTH_SHORT)
                .setBackgroundTint(resources.getColor(R.color.success)).show()
        }
        // TODO open a dialog of sub categories?
        // or navigate to another screen of sub categories?
        // move to fragment of result of place!
        val action = SubCategoryFragmentDirections.actionSubCategoryFragmentToPlacesListFragment(sub_category_name)
        findNavController().navigate(action)
    }
}
