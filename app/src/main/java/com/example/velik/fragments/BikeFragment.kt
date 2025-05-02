package com.example.velik.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.velik.R
import com.example.velik.databinding.FragmentBikeBinding
import com.example.velik.db.MainDb

class BikeFragment : Fragment() {

    private lateinit var binding: FragmentBikeBinding
    private lateinit var db: MainDb

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentBikeBinding.inflate(layoutInflater)
        db = MainDb.getDb(this.requireActivity())
        val navController = this.requireActivity().findNavController(R.id.fragmentContainerView)

        binding.stackBack.setOnClickListener {
            navController.navigateUp()
        }

        return binding.root
    }
}