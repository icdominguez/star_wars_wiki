package com.icdominguez.starwarsencyclopedia.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.icdominguez.starwarsencyclopedia.R
import com.icdominguez.starwarsencyclopedia.databinding.CardViewItemBinding
import com.icdominguez.starwarsencyclopedia.domain.model.Item

class GenericAdapter(
    private var context: Context,
    private var itemList: ArrayList<Item>,
    private val onItemClicked: (name: String) -> Unit
) : RecyclerView.Adapter<GenericAdapter.ViewHolder>(), Filterable {

    var itemListFilter = ArrayList<Item>()

    class ViewHolder(val binding: CardViewItemBinding) : RecyclerView.ViewHolder(binding.root)

    init {
        itemListFilter = itemList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CardViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemListFilter[position]

        with(holder) {
            binding.textViewItemDescription.text = item.name.lowercase()
            binding.textViewItemDescription.isSelected = true

            itemView.setOnClickListener { onItemClicked.invoke(item.name) }

            if (item.photo.isNotEmpty()) {
                Glide.with(context).load(item.photo).into(binding.imageViewItem)
            } else {
                Glide.with(context).load(R.drawable.no_picture).into(binding.imageViewItem)
            }
        }
    }

    override fun getItemCount(): Int {
        return itemListFilter.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                val search = charSequence.toString()

                itemListFilter = if (search.isEmpty()) {
                    itemList
                } else {
                    val resultList = ArrayList<Item>()
                    itemList.forEach { item ->
                        if (item.name.lowercase().contains(search)) {
                            resultList.add(item)
                        }
                    }
                    resultList
                }

                val filterResults = FilterResults()
                filterResults.values = itemListFilter
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence?, results: FilterResults?) {
                itemListFilter = results?.values as ArrayList<Item>
                notifyDataSetChanged()
            }
        }
    }
}
