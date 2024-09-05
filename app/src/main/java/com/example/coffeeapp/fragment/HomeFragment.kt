package com.example.coffeeapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.coffeeapp.R
import com.example.coffeeapp.adapter.CoffeePagerAdapter
import com.example.coffeeapp.databinding.FragmentHomeBinding
import com.example.coffeeapp.viewmodel.CoffeeViewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var coffeeViewModel: CoffeeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val coffeePagerAdapter = CoffeePagerAdapter(this)
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

            searchIV.setOnClickListener {
                val searchText = binding.searchET.text.toString()
                coffeeViewModel.filterCoffee(searchText)
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