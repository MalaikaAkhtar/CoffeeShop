package com.example.coffeeapp.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.coffeeapp.R
import com.example.coffeeapp.constants.Constants
import com.example.coffeeapp.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args: DetailFragmentArgs by navArgs()
        val title = args.coffeeTitle
        val image = args.coffeeImage
        val description = args.coffeeDescription

        binding.apply {
            coffeNameTV.text = title
            descriptionTV.text = description
            Glide.with(this@DetailFragment).load(args.coffeeImage).into(itemDetailIV)
        }

        binding.apply {
            coffeNameTV.text = title
            descriptionTV.text = description

            backIV.setOnClickListener {
                requireActivity().supportFragmentManager.popBackStack()
            }
            buyNowButton.setOnClickListener {
                val action = DetailFragmentDirections.actionDetailFragmentToOrderFragment(
                    coffeeTitle = title,
                    coffeeImage = image,
                    selectedPrice = binding.priceTV.text.toString()
                )
                findNavController().navigate(action)
            }
            priceTV.text = Constants.MEDIUM_PRICE.toString()
            radioGroup.setOnCheckedChangeListener { group, checkedId ->
                when (checkedId) {
                    R.id.radioButtonSmall -> {
                        binding.priceTV.text=Constants.SMALL_PRICE.toString()
                    }
                    R.id.radioButtonMedium -> {
                        binding.priceTV.setText(Constants.MEDIUM_PRICE)
                    }
                    R.id.radioButtonLarge -> {
                        binding.priceTV.text=Constants.LARGE_PRICE.toString()
                    }
                }
            }
        }
    }
}

