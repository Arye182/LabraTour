package com.example.labratour.presentation.ui.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.labratour.R
import com.example.labratour.presentation.model.data.PlaceModel
import com.google.android.material.imageview.ShapeableImageView
import kotlinx.android.synthetic.main.place_card_small.view.*

class BigPlaceCardRecyclerAdapter(
    private val placesList: List<PlaceModel>,
    private val listener: OnItemClickListener,
    private val code: Int
) : RecyclerView.Adapter<BigPlaceCardRecyclerAdapter.BigPlaceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BigPlaceViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.place_card_big, parent, false)
        return BigPlaceViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BigPlaceViewHolder, position: Int) {
        val currntItem = placesList[position]
        holder.imageView.setImageBitmap(currntItem.bitmap)
        holder.nameTextView.text = currntItem.googlePlaceSdk.name.toString()
        holder.adreesTextView.text = currntItem.googlePlaceSdk.address.toString()
        holder.typeTextView.text = (currntItem.googlePlaceSdk.types?.get(0)).toString()
        if (currntItem.googlePlaceSdk.isOpen == true) {
            holder.openClosedTextView.text = "Opened"
            holder.openClosedTextView.setTextColor(Color.GREEN)
        } else if (currntItem.googlePlaceSdk.isOpen == false) {
            holder.openClosedTextView.text = "Closed"
            holder.openClosedTextView.setTextColor(Color.RED)
        } else {
            holder.openClosedTextView.text = ""
            holder.openClosedTextView.setTextColor(Color.BLACK)
        }
    }

    override fun getItemCount() = placesList.size

    inner class BigPlaceViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        val imageView: ShapeableImageView = itemView.place_small_card_image
        val adreesTextView: TextView = itemView.place_small_card_address
        val typeTextView: TextView = itemView.place_small_card_type
        val nameTextView: TextView = itemView.place_small_card_name
        val openClosedTextView: TextView = itemView.place_small_card_opened_closed

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position: Int = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position, code)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int, code: Int)
    }
}
