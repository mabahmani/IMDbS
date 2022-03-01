package com.mabahmani.imdb_scraping.ui.main.name.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mabahmani.domain.vo.common.TextLink
import com.mabahmani.imdb_scraping.databinding.ItemNameBiographyBinding

class NameBiographyOverviewAdapter () : ListAdapter<TextLink, NameBiographyOverviewAdapter.ViewHolder>(
    DiffCallback
)  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemNameBiographyBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(itemView: ItemNameBiographyBinding) :
        RecyclerView.ViewHolder(itemView.root) {

        private var binding: ItemNameBiographyBinding

        init {
            itemView.executePendingBindings()
            binding = itemView
        }
        fun bind(model: TextLink){

            binding.title = model.title
            binding.subtitle = model.subtitle

        }
    }

    object DiffCallback : DiffUtil.ItemCallback<TextLink>() {
        override fun areItemsTheSame(oldItem: TextLink, newItem: TextLink): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: TextLink, newItem: TextLink): Boolean =
            oldItem == newItem
    }

}