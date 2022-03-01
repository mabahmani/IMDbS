package com.mabahmani.imdb_scraping.ui.main.name.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mabahmani.domain.vo.NameAwards
import com.mabahmani.domain.vo.common.EventId
import com.mabahmani.imdb_scraping.databinding.ItemNameEventsBinding

class NameEventsAdapter (private val itemClickListener: (EventId,String,String) -> Unit) : ListAdapter<NameAwards.Event, NameEventsAdapter.ViewHolder>(
    DiffCallback
)  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemNameEventsBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            itemClickListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(itemView: ItemNameEventsBinding, private val itemClickListener: (EventId,String,String) -> Unit) :
        RecyclerView.ViewHolder(itemView.root) {

        private var binding: ItemNameEventsBinding

        init {
            itemView.executePendingBindings()
            binding = itemView
        }
        fun bind(model: NameAwards.Event){

            binding.titleWidget.setTitle(model.name)
            binding.titleWidget.setTitleSize(16f)

            val adapter = NameEventsAwardsAdapter{
                itemClickListener.invoke(it.eventId, it.eventYear, model.name)
            }

            binding.list.layoutManager = LinearLayoutManager(binding.root.context)
            binding.list.adapter = adapter

            adapter.submitList(model.awards)

        }
    }

    object DiffCallback : DiffUtil.ItemCallback<NameAwards.Event>() {
        override fun areItemsTheSame(oldItem: NameAwards.Event, newItem: NameAwards.Event): Boolean =
            oldItem.name == newItem.name

        override fun areContentsTheSame(oldItem: NameAwards.Event, newItem: NameAwards.Event): Boolean =
            oldItem == newItem
    }

}