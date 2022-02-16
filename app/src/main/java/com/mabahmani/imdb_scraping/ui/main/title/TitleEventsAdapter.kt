package com.mabahmani.imdb_scraping.ui.main.title

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mabahmani.domain.vo.NameAwards
import com.mabahmani.domain.vo.TitleAwards
import com.mabahmani.imdb_scraping.databinding.*

class TitleEventsAdapter (private val itemClickListener: (TitleAwards.Event) -> Unit) : ListAdapter<TitleAwards.Event, TitleEventsAdapter.ViewHolder>(
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

    class ViewHolder(itemView: ItemTitleEventsBinding, private val itemClickListener: (TitleAwards.Event) -> Unit) :
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

            }

            binding.list.layoutManager = LinearLayoutManager(binding.root.context)
            binding.list.adapter = adapter

            adapter.submitList(model.awards)

            binding.root.setOnClickListener {
                itemClickListener.invoke(model)
            }
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<TitleAwards.Event>() {
        override fun areItemsTheSame(oldItem: TitleAwards.Event, newItem: TitleAwards.Event): Boolean =
            oldItem.name == newItem.name

        override fun areContentsTheSame(oldItem: TitleAwards.Event, newItem: TitleAwards.Event): Boolean =
            oldItem == newItem
    }

}