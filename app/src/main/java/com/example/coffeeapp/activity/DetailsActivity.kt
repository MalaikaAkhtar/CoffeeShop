package com.example.coffeeapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import com.bumptech.glide.Glide
import com.example.coffeeapp.R
import com.example.coffeeapp.constants.Constants
import com.example.coffeeapp.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding
    private var selectedPrice: String = "Constants.MEDIUM_PRICE"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val coffeeTitle = intent.getStringExtra("COFFEE_TITLE")
        val coffeeImage = intent.getStringExtra("COFFEE_IMAGE")
        val coffeeDescription = intent.getStringExtra("COFFEE_DESCRIPTION")

        binding.coffeNameTV.text = coffeeTitle
        binding.descriptionTV.text = coffeeDescription

        Glide.with(this)
            .load(coffeeImage)
            .into(binding.itemDetailIV)


        binding.backIV.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        binding.buyNowButton.setOnClickListener {
            val intent = Intent(this, OrderActivity::class.java)
            intent.putExtra("COFFEE_TITLE", coffeeTitle)
            intent.putExtra("COFFEE_IMAGE", coffeeImage)
            intent.putExtra("SELECTED_PRICE", selectedPrice)
            startActivity(intent)
        }

        binding.priceTV.setText(Constants.MEDIUM_PRICE)

        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.radioButtonSmall -> {
                    binding.priceTV.setText(Constants.SMALL_PRICE)
                }
                R.id.radioButtonMedium -> {
                    binding.priceTV.setText(Constants.MEDIUM_PRICE)
                }
                R.id.radioButtonLarge -> {
                    binding.priceTV.setText(Constants.LARGE_PRICE)
                }
            }
            binding.priceTV.text = selectedPrice
        }
    }
}