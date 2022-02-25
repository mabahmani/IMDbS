package com.mabahmani.imdb_scraping.ui.main.name

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mabahmani.domain.vo.NameAwards
import com.mabahmani.domain.vo.common.Either
import com.mabahmani.imdb_scraping.R
import com.mabahmani.imdb_scraping.databinding.ItemNameEventsAwardsOutcomeDescriptionBinding
import com.mabahmani.imdb_scraping.databinding.ItemNameEventsAwardsOutcomeDescriptionTitleBinding
import com.mabahmani.imdb_scraping.util.toast

class NameEventsAwardsOutcomesDescriptionsAdapter : ListAdapter<NameAwards.Event.Award.AwardOutcome.AwardDetail, NameEventsAwardsOutcomesDescriptionsAdapter.ViewHolder>(
    DiffCallback
)  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemNameEventsAwardsOutcomeDescriptionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(itemView: ItemNameEventsAwardsOutcomeDescriptionBinding) :
        RecyclerView.ViewHolder(itemView.root) {

        private var binding: ItemNameEventsAwardsOutcomeDescriptionBinding

        init {
            itemView.executePendingBindings()
            binding = itemView
        }
        fun bind(model: NameAwards.Event.Award.AwardOutcome.AwardDetail){

            binding.awardDescription = model.description

            val adapter = NameEventsAwardsOutcomesDescriptionTitlesAdapter{
                when (it.titleLink.titleId.validate()) {
                    is Either.Right -> {
                        Navigation.findNavController(binding.root).navigate(
                            R.id.titleDetailsFragment,
                            Bundle().apply {
                                putString("titleId", it.titleLink.titleId.value)
                                putString("title", it.titleLink.title)
                            }
                        )
                    }

                    else -> {
                        binding.root.context.toast(binding.root.context.getString(R.string.invalid_title_id))
                    }
                }
            }

            binding.list.layoutManager = LinearLayoutManager(binding.root.context)
            binding.list.adapter = adapter

            adapter.submitList(model.awardTitles)

        }
    }

    object DiffCallback : DiffUtil.ItemCallback<NameAwards.Event.Award.AwardOutcome.AwardDetail>() {
        override fun areItemsTheSame(oldItem: NameAwards.Event.Award.AwardOutcome.AwardDetail, newItem: NameAwards.Event.Award.AwardOutcome.AwardDetail): Boolean =
            oldItem.description == newItem.description

        override fun areContentsTheSame(oldItem: NameAwards.Event.Award.AwardOutcome.AwardDetail, newItem: NameAwards.Event.Award.AwardOutcome.AwardDetail): Boolean =
            oldItem == newItem
    }

}