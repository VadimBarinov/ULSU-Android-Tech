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
        val updateFavoriteClass = UpdateFavoriteClass()

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
                val isChecked = binding.toggleButton.isChecked
                listenerFavorite.onClickFavorite(bike, isChecked)
                updateFavoriteClass.addOrDelete(binding, bike, isChecked)
            }

            // TODO нужно исправить. Не работает нажатие
            binding.iconFavorite.setOnClickListener {
                val isChecked = binding.toggleButton.isChecked
                listenerFavorite.onClickFavorite(bike, isChecked)
                updateFavoriteClass.addOrDelete(binding, bike, isChecked)
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
        if (bikeList.firstOrNull{ it.id == bike.id } == null) {
            bikeList.add(bike)
            notifyDataSetChanged()
        }
    }

    class UpdateFavoriteClass {
        fun addOrDelete(binding: BikeItemBinding, bike: BikeClass, isChecked: Boolean) {
            if (isChecked){
                bike.isFavorite = true
                bike.favoriteIcon = R.drawable.check
                binding.iconFavorite.setImageResource(R.drawable.check)
            }
            else{
                bike.isFavorite = false
                bike.favoriteIcon = R.drawable.favorite
                binding.iconFavorite.setImageResource(R.drawable.favorite)
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