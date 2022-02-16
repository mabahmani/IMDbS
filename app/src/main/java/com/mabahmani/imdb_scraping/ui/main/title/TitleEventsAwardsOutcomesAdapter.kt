package com.mabahmani.imdb_scraping.ui.main.title

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mabahmani.domain.vo.NameAwards
import com.mabahmani.domain.vo.TitleAwards
import com.mabahmani.imdb_scraping.databinding.ItemNameEventsAwardsOutcomeBinding
import com.mabahmani.imdb_scraping.databinding.ItemNameEventsAwardsOutcomeDescriptionBinding
import com.mabahmani.imdb_scraping.databinding.ItemNameEventsAwardsOutcomeDescriptionTitleBinding
import com.mabahmani.imdb_scraping.databinding.ItemTitleEventsAwardsOutcomeBinding

class TitleEventsAwardsOutcomesAdapter (private val itemClickListener: (TitleAwards.Event.Award.AwardOutcome) -> Unit) : ListAdapter<TitleAwards.Event.Award.AwardOutcome, TitleEventsAwardsOutcomesAdapter.ViewHolder>(
    DiffCallback
)  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemTitleEventsAwardsOutcomeBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            itemClickListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(itemView: ItemTitleEventsAwardsOutcomeBinding, private val itemClickListener: (TitleAwards.Event.Award.AwardOutcome) -> Unit) :
        RecyclerView.ViewHolder(itemView.root) {

        private var binding: ItemTitleEventsAwardsOutcomeBinding

        init {
            itemView.executePendingBindings()
            binding = itemView
        }
        fun bind(model: TitleAwards.Event.Award.AwardOutcome){

            binding.awardName = model.subtitle
            binding.awardOutcome = model.title

            val adapter = TitleEventsAwardsOutcomesDescriptionsAdapter{

            }

            binding.list.layoutManager = LinearLayoutManager(binding.root.context)
            binding.list.adapter = adapter

            adapter.submitList(model.awardDetails)

            binding.root.setOnClickListener {
                itemClickListener.invoke(model)
            }
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<TitleAwards.Event.Award.AwardOutcome>() {
        override fun areItemsTheSame(oldItem: TitleAwards.Event.Award.AwardOutcome, newItem: TitleAwards.Event.Award.AwardOutcome): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: TitleAwards.Event.Award.AwardOutcome, newItem: TitleAwards.Event.Award.AwardOutcome): Boolean =
            oldItem == newItem
    }

}