package com.example.labratour.presentation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.labratour.R

class SubCategoryFragment : Fragment() {

    private lateinit var subCategory: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args: SubCategoryFragmentArgs by navArgs()
        subCategory = args.subCategory
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // TODO SWITCH CASE ON CATEGORY STRING -> LAYOUT OF BUTTONS - DISPLAY VISIBLE
        return when (this.subCategory) {
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
    }
}
