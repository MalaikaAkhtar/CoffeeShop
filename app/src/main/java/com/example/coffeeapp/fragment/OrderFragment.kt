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
import com.example.coffeeapp.constants.Constants
import com.example.coffeeapp.databinding.FragmentOrderBinding

class OrderFragment : Fragment() {

    private lateinit var binding: FragmentOrderBinding
    private var quantity: Int = 1
    private var coffeePrice = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrderBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val args: OrderFragmentArgs by navArgs()
//        val coffeeTitle = args.coffeeTitle
//        val coffeeImage = args.coffeeImage
//        coffeePrice = args.selectedPrice

        binding.apply {
//            coffeeName.text = coffeeTitle
            feeValue.text= Constants.DELIVERY_FEE.toString()
            backImageView.setOnClickListener {
                requireActivity().supportFragmentManager.popBackStack()
            }
            increment.setOnClickListener {
                quantity += 1
                setPrice(quantity)
            }
            decrement.setOnClickListener {
                if (quantity > 0) {
                    quantity -= 1
                    setPrice(quantity)
                }
            }
            orderBtn.setOnClickListener {
                val action = OrderFragmentDirections.actionOrderFragmentToMapFragment()
                findNavController().navigate(action)
            }
        }
//        Glide.with(this)
//            .load(coffeeImage)
//            .into(binding.coffeeImage)
//        setPrice(quantity)
    }
    private fun setPrice(itemQuantity: Int) {
        val totalPrice = (coffeePrice.toInt().times(itemQuantity))
        val totalCharges = totalPrice.plus(Constants.DELIVERY_FEE)
        binding.apply {
            qualityTV.text = itemQuantity.toString()
            priceValue.text = "$totalPrice"
            totalChargesTV.text = "$totalCharges"
        }
    }
}