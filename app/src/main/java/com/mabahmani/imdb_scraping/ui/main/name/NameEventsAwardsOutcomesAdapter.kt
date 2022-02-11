package com.mabahmani.imdb_scraping.ui.main.name

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mabahmani.domain.vo.NameAwards
import com.mabahmani.imdb_scraping.databinding.ItemNameEventsAwardsOutcomeBinding
import com.mabahmani.imdb_scraping.databinding.ItemNameEventsAwardsOutcomeDescriptionBinding
import com.mabahmani.imdb_scraping.databinding.ItemNameEventsAwardsOutcomeDescriptionTitleBinding

class NameEventsAwardsOutcomesAdapter (private val itemClickListener: (NameAwards.Event.Award.AwardOutcome) -> Unit) : ListAdapter<NameAwards.Event.Award.AwardOutcome, NameEventsAwardsOutcomesAdapter.ViewHolder>(
    DiffCallback
)  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemNameEventsAwardsOutcomeBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            itemClickListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(itemView: ItemNameEventsAwardsOutcomeBinding, private val itemClickListener: (NameAwards.Event.Award.AwardOutcome) -> Unit) :
        RecyclerView.ViewHolder(itemView.root) {

        private var binding: ItemNameEventsAwardsOutcomeBinding

        init {
            itemView.executePendingBindings()
            binding = itemView
        }
        fun bind(model: NameAwards.Event.Award.AwardOutcome){

            binding.awardName = model.subtitle
            binding.awardOutcome = model.title

            val adapter = NameEventsAwardsOutcomesDescriptionsAdapter{

            }

            binding.list.layoutManager = LinearLayoutManager(binding.root.context)
            binding.list.adapter = adapter

            adapter.submitList(model.awardDetails)

            binding.root.setOnClickListener {
                itemClickListener.invoke(model)
            }
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<NameAwards.Event.Award.AwardOutcome>() {
        override fun areItemsTheSame(oldItem: NameAwards.Event.Award.AwardOutcome, newItem: NameAwards.Event.Award.AwardOutcome): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: NameAwards.Event.Award.AwardOutcome, newItem: NameAwards.Event.Award.AwardOutcome): Boolean =
            oldItem == newItem
    }

}