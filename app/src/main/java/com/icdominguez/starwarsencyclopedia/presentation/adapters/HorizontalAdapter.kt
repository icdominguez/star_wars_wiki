package com.icdominguez.starwarsencyclopedia.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.icdominguez.starwarsencyclopedia.R
import com.icdominguez.starwarsencyclopedia.databinding.HorizontalRecyclerViewItemBinding
import com.icdominguez.starwarsencyclopedia.domain.model.detail.*

class HorizontalAdapter<T : Any>(
    private var context: Context,
    private var list: List<T>,
    private val onItemClicked: (item: T) -> Unit, /*, private val onFilmClicked: (film: Film) -> Unit*/
) : RecyclerView.Adapter<HorizontalAdapter.ViewHolder>() {

    class ViewHolder(val binding: HorizontalRecyclerViewItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        val binding =
            HorizontalRecyclerViewItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = list[position]

        with(holder) {
            when (item) {
                is FilmDetail -> {
                    with(binding) {
                        Glide.with(context).load(if (item.photo.isNotEmpty()) item.photo else R.drawable.no_picture).into(roundedImageView)
                        textViewTitle.text = item.name.lowercase()
                        textViewItemDescription.text = item.director.lowercase()
                    }

                    itemView.setOnClickListener {
                        onItemClicked.invoke(item)
                    }
                }
                is VehicleDetail -> {
                    with(binding) {
                        Glide.with(context).load(if (item.photo.isNotEmpty()) item.photo else R.drawable.no_picture).into(roundedImageView)
                        textViewTitle.text = item.name.lowercase()
                        textViewItemDescription.text = item.model.lowercase()
                    }
                    itemView.setOnClickListener {
                        onItemClicked.invoke(item)
                    }
                }
                is StarshipDetail -> {
                    with(binding) {
                        Glide.with(context)
                            .load(if (item.photo.isNotEmpty()) item.photo else R.drawable.no_picture)
                            .into(roundedImageView)
                        textViewTitle.text = item.name.lowercase()
                        textViewItemDescription.text = item.model.lowercase()
                    }

                    itemView.setOnClickListener {
                        onItemClicked.invoke(item)
                    }
                }
                is PlanetDetail -> {
                    with(binding) {
                        Glide.with(context).load(if (item.photo.isNotEmpty()) item.photo else R.drawable.no_picture).into(binding.roundedImageView)
                        textViewTitle.text = item.name.lowercase()
                        textViewItemDescription.text = item.population.lowercase()
                    }

                    itemView.setOnClickListener {
                        onItemClicked.invoke(item)
                    }
                }
                is SpecieDetail -> {
                    with(binding) {
                        Glide.with(context).load(if (item.photo.isNotEmpty()) item.photo else R.drawable.no_picture).into(roundedImageView)
                        textViewTitle.text = item.name.lowercase()
                        textViewItemDescription.text = item.language.lowercase()
                    }

                    itemView.setOnClickListener {
                        onItemClicked.invoke(item)
                    }
                }
                is CharacterDetail -> {
                    with(binding) {
                        Glide.with(context).load(if (item.photo.isNotEmpty()) item.photo else R.drawable.no_picture).into(roundedImageView)
                        textViewTitle.text = item.name.lowercase()
                        textViewItemDescription.text = item.name.lowercase()
                    }
                    itemView.setOnClickListener {
                        onItemClicked.invoke(item)
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}
