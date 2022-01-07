package com.mabahmani.imdb_scraping.ui.main.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mabahmani.domain.vo.Home
import com.mabahmani.domain.vo.common.Title
import com.mabahmani.domain.vo.common.Trailer
import com.mabahmani.domain.vo.enum.HomeMediaType
import com.mabahmani.imdb_scraping.R
import com.mabahmani.imdb_scraping.databinding.ItemHomeMediaBinding
import com.mabahmani.imdb_scraping.databinding.ItemHomeMovieBinding
import com.mabahmani.imdb_scraping.databinding.ItemHomeTrailerBinding

class HomeMovieAdapter(private val itemClickListener: (Title) -> Unit) : ListAdapter<Title, HomeMovieAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            ItemHomeMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            itemClickListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    class ViewHolder(itemView: ItemHomeMovieBinding, private val itemClickListener: (Title) -> Unit) :
        RecyclerView.ViewHolder(itemView.root) {
            var binding: ItemHomeMovieBinding
            init {
                itemView.executePendingBindings()
                binding = itemView
            }
            fun bind(model: Title){
                binding.imageUrl = model.cover?.getCustomImageWidthUrl(512)
                binding.titleText = model.title
                binding.rateText = model.rate

                binding.root.setOnClickListener {
                    itemClickListener.invoke(model)
                }
            }
    }


    object DiffCallback : DiffUtil.ItemCallback<Title>() {
        override fun areItemsTheSame(oldItem: Title, newItem: Title): Boolean =
            oldItem.title == newItem.title

        override fun areContentsTheSame(oldItem: Title, newItem: Title): Boolean =
            oldItem == newItem
    }
}

