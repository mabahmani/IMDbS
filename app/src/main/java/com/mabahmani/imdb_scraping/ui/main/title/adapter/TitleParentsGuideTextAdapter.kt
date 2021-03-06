package com.mabahmani.imdb_scraping.ui.main.title.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mabahmani.imdb_scraping.databinding.ItemTitleParentsGuideTextBinding

class TitleParentsGuideTextAdapter : ListAdapter<String, TitleParentsGuideTextAdapter.ViewHolder>(
    DiffCallback
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemTitleParentsGuideTextBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(itemView: ItemTitleParentsGuideTextBinding) :
        RecyclerView.ViewHolder(itemView.root) {

        private var binding: ItemTitleParentsGuideTextBinding

        init {
            itemView.executePendingBindings()
            binding = itemView
        }

        fun bind(model: String) {

            binding.text = model

        }
    }

    object DiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean =
            oldItem == newItem
    }

}