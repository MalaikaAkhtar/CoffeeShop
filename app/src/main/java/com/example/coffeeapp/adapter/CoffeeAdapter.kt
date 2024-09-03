package com.example.coffeeapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.coffeeapp.R
import com.example.coffeeapp.dataclass.Coffee

class CoffeeAdapter(private var coffeeList: List<Coffee>,
                    private val onItemClick: (Coffee) -> Unit)
    : RecyclerView.Adapter<CoffeeAdapter.CoffeeViewHolder>() {

  //  private var filteredCoffeeList: List<Coffee> = coffeeList

    inner class CoffeeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val coffeeImage: ImageView = view.findViewById(R.id.itemIV)
        val coffeeName: TextView = view.findViewById(R.id.coffeeItemTV)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoffeeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.coffee_items, parent, false)
        return CoffeeViewHolder(view)
    }

    override fun onBindViewHolder(holder: CoffeeViewHolder, position: Int) {
        val coffee =coffeeList[position]

        holder.coffeeName.text = coffee.title
        Glide.with(holder.itemView.context)
            .load(coffee.image)
            .into(holder.coffeeImage)

        holder.itemView.setOnClickListener {
            onItemClick.invoke(coffee)
        }
    }

    override fun getItemCount() = coffeeList.size

//    override fun getFilter(): Filter {
//        return object : Filter() {
//            override fun performFiltering(constraint: CharSequence?): FilterResults {
//                val query = constraint?.toString()?.lowercase()?.trim()
//                val filteredList = if (query.isNullOrEmpty()) {
//                    coffeeList
//                } else {
//                    coffeeList.filter {
//                        it.title.lowercase().contains(query) ||
//                                it.description?.lowercase()?.contains(query) ?: false
//                    }
//                }
//                return FilterResults().apply { values = filteredList }
//            }
//
//            @Suppress("UNCHECKED_CAST")
//            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
//                filteredCoffeeList = results?.values as? List<Coffee> ?: emptyList()
//                notifyDataSetChanged()
//            }
//        }
//    }

    fun updateCoffeeList(newCoffeeList: List<Coffee>) {
        coffeeList = newCoffeeList
        notifyDataSetChanged()
    }

}