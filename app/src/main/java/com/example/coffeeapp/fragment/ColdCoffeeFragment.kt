package com.example.coffeeapp.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.navigation.fragment.findNavController
import com.example.coffeeapp.R
import com.example.coffeeapp.adapter.CoffeeAdapter
import com.example.coffeeapp.databinding.FragmentColdCoffeeBinding
import com.example.coffeeapp.fragment.HotCoffeeFragment.Companion
import com.example.coffeeapp.viewmodel.CoffeeViewModel
import kotlinx.coroutines.launch


class ColdCoffeeFragment : Fragment() {

    private lateinit var binding: FragmentColdCoffeeBinding
    private lateinit var coffeeViewModel: CoffeeViewModel
    private lateinit var coffeeAdapter: CoffeeAdapter

    companion object {
        private var onClick: () -> Unit = {}

        fun instance(onClick: () -> Unit): ColdCoffeeFragment {
            this.onClick = onClick
            return ColdCoffeeFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentColdCoffeeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        coffeeAdapter = CoffeeAdapter(emptyList()) { selectedCoffee ->
            onClick.invoke()

//            Log.d("ColdCoffeeFragment",
//                "Selected Coffee: Title = ${selectedCoffee.title}," +
//                    " Image = ${selectedCoffee.image}, Description = ${selectedCoffee.description}")
//
//            val action = ColdCoffeeFragmentDirections.actionColdCoffeeFragmentToDetailFragment(
//                selectedCoffee.title,
//                selectedCoffee.image,
//                selectedCoffee.description
//            )
//            findNavController().navigate(action)
//            Navigation.findNavController(binding.root).navigate(R.id.action_coldCoffeeFragment_to_detailFragment)

        }

        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = coffeeAdapter
        }

        coffeeViewModel = ViewModelProvider(requireActivity())[CoffeeViewModel::class.java]
        viewLifecycleOwner.lifecycleScope.launch {
            coffeeViewModel.coldCoffeeList.collect { coldCoffeeList ->
                coffeeAdapter.updateCoffeeList(coldCoffeeList)
                binding.progressBar.visibility = View.GONE
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            coffeeViewModel.isLoading.collect { isLoading ->
                if (isLoading) {
                    binding.progressBar.visibility = View.VISIBLE // Show progress bar
                } else {
                    binding.progressBar.visibility = View.GONE // Hide progress bar
                }
            }
        }
    }
}

