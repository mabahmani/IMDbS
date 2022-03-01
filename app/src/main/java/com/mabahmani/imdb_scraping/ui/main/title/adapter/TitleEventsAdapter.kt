package com.mabahmani.imdb_scraping.ui.main.title.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mabahmani.domain.vo.TitleAwards
import com.mabahmani.domain.vo.common.EventId
import com.mabahmani.imdb_scraping.databinding.ItemTitleEventsBinding

class TitleEventsAdapter (private val itemClickListener: (EventId, String, String) -> Unit) : ListAdapter<TitleAwards.Event, TitleEventsAdapter.ViewHolder>(
    DiffCallback
)  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemTitleEventsBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            itemClickListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(itemView: ItemTitleEventsBinding, private val itemClickListener: (EventId,String,String) -> Unit) :
        RecyclerView.ViewHolder(itemView.root) {

        private var binding: ItemTitleEventsBinding

        init {
            itemView.executePendingBindings()
            binding = itemView
        }
        fun bind(model: TitleAwards.Event){

            binding.titleWidget.setTitle(model.name)
            binding.titleWidget.setTitleSize(16f)

            val adapter = TitleEventsAwardsAdapter{
                itemClickListener.invoke(it.eventId, it.eventYear, model.name)
            }

            binding.list.layoutManager = LinearLayoutManager(binding.root.context)
            binding.list.adapter = adapter

            adapter.submitList(model.awards)

        }
    }

    object DiffCallback : DiffUtil.ItemCallback<TitleAwards.Event>() {
        override fun areItemsTheSame(oldItem: TitleAwards.Event, newItem: TitleAwards.Event): Boolean =
            oldItem.name == newItem.name

        override fun areContentsTheSame(oldItem: TitleAwards.Event, newItem: TitleAwards.Event): Boolean =
            oldItem == newItem
    }

}