package com.mabahmani.imdb_scraping.ui.main.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mabahmani.domain.vo.common.TitleLink
import com.mabahmani.imdb_scraping.databinding.ItemCalenderTitleBinding

class CalenderTitleAdapter (private val itemClickListener: (TitleLink) -> Unit) : ListAdapter<TitleLink, CalenderTitleAdapter.ViewHolder>(
    DiffCallback
)  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCalenderTitleBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            itemClickListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(itemView: ItemCalenderTitleBinding, private val itemClickListener: (TitleLink) -> Unit) :
        RecyclerView.ViewHolder(itemView.root) {

        private var binding: ItemCalenderTitleBinding

        init {
            itemView.executePendingBindings()
            binding = itemView
        }
        fun bind(model: TitleLink){
            binding.title = model.title

            binding.root.setOnClickListener {
                itemClickListener.invoke(model)
            }
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<TitleLink>() {
        override fun areItemsTheSame(oldItem: TitleLink, newItem: TitleLink): Boolean =
            oldItem.titleId.value == newItem.titleId.value

        override fun areContentsTheSame(oldItem: TitleLink, newItem: TitleLink): Boolean =
            oldItem == newItem
    }

}