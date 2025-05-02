package com.example.velik.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.velik.R
import com.example.velik.databinding.FragmentFavoriteBinding
import com.example.velik.db.Bike
import com.example.velik.db.MainDb
import com.example.velik.recyclerViewFiles.BikeAdapter
import com.example.velik.recyclerViewFiles.BikeClass
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Favorite : Fragment(), BikeAdapter.Listener, BikeAdapter.ListenerFavorite {

    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var db: MainDb
    private val bikeAdapter = BikeAdapter(this, this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFavoriteBinding.inflate(layoutInflater)
        db = MainDb.getDb(this.requireActivity())
        init()

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        CoroutineScope(Dispatchers.IO).launch {
            if (db.getDao().getCountFavorite() == 0) {
                emptyFavorite()
            }
        }

    }

    private fun init() {

        binding.recyclerViewBikes.layoutManager = LinearLayoutManager(this.requireActivity())
        binding.recyclerViewBikes.adapter = bikeAdapter

        db.getDao().getFavoriteBikes().asLiveData().observe(this.requireActivity()) {

            it.forEach { bike ->
                var item: BikeClass
                if (bike.favorite == true) {
                    item = addedFavorite(bike)
                }
                else {
                    item = notAddedFavorite(bike)
                }

                bikeAdapter.addBike(item)
            }

        }

    }

    private fun emptyFavorite() {
        binding.textViewEmptyFavorite.visibility = View.VISIBLE
    }

    private fun addedFavorite(bike: Bike) : BikeClass{
        val item = BikeClass(
            bike.id,
            bike.image,
            bike.name,
            true,
            R.drawable.check,
        )
        return item
    }

    private fun notAddedFavorite(bike: Bike) : BikeClass {
        val item = BikeClass(
            bike.id,
            bike.image,
            bike.name,
            false,
            R.drawable.favorite,
        )
        return item
    }

    override fun onClick(bike: BikeClass) {
        val bundle = bundleOf("bikeId" to bike.id)
        this.requireActivity().findNavController(R.id.fragmentContainerView)
            .navigate(R.id.action_favorite_to_bikeFragment, bundle)
    }

    override fun onClickFavorite(bike: BikeClass, isChecked: Boolean) {
        CoroutineScope(Dispatchers.IO).launch {
            db.getDao().changeFavorite(bike.id, isChecked)
        }
    }

}
