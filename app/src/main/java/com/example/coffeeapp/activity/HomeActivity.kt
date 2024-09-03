package com.example.coffeeapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.coffeeapp.R
import com.example.coffeeapp.adapter.CoffeeAdapter
import com.example.coffeeapp.adapter.CoffeePagerAdapter
import com.example.coffeeapp.databinding.ActivityHomeBinding
import com.example.coffeeapp.viewmodel.CoffeeViewModel


class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var coffeeAdapter: CoffeeAdapter
    private lateinit var coffeeViewModel: CoffeeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.searchIV.setOnClickListener {
            val searchText = binding.searchET.text.toString()
            coffeeViewModel.filterCoffee(searchText)
        }

        val coffeePagerAdapter = CoffeePagerAdapter(this)
        binding.viewPager.adapter = coffeePagerAdapter
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    0 -> binding.radioGroupHome.check(R.id.rBHotCoffee)
                    1 -> binding.radioGroupHome.check(R.id.rbColdCoffee)
                }
            }
        })


        binding.radioGroupHome.setOnCheckedChangeListener { group, checkedId ->
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

    }

    private fun updateRadioButtonStyles(checkedId: Int) {
        val selectedColor = ContextCompat.getColor(this, R.color.white)
        val defaultColor = ContextCompat.getColor(this, R.color.black)

        val rbHotCoffee = binding.rBHotCoffee
        val rbColdCoffee = binding.rbColdCoffee

        rbHotCoffee.setTextColor(if (checkedId == R.id.rBHotCoffee) selectedColor else defaultColor)
        rbColdCoffee.setTextColor(if (checkedId == R.id.rbColdCoffee) selectedColor else defaultColor)

        rbHotCoffee.background = ContextCompat.getDrawable(
            this,
            if (checkedId == R.id.rBHotCoffee) R.drawable.rounded_button else R.drawable.radio_button_selector
        )

        rbColdCoffee.background = ContextCompat.getDrawable(
            this,
            if (checkedId == R.id.rbColdCoffee) R.drawable.rounded_button else R.drawable.radio_button_selector
        )
    }

}


//        TabLayoutMediator(binding.radioGroupHome, binding.viewPager) { tab, position ->
//            tab.text = when (position) {
//                0 -> "Hot Coffee"
//                1 -> "Cold Coffee"
//                else -> "Hot Coffee"
//            }
//        }.attach()
//
//        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
//            override fun onTabSelected(tab: TabLayout.Tab?) {
//                tab?.view?.setBackgroundColor(ContextCompat.getColor(this@HomeActivity, R.color.brown))
//                val tabTextView = tab?.view?.findViewById<TextView>(android.R.id.text1)
//                tabTextView?.setTextColor(ContextCompat.getColor(this@HomeActivity, R.color.white))
//                tabTextView?.setBackgroundColor(ContextCompat.getColor(this@HomeActivity,R.color.brown))
//            }
//
//            override fun onTabUnselected(tab: TabLayout.Tab?) {
//                tab?.view?.setBackgroundColor(ContextCompat.getColor(this@HomeActivity, R.color.white))
//                val tabTextView = tab?.view?.findViewById<TextView>(android.R.id.text1)
//                tabTextView?.setTextColor(ContextCompat.getColor(this@HomeActivity, R.color.black))
//
//            }
//
//            override fun onTabReselected(tab: TabLayout.Tab?) {
//                tab?.view?.setBackgroundColor(ContextCompat.getColor(this@HomeActivity, R.color.white))
//            }
//        })