package com.mabahmani.imdb_scraping.ui.main.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mabahmani.domain.vo.common.Genre
import com.mabahmani.imdb_scraping.databinding.ItemGenreBinding

class GenresAdapter (private val itemClickListener: (Genre) -> Unit) : ListAdapter<Genre, GenresAdapter.ViewHolder>(
    DiffCallback
)  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemGenreBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            itemClickListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(itemView: ItemGenreBinding, private val itemClickListener: (Genre) -> Unit) :
        RecyclerView.ViewHolder(itemView.root) {

        private var binding: ItemGenreBinding

        init {
            itemView.executePendingBindings()
            binding = itemView
        }
        fun bind(model: Genre){
            binding.imageUrl = model.image.getOriginalImageSizeUrl()

            binding.root.setOnClickListener {
                itemClickListener.invoke(model)
            }
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<Genre>() {
        override fun areItemsTheSame(oldItem: Genre, newItem: Genre): Boolean =
            oldItem.name == newItem.name

        override fun areContentsTheSame(oldItem: Genre, newItem: Genre): Boolean =
            oldItem == newItem
    }

}