package com.example.coffeeapp.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        var title = ""
        var image = ""
        var description = ""
        arguments?.let {
          title = it.getString("COFFEE_TITLE").toString()
         image = it.getString("COFFEE_IMAGE").toString()
          description = it.getString("COFFEE_DESCRIPTION").toString()

        }
//        Glide.with(this)
//            .load(coffeeImage)
//            .into(binding.itemDetailIV)
        binding.apply {
            coffeNameTV.text = title
            descriptionTV.text = description

            backIV.setOnClickListener {
                requireActivity().supportFragmentManager.popBackStack()
            }
//            buyNowButton.setOnClickListener {
//                val intent = Intent(requireContext(), OrderFragment::class.java)
//                intent.putExtra("COFFEE_TITLE", title)
//                intent.putExtra("COFFEE_IMAGE", image)
//                intent.putExtra("SELECTED_PRICE", binding.priceTV.text.toString()
//                )
//                startActivity(intent)
//            }
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

    companion object {
        fun newInstance(title: String, image: String, description: String): DetailFragment {
            val fragment = DetailFragment()
            val args = Bundle().apply {
                putString("COFFEE_TITLE", title)
                putString("COFFEE_IMAGE", image)
                putString("COFFEE_DESCRIPTION", description)
            }
            fragment.arguments = args
            return fragment
        }
    }
}