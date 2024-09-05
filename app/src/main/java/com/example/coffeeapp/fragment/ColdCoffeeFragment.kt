package com.example.coffeeapp.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.coffeeapp.R
import com.example.coffeeapp.adapter.CoffeeAdapter
import com.example.coffeeapp.databinding.FragmentColdCoffeeBinding
import com.example.coffeeapp.viewmodel.CoffeeViewModel
import kotlinx.coroutines.launch


class ColdCoffeeFragment : Fragment() {

    private lateinit var binding: FragmentColdCoffeeBinding
    private lateinit var coffeeViewModel: CoffeeViewModel
    private lateinit var coffeeAdapter: CoffeeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentColdCoffeeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        coffeeAdapter = CoffeeAdapter(emptyList()) { selectedCoffee ->

            Log.d("ColdCoffeeFragment",
                "Selected Coffee: Title = ${selectedCoffee.title}," +
                    " Image = ${selectedCoffee.image}, Description = ${selectedCoffee.description}")

            // Assuming you are in a Fragment
            val detailFragment = DetailFragment.newInstance(
                selectedCoffee.title,
                selectedCoffee.image,
                selectedCoffee.description
            )

            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, detailFragment) // Replace with your container ID
                .addToBackStack(null) // Optional: add this transaction to the back stack
                .commit()
        }

        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = coffeeAdapter
        }

        coffeeViewModel = ViewModelProvider(requireActivity())[CoffeeViewModel::class.java]
        viewLifecycleOwner.lifecycleScope.launch {
            coffeeViewModel.coldCoffeeList.collect { coldCoffeeList ->
                coffeeAdapter.updateCoffeeList(coldCoffeeList)
                binding.progressBar.visibility = View.GONE
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            coffeeViewModel.isLoading.collect { isLoading ->
                if (isLoading) {
                    binding.progressBar.visibility = View.VISIBLE // Show progress bar
                } else {
                    binding.progressBar.visibility = View.GONE // Hide progress bar
                }
            }
        }
    }
}
