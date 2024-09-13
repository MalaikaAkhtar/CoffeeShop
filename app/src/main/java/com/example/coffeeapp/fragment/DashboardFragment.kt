package com.example.coffeeapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation.findNavController
import com.example.coffeeapp.R
import com.example.coffeeapp.databinding.FragmentDashboardBinding


class DashboardFragment : Fragment() {

    private lateinit var binding: FragmentDashboardBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.startBtn.setOnClickListener {
            findNavController(binding.root).navigate(R.id.action_dashboardFragment_to_homeFragment)
        }
    }
}