package com.example.coffeeapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import com.bumptech.glide.Glide
import com.example.coffeeapp.constants.Constants
import com.example.coffeeapp.databinding.ActivityOrderBinding

class OrderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrderBinding
    private var quantity: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val coffeeTitle = intent.getStringExtra("COFFEE_TITLE")
        val coffeeImage = intent.getStringExtra("COFFEE_IMAGE")
        val selectedPrice = intent.getStringExtra("SELECTED_PRICE")

        binding.coffeeName.text = coffeeTitle
        binding.priceValue.text = selectedPrice

        Glide.with(this)
            .load(coffeeImage)
            .into(binding.coffeeImage)

        binding.backImageView.setOnClickListener {
            val intent = Intent(this, DetailsActivity::class.java)
            startActivity(intent)
        }

        binding.increment.setOnClickListener {
            quantity += 1
            binding.qualityTV.text = quantity.toString()
        }

        binding.feeValue.setText(Constants.DELIVERY_FEE)
    }
}