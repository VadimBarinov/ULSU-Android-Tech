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
import com.example.velik.databinding.FragmentHomeBinding
import com.example.velik.db.Bike
import com.example.velik.db.MainDb
import com.example.velik.listViewFiles.BikeHomeAdapter
import com.example.velik.recyclerViewFiles.BikeClass
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class Home : Fragment(), BikeHomeAdapter.Listener, BikeHomeAdapter.ListenerFavorite {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var db: MainDb
    private val bikeHomeAdapter = BikeHomeAdapter(this, this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(layoutInflater)
        db = MainDb.getDb(this.requireActivity())
        init()

        binding.watchAll.setOnClickListener {
            onClickWatchAll()
        }

        return binding.root
    }

    private fun init(){
        binding.recyclerViewBikesHome.overScrollMode = View.OVER_SCROLL_NEVER
        binding.recyclerViewBikesHome.layoutManager = LinearLayoutManager(this.requireActivity())
        binding.recyclerViewBikesHome.adapter = bikeHomeAdapter

        db.getDao().get2Bikes().asLiveData().observe(this.requireActivity()) {

            it.forEach { bike ->
                var item: BikeClass
                if (bike.favorite == true) {
                    item = addedFavorite(bike)
                }
                else {
                    item = notAddedFavorite(bike)
                }

                bikeHomeAdapter.addBike(item)
            }

        }

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
            .navigate(R.id.action_home_to_bikeFragment, bundle)
    }

    override fun onClickFavorite(bike: BikeClass, isChecked: Boolean) {
        CoroutineScope(Dispatchers.IO).launch {
            db.getDao().changeFavorite(bike.id, isChecked)
        }
    }

    private fun onClickWatchAll() {
        this.requireActivity().findNavController(R.id.fragmentContainerView)
            .navigate(R.id.action_home_to_catalog)
    }

}