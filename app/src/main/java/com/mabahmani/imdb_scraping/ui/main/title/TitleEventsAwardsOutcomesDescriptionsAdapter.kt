package com.mabahmani.imdb_scraping.ui.main.title

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mabahmani.domain.vo.NameAwards
import com.mabahmani.domain.vo.TitleAwards
import com.mabahmani.domain.vo.common.Either
import com.mabahmani.imdb_scraping.R
import com.mabahmani.imdb_scraping.databinding.ItemNameEventsAwardsOutcomeDescriptionBinding
import com.mabahmani.imdb_scraping.databinding.ItemNameEventsAwardsOutcomeDescriptionTitleBinding
import com.mabahmani.imdb_scraping.databinding.ItemTitleEventsAwardsOutcomeDescriptionBinding
import com.mabahmani.imdb_scraping.util.toast

class TitleEventsAwardsOutcomesDescriptionsAdapter : ListAdapter<TitleAwards.Event.Award.AwardOutcome.AwardDetail, TitleEventsAwardsOutcomesDescriptionsAdapter.ViewHolder>(
    DiffCallback
)  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemTitleEventsAwardsOutcomeDescriptionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(itemView: ItemTitleEventsAwardsOutcomeDescriptionBinding) :
        RecyclerView.ViewHolder(itemView.root) {

        private var binding: ItemTitleEventsAwardsOutcomeDescriptionBinding

        init {
            itemView.executePendingBindings()
            binding = itemView
        }
        fun bind(model: TitleAwards.Event.Award.AwardOutcome.AwardDetail){

            binding.awardDescription = model.description

            val adapter = TitleEventsAwardsOutcomesDescriptionNamesAdapter{

                when (it.nameId.validate()) {
                    is Either.Right -> {
                        Navigation.findNavController(binding.root).navigate(R.id.nameDetailsFragment,
                            Bundle().apply {
                                putString("nameId", it.nameId.value)
                                putString("name", it.name)
                            }
                        )
                    }

                    else -> {
                        binding.root.context.toast(binding.root.context.getString(R.string.invalid_name_id))
                    }
                }
            }

            binding.list.layoutManager = LinearLayoutManager(binding.root.context)
            binding.list.adapter = adapter

            adapter.submitList(model.awardNames)

        }
    }

    object DiffCallback : DiffUtil.ItemCallback<TitleAwards.Event.Award.AwardOutcome.AwardDetail>() {
        override fun areItemsTheSame(oldItem: TitleAwards.Event.Award.AwardOutcome.AwardDetail, newItem: TitleAwards.Event.Award.AwardOutcome.AwardDetail): Boolean =
            oldItem.description == newItem.description

        override fun areContentsTheSame(oldItem: TitleAwards.Event.Award.AwardOutcome.AwardDetail, newItem: TitleAwards.Event.Award.AwardOutcome.AwardDetail): Boolean =
            oldItem == newItem
    }

}