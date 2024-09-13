package com.example.coffeeapp.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.example.coffeeapp.R
import com.example.coffeeapp.adapter.CoffeeAdapter
import com.example.coffeeapp.adapter.CoffeePagerAdapter
import com.example.coffeeapp.databinding.FragmentHomeBinding
import com.example.coffeeapp.viewmodel.CoffeeViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val coffeeViewModel: CoffeeViewModel  by activityViewModels()

    private lateinit var coffeeAdapter: CoffeeAdapter
    private var isHotCoffeeSelected = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    @SuppressLint("RestrictedApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val coffeePagerAdapter = CoffeePagerAdapter(this)
        coffeeAdapter = CoffeeAdapter(emptyList()) { coffee ->

        }
        binding.recyclerView.adapter = coffeeAdapter
        binding.apply {
            viewPager.adapter = coffeePagerAdapter
            viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    when (position) {
                        0 -> binding.radioGroupHome.check(R.id.rBHotCoffee)
                        1 -> binding.radioGroupHome.check(R.id.rbColdCoffee)
                    }
                }
            })

            radioGroupHome.setOnCheckedChangeListener { group, checkedId ->
                when (checkedId) {
                    R.id.rBHotCoffee -> {
                        binding.viewPager.currentItem = 0
                        updateRadioButtonStyles(checkedId)
                    }
                    R.id.rbColdCoffee -> {
                        binding.viewPager.currentItem = 1
                        updateRadioButtonStyles(checkedId)
                    }
                }
            }

            val searchAutoComplete = searchView.findViewById<SearchView.SearchAutoComplete>(androidx.appcompat.R.id.search_src_text)
            searchAutoComplete.setHintTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            searchAutoComplete.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
                android.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    val query = newText.orEmpty()
                    coffeeViewModel.filterCoffeeList(query, isHotCoffeeSelected)
                    return true
                }
                })

            viewLifecycleOwner.lifecycleScope.launch {
                coffeeViewModel.filterCoffeeList.collectLatest { filteredList ->
                    coffeeAdapter.updateCoffeeList(filteredList)
                }
            }

        }
    }
    private fun updateRadioButtonStyles(checkedId: Int) {
        val selectedColor = ContextCompat.getColor(requireContext(), R.color.white)
        val defaultColor = ContextCompat.getColor(requireContext(), R.color.black)
        val rbHotCoffee = binding.rBHotCoffee
        val rbColdCoffee = binding.rbColdCoffee

        rbHotCoffee.setTextColor(
            if (checkedId == R.id.rBHotCoffee) selectedColor else defaultColor)
        rbColdCoffee.setTextColor(
            if (checkedId == R.id.rbColdCoffee) selectedColor else defaultColor)

        rbHotCoffee.background = ContextCompat.getDrawable(
            requireContext(),
            if (checkedId == R.id.rBHotCoffee) R.drawable.rounded_button
            else R.drawable.radio_button_selector
        )

        rbColdCoffee.background = ContextCompat.getDrawable(
            requireContext(),
            if (checkedId == R.id.rbColdCoffee) R.drawable.rounded_button
            else R.drawable.radio_button_selector
        )
    }
}