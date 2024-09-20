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
import com.example.coffeeapp.databinding.FragmentColdCoffeeBinding
import com.example.coffeeapp.dataclass.Coffee
import com.example.coffeeapp.viewmodel.CoffeeViewModel
import kotlinx.coroutines.launch


class ColdCoffeeFragment : Fragment() {

    private lateinit var binding: FragmentColdCoffeeBinding
    private lateinit var coffeeViewModel: CoffeeViewModel
    private lateinit var coffeeAdapter: CoffeeAdapter

    companion object {
        fun newInstance(onClick: (Coffee) -> Unit): ColdCoffeeFragment {
            val fragment = ColdCoffeeFragment()
            fragment.onClick = onClick
            return fragment
        }
    }
    var onClick: (Coffee) -> Unit = {}

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
            onClick.invoke(selectedCoffee)
        }

        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = coffeeAdapter
        }

        coffeeViewModel = ViewModelProvider(requireActivity())[CoffeeViewModel::class.java]

        coffeeViewModel.coldCoffeeList.observe(viewLifecycleOwner) { coldCoffeeList ->
            coffeeAdapter.updateCoffeeList(coldCoffeeList)
            binding.progressBar.visibility = View.GONE
        }

        viewLifecycleOwner.lifecycleScope.launch {
            coffeeViewModel.isLoading.collect { isLoading ->
                binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            }
        }
    }
}

