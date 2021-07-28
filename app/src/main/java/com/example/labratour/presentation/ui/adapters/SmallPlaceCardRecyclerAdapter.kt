package com.example.labratour.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.labratour.R
import com.example.labratour.presentation.model.data.PlaceModel
import kotlinx.android.synthetic.main.place_card_small.view.*

class SmallPlaceCardRecyclerAdapter(private val placesList: List<PlaceModel>) : RecyclerView.Adapter<SmallPlaceCardRecyclerAdapter.SmallPlaceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SmallPlaceViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.place_card_small, parent, false)
        return SmallPlaceViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SmallPlaceViewHolder, position: Int) {
        val currntItem = placesList[position]
        holder.imageView.setImageBitmap(currntItem.bitmap)
        holder.nameTextView.text = currntItem.googlePlace.name.toString()
        holder.adreesTextView.text = currntItem.googlePlace.address.toString()
        holder.typeTextView.text = (currntItem.googlePlace.types?.get(0)).toString()
    }

    override fun getItemCount() = placesList.size

    class SmallPlaceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.place_small_card_image
        val adreesTextView: TextView = itemView.place_small_card_address
        val typeTextView: TextView = itemView.place_small_card_type
        val nameTextView: TextView = itemView.place_small_card_name
    }
}
