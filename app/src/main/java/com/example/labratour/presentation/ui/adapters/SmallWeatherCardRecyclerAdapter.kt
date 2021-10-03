package com.example.labratour.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.labratour.R
import com.example.labratour.presentation.model.data.weather.IntervalWeather
import kotlinx.android.synthetic.main.weather_card.view.current_degrees_tv
import kotlinx.android.synthetic.main.weather_card_small.view.*
import kotlin.math.floor

class SmallWeatherCardRecyclerAdapter(
    private val weatherList: List<IntervalWeather>,
) : RecyclerView.Adapter<SmallWeatherCardRecyclerAdapter.SmallWeatherViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SmallWeatherViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.weather_card_small, parent, false)
        return SmallWeatherViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SmallWeatherViewHolder, position: Int) {
        val currntItem = weatherList[position]

        // current weather temperature
        val current_temp: Double = floor(currntItem.main.temp)
        holder.temp.text = current_temp.toString() + "\u2103"
        // update current icon
        val current_condition: String = currntItem.weather[0].main
        when (current_condition) {
            "Clear" -> {
                holder.icon.setImageResource(R.drawable.ic_day)
            }
            "Clouds" -> {
                holder.icon.setImageResource(R.drawable.ic_cloudy_day_1)
            }
            "Snow" -> {
                holder.icon.setImageResource(R.drawable.ic_snowy_1)
            }
            "Rain" -> {
                holder.icon.setImageResource(R.drawable.ic_rainy_1)
            }
            "Drizzle" -> {
            }
            "Thunderstorm" -> {
                holder.icon.setImageResource(R.drawable.ic_thunder)
            }
        }
        //
        holder.feelsLike.text = currntItem.main.feels_like.toString() + "\u2103"
        holder.description.text = currntItem.weather[0].description
        holder.date.text = currntItem.dt_txt
    }

    override fun getItemCount() = weatherList.size

    inner class SmallWeatherViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val feelsLike: TextView = itemView.weather_small_card_feels_like
        val temp: TextView = itemView.current_degrees_tv
        val date: TextView = itemView.weather_small_card_date
        val icon: ImageView = itemView.weather_card_small_current_weather_icon
        val description: TextView = itemView.weather_small_card_description

        override fun onClick(v: View?) {
        }
    }
}
