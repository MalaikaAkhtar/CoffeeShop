package com.example.coffeeapp.adapter

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.coffeeapp.fragment.ColdCoffeeFragment
import com.example.coffeeapp.fragment.HomeFragment
import com.example.coffeeapp.fragment.HomeFragmentDirections
import com.example.coffeeapp.fragment.HotCoffeeFragment

class CoffeePagerAdapter(private val fragmentActivity: HomeFragment) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 2// no of tabs
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HotCoffeeFragment.newInstance { selectedCoffee ->
                fragmentActivity.findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToDetailFragment(
                        selectedCoffee.title,
                        selectedCoffee.image,
                        selectedCoffee.description))
            }

            1 -> ColdCoffeeFragment.newInstance { selectedCoffee ->
                fragmentActivity.findNavController()
                    .navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment(
                        selectedCoffee.title,
                        selectedCoffee.image,
                        selectedCoffee.description))

            }

            else -> HotCoffeeFragment()
        }
    }

}