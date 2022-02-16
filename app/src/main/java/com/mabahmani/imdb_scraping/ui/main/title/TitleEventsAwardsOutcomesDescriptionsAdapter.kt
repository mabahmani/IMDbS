package com.mabahmani.imdb_scraping.ui.main.title

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mabahmani.domain.vo.NameAwards
import com.mabahmani.domain.vo.TitleAwards
import com.mabahmani.imdb_scraping.databinding.ItemNameEventsAwardsOutcomeDescriptionBinding
import com.mabahmani.imdb_scraping.databinding.ItemNameEventsAwardsOutcomeDescriptionTitleBinding
import com.mabahmani.imdb_scraping.databinding.ItemTitleEventsAwardsOutcomeDescriptionBinding

class TitleEventsAwardsOutcomesDescriptionsAdapter (private val itemClickListener: (TitleAwards.Event.Award.AwardOutcome.AwardDetail) -> Unit) : ListAdapter<TitleAwards.Event.Award.AwardOutcome.AwardDetail, TitleEventsAwardsOutcomesDescriptionsAdapter.ViewHolder>(
    DiffCallback
)  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemTitleEventsAwardsOutcomeDescriptionBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            itemClickListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(itemView: ItemTitleEventsAwardsOutcomeDescriptionBinding, private val itemClickListener: (TitleAwards.Event.Award.AwardOutcome.AwardDetail) -> Unit) :
        RecyclerView.ViewHolder(itemView.root) {

        private var binding: ItemTitleEventsAwardsOutcomeDescriptionBinding

        init {
            itemView.executePendingBindings()
            binding = itemView
        }
        fun bind(model: TitleAwards.Event.Award.AwardOutcome.AwardDetail){

            binding.awardDescription = model.description

            val adapter = TitleEventsAwardsOutcomesDescriptionNamesAdapter{

            }

            binding.list.layoutManager = LinearLayoutManager(binding.root.context)
            binding.list.adapter = adapter

            adapter.submitList(model.awardNames)

            binding.root.setOnClickListener {
                itemClickListener.invoke(model)
            }
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<TitleAwards.Event.Award.AwardOutcome.AwardDetail>() {
        override fun areItemsTheSame(oldItem: TitleAwards.Event.Award.AwardOutcome.AwardDetail, newItem: TitleAwards.Event.Award.AwardOutcome.AwardDetail): Boolean =
            oldItem.description == newItem.description

        override fun areContentsTheSame(oldItem: TitleAwards.Event.Award.AwardOutcome.AwardDetail, newItem: TitleAwards.Event.Award.AwardOutcome.AwardDetail): Boolean =
            oldItem == newItem
    }

}