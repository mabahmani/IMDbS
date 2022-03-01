package com.mabahmani.imdb_scraping.ui.main.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mabahmani.imdb_scraping.databinding.ItemKeywordBinding

class KeywordsAdapter(private val itemClickListener: (String) -> Unit) :
    ListAdapter<String, KeywordsAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemKeywordBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            itemClickListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(itemView: ItemKeywordBinding, private val itemClickListener: (String) -> Unit) :
        RecyclerView.ViewHolder(itemView.root) {

        private var binding: ItemKeywordBinding

        init {
            itemView.executePendingBindings()
            binding = itemView
        }

        fun bind(model: String?) {

            binding.keyword = model

            binding.parent.setOnClickListener {
                itemClickListener.invoke(model.orEmpty())
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