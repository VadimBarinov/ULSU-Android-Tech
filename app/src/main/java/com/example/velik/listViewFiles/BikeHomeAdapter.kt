package com.example.velik.listViewFiles

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.velik.R
import com.example.velik.databinding.BikeItemHomeBinding
import com.example.velik.recyclerViewFiles.BikeClass

class BikeHomeAdapter(
    val listener: Listener,
    val listenerFavorite: ListenerFavorite
) : RecyclerView.Adapter<BikeHomeAdapter.BikeHolder>() {

    val bikeList = ArrayList<BikeClass>()

    class BikeHolder(item: View) : RecyclerView.ViewHolder(item) {

        val binding = BikeItemHomeBinding.bind(item)
        val updateFavoriteClass = UpdateFavoriteClass()

        fun bind(
            bike: BikeClass,
            listener: Listener,
            listenerFavorite: ListenerFavorite
        ) {
            binding.imageView.setImageResource(bike.imageId)
            binding.textView.text = bike.name
            binding.toggleButton.isChecked = bike.isFavorite

            itemView.setOnClickListener {
                listener.onClick(bike)
            }

            binding.toggleButton.setOnClickListener {
                val isChecked = binding.toggleButton.isChecked
                listenerFavorite.onClickFavorite(bike, isChecked)
                updateFavoriteClass.addOrDelete(bike, isChecked)
            }

        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BikeHolder {

        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.bike_item_home,
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
        var foundBike = bikeList.firstOrNull{ it.id == bike.id }
        if (foundBike == null) {
            bikeList.add(bike)
            notifyDataSetChanged()
        }
        else {
            foundBike.isFavorite = bike.isFavorite
            foundBike.favoriteIcon = bike.favoriteIcon
        }
    }

    class UpdateFavoriteClass {
        fun addOrDelete(bike: BikeClass, isChecked: Boolean) {
            if (isChecked){
                bike.isFavorite = true
                bike.favoriteIcon = R.drawable.check
            }
            else{
                bike.isFavorite = false
                bike.favoriteIcon = R.drawable.favorite
            }
        }
    }

    interface Listener {
        fun onClick(bike: BikeClass)
    }

    interface ListenerFavorite {
        fun onClickFavorite(bike: BikeClass, isChecked: Boolean)
    }

}