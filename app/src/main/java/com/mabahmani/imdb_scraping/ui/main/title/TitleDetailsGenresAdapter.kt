package com.mabahmani.imdb_scraping.ui.main.title

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mabahmani.imdb_scraping.databinding.ItemTitleDetailsGenreBinding

class TitleDetailsGenresAdapter (private val itemClickListener: (String) -> Unit) : ListAdapter<String, TitleDetailsGenresAdapter.ViewHolder>(
    DiffCallback
)  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemTitleDetailsGenreBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            itemClickListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(itemView: ItemTitleDetailsGenreBinding, private val itemClickListener: (String) -> Unit) :
        RecyclerView.ViewHolder(itemView.root) {

        private var binding: ItemTitleDetailsGenreBinding

        init {
            itemView.executePendingBindings()
            binding = itemView
        }
        fun bind(model: String){
            binding.genre = model

            binding.root.setOnClickListener {
                itemClickListener.invoke(model)
            }
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean =
            oldItem == newItem
    }

}