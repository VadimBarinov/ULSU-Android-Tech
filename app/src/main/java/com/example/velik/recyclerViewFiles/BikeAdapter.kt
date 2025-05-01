package com.example.velik.recyclerViewFiles

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.velik.R
import com.example.velik.databinding.BikeItemBinding

class BikeAdapter(
    val listener: Listener,
    val listenerFavorite: ListenerFavorite
) : RecyclerView.Adapter<BikeAdapter.BikeHolder>() {

    val bikeList = ArrayList<BikeClass>()

    class BikeHolder(item: View) : RecyclerView.ViewHolder(item) {

        val binding = BikeItemBinding.bind(item)

        fun bind(
            bike: BikeClass,
            listener: Listener,
            listenerFavorite: ListenerFavorite
        ) {
            binding.imageView.setImageResource(bike.imageId)
            binding.textView.text = bike.name
            binding.iconFavorite.setImageResource(bike.favoriteIcon)
            binding.toggleButton.isChecked = bike.isFavorite

            itemView.setOnClickListener {
                listener.onClick(bike)
            }

            binding.toggleButton.setOnClickListener {
                listenerFavorite.onClickFavorite(bike)
            }

        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BikeHolder {

        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.bike_item,
            parent,
            false
        )
        return BikeHolder(view)

    }

    override fun onBindViewHolder(
        holder: BikeHolder,
        position: Int
    ) {
        holder.bind(bikeList[position], listener, listenerFavorite)
    }

    override fun getItemCount(): Int {
        return bikeList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addBike(bike: BikeClass) {
        bikeList.add(bike)
        notifyDataSetChanged()
    }

    interface Listener {
        fun onClick(bike: BikeClass)
    }

    interface ListenerFavorite {
        fun onClickFavorite(bike: BikeClass)
    }

}