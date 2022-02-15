package com.mabahmani.imdb_scraping.ui.main.title

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mabahmani.domain.vo.common.Title
import com.mabahmani.imdb_scraping.databinding.ItemTitleDetailsGenreBinding
import com.mabahmani.imdb_scraping.databinding.ItemTitleDetailsRelatedMovieBinding

class TitleDetailsRelatedMoviesAdapter (private val itemClickListener: (Title) -> Unit) : ListAdapter<Title, TitleDetailsRelatedMoviesAdapter.ViewHolder>(
    DiffCallback
)  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemTitleDetailsRelatedMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            itemClickListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(itemView: ItemTitleDetailsRelatedMovieBinding, private val itemClickListener: (Title) -> Unit) :
        RecyclerView.ViewHolder(itemView.root) {

        private var binding: ItemTitleDetailsRelatedMovieBinding

        init {
            itemView.executePendingBindings()
            binding = itemView
        }
        fun bind(model: Title){
            binding.imageUrl = model.cover?.getCustomImageWidthUrl(320)
            binding.titleText = model.title
            binding.rateText = model.rate

            binding.root.setOnClickListener {
                itemClickListener.invoke(model)
            }
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<Title>() {
        override fun areItemsTheSame(oldItem: Title, newItem: Title): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: Title, newItem: Title): Boolean =
            oldItem == newItem
    }

}