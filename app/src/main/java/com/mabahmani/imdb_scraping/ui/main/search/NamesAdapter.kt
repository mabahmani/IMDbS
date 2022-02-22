package com.mabahmani.imdb_scraping.ui.main.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mabahmani.domain.vo.common.Name
import com.mabahmani.imdb_scraping.databinding.ItemCelebBinding

class NamesAdapter(private val itemClickListener: (Name) -> Unit) :
    PagingDataAdapter<Name, NamesAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCelebBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            itemClickListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(itemView: ItemCelebBinding, private val itemClickListener: (Name) -> Unit) :
        RecyclerView.ViewHolder(itemView.root) {

        private var binding: ItemCelebBinding

        init {
            itemView.executePendingBindings()
            binding = itemView
        }

        fun bind(model: Name?) {
            binding.avatarUrl = model?.avatar?.getCustomImageWidthUrl(256)
            binding.name = model?.name
            binding.role = model?.topRole
            binding.title = model?.topTitle?.title
            binding.summary = model?.summary

            binding.root.setOnClickListener {
                if (model != null) {
                    itemClickListener.invoke(model)
                }
            }
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<Name>() {
        override fun areItemsTheSame(oldItem: Name, newItem: Name): Boolean =
            oldItem.nameId.value == newItem.nameId.value

        override fun areContentsTheSame(oldItem: Name, newItem: Name): Boolean =
            oldItem == newItem
    }

}