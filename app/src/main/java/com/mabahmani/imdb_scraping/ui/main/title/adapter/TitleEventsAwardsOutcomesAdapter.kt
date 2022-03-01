package com.mabahmani.imdb_scraping.ui.main.title.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mabahmani.domain.vo.TitleAwards
import com.mabahmani.imdb_scraping.databinding.ItemTitleEventsAwardsOutcomeBinding

class TitleEventsAwardsOutcomesAdapter: ListAdapter<TitleAwards.Event.Award.AwardOutcome, TitleEventsAwardsOutcomesAdapter.ViewHolder>(
    DiffCallback
)  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemTitleEventsAwardsOutcomeBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(itemView: ItemTitleEventsAwardsOutcomeBinding) :
        RecyclerView.ViewHolder(itemView.root) {

        private var binding: ItemTitleEventsAwardsOutcomeBinding

        init {
            itemView.executePendingBindings()
            binding = itemView
        }
        fun bind(model: TitleAwards.Event.Award.AwardOutcome){

            binding.awardName = model.subtitle
            binding.awardOutcome = model.title

            val adapter = TitleEventsAwardsOutcomesDescriptionsAdapter()

            binding.list.layoutManager = LinearLayoutManager(binding.root.context)
            binding.list.adapter = adapter

            adapter.submitList(model.awardDetails)
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<TitleAwards.Event.Award.AwardOutcome>() {
        override fun areItemsTheSame(oldItem: TitleAwards.Event.Award.AwardOutcome, newItem: TitleAwards.Event.Award.AwardOutcome): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: TitleAwards.Event.Award.AwardOutcome, newItem: TitleAwards.Event.Award.AwardOutcome): Boolean =
            oldItem == newItem
    }

}