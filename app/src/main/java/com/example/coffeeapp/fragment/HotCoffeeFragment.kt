package com.example.coffeeapp.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.coffeeapp.R
import com.example.coffeeapp.activity.DetailsActivity
import com.example.coffeeapp.adapter.CoffeeAdapter
import com.example.coffeeapp.databinding.FragmentHotCoffeeBinding
import com.example.coffeeapp.viewmodel.CoffeeViewModel


class HotCoffeeFragment : Fragment() {

    private lateinit var binding: FragmentHotCoffeeBinding
    private lateinit var coffeeViewModel: CoffeeViewModel
    private lateinit var coffeeAdapter: CoffeeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHotCoffeeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        coffeeAdapter = CoffeeAdapter(emptyList()) { selectedCoffee ->

            Log.d("ColdCoffeeFragment", "Selected Coffee: Title = ${selectedCoffee.title}," +
                    " Image = ${selectedCoffee.image}, Description = ${selectedCoffee.description}")

            val intent = Intent(context, DetailsActivity::class.java).apply {
                putExtra("COFFEE_TITLE", selectedCoffee.title)
                putExtra("COFFEE_IMAGE", selectedCoffee.image)
                putExtra("COFFEE_DESCRIPTION", selectedCoffee.description)
            }
            startActivity(intent)
        }

        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context,2)
            adapter = coffeeAdapter
        }

        coffeeViewModel = ViewModelProvider(requireActivity())[CoffeeViewModel::class.java]

        coffeeViewModel.hotCoffeeList.observe(viewLifecycleOwner) { hotCoffeeList ->
            coffeeAdapter.updateCoffeeList(hotCoffeeList)
        }
    }

}