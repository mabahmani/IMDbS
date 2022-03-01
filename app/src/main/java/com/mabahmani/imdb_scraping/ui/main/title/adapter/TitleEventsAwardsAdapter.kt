package com.mabahmani.imdb_scraping.ui.main.title.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mabahmani.domain.vo.TitleAwards
import com.mabahmani.imdb_scraping.databinding.ItemTitleEventsAwardsBinding

class TitleEventsAwardsAdapter (private val itemClickListener: (TitleAwards.Event.Award) -> Unit) : ListAdapter<TitleAwards.Event.Award, TitleEventsAwardsAdapter.ViewHolder>(
    DiffCallback
)  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemTitleEventsAwardsBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            itemClickListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(itemView: ItemTitleEventsAwardsBinding, private val itemClickListener: (TitleAwards.Event.Award) -> Unit) :
        RecyclerView.ViewHolder(itemView.root) {

        private var binding: ItemTitleEventsAwardsBinding

        init {
            itemView.executePendingBindings()
            binding = itemView
        }
        fun bind(model: TitleAwards.Event.Award){

            binding.year = model.eventYear

            val adapter = TitleEventsAwardsOutcomesAdapter()

            binding.list.layoutManager = LinearLayoutManager(binding.root.context)
            binding.list.adapter = adapter

            adapter.submitList(model.awardOutcomes)

            binding.root.setOnClickListener {
                itemClickListener.invoke(model)
            }
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<TitleAwards.Event.Award>() {
        override fun areItemsTheSame(oldItem: TitleAwards.Event.Award, newItem: TitleAwards.Event.Award): Boolean =
            oldItem.eventId.value == newItem.eventId.value

        override fun areContentsTheSame(oldItem: TitleAwards.Event.Award, newItem: TitleAwards.Event.Award): Boolean =
            oldItem == newItem
    }

}