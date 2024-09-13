package com.example.coffeeapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.coffeeapp.adapter.CoffeeAdapter
import com.example.coffeeapp.databinding.FragmentHotCoffeeBinding
import com.example.coffeeapp.dataclass.Coffee
import com.example.coffeeapp.viewmodel.CoffeeViewModel
import kotlinx.coroutines.launch

class HotCoffeeFragment : Fragment() {

    private lateinit var binding: FragmentHotCoffeeBinding
    private lateinit var coffeeViewModel: CoffeeViewModel
    private lateinit var coffeeAdapter: CoffeeAdapter

    companion object {
        fun newInstance(onClick: (Coffee) -> Unit): HotCoffeeFragment {
            val fragment = HotCoffeeFragment()
            fragment.onClick = onClick
            return fragment
        }
    }
    private var onClick: (Coffee) -> Unit = {}
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHotCoffeeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        coffeeAdapter = CoffeeAdapter(emptyList()) { selectedCoffee ->
            onClick.invoke(selectedCoffee)

        }
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = coffeeAdapter
        }

        coffeeViewModel = ViewModelProvider(requireActivity())[CoffeeViewModel::class.java]

        viewLifecycleOwner.lifecycleScope.launch {
            coffeeViewModel.hotCoffeeList.collect { hotCoffeeList ->
                coffeeAdapter.updateCoffeeList(hotCoffeeList)
                binding.progressBar.visibility = View.GONE
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            coffeeViewModel.isLoading.collect { isLoading ->
                if (isLoading) {
                    binding.progressBar.visibility = View.VISIBLE
                } else {
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }
}