package com.example.coffeeapp.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.coffeeapp.fragment.ColdCoffeeFragment
import com.example.coffeeapp.fragment.HotCoffeeFragment

class CoffeePagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 2// no of tabs
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HotCoffeeFragment()
            1 -> ColdCoffeeFragment()
            else ->HotCoffeeFragment()
        }
    }

}