package com.mabahmani.imdb_scraping.ui.main.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mabahmani.domain.vo.common.Title
import com.mabahmani.imdb_scraping.databinding.ItemHomeMovieBinding

class HomeMovieAdapter(private val itemClickListener: (Boolean, Title) -> Unit) : ListAdapter<Title, HomeMovieAdapter.ViewHolder>(
    DiffCallback
) {

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


    class ViewHolder(itemView: ItemHomeMovieBinding, private val itemClickListener: (Boolean, Title) -> Unit) :
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
                    itemClickListener.invoke(false, model)
                }

                binding.trailerParent.setOnClickListener{
                    itemClickListener.invoke(true, model)
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

