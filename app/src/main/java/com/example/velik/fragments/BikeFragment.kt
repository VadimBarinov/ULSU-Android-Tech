package com.example.velik.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import androidx.navigation.findNavController
import com.example.velik.R
import com.example.velik.databinding.FragmentBikeBinding
import com.example.velik.db.MainDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BikeFragment : Fragment() {

    private lateinit var binding: FragmentBikeBinding
    private lateinit var db: MainDb

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentBikeBinding.inflate(layoutInflater)
        db = MainDb.getDb(this.requireActivity())

        val bikeId = arguments?.getInt("bikeId")

        init(bikeId)

        binding.stackBack.setOnClickListener {
            onClickBack()
        }

        binding.toggleButton.setOnClickListener {
            val isChecked = binding.toggleButton.isChecked
            onClickFavorite(bikeId, isChecked)
        }

        binding.iconFavorite.setOnClickListener {
            binding.toggleButton.isChecked = !binding.toggleButton.isChecked
            val isChecked = binding.toggleButton.isChecked
            onClickFavorite(bikeId, isChecked)
        }

        return binding.root
    }

    private fun onClickBack (){
        this.requireActivity().findNavController(R.id.fragmentContainerView)
            .navigateUp()
    }

    private fun onClickFavorite(bikeId: Int?, isChecked: Boolean){

        CoroutineScope(Dispatchers.IO).launch {
            db.getDao().changeFavorite(bikeId, isChecked)
        }
        if (isChecked) {
            binding.iconFavorite.setImageResource(R.drawable.check)
        }
        else {
            binding.iconFavorite.setImageResource(R.drawable.favorite)
        }

    }

    private fun init(bikeId: Int?){

        if (bikeId != null){
            db.getDao().getBikeById(bikeId).asLiveData().observe(this.requireActivity()) { bike ->

                binding.imageBike.setImageResource(bike.image)
                binding.textBikeName.text = bike.name

                if (bike.favorite) {
                    binding.iconFavorite.setImageResource(R.drawable.check)
                    binding.toggleButton.isChecked = true
                }
                else {
                    binding.iconFavorite.setImageResource(R.drawable.favorite)
                    binding.toggleButton.isChecked = false
                }

                binding.description.text = bike.description

                binding.bikeBrandValue.text = bike.brand
                binding.bikeSeasonValue.text = bike.season.toString()
                binding.bikeSexValue.text = bike.sex
                binding.bikeAgeValue.text = bike.age
                binding.bikeTypeValue.text = bike.type
                binding.bikePurposeValue.text = bike.purpose
                binding.bikeFasteningValue.text = bike.fastening
                binding.bikeLevelValue.text = bike.level
                binding.bikeMaterialValue.text = bike.material
                binding.bikeSpeedsValue. text = bike.speeds.toString()

            }

        }

    }

}