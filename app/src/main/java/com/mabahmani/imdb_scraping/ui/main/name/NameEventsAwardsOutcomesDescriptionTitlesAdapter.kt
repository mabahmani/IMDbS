package com.mabahmani.imdb_scraping.ui.main.name

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mabahmani.domain.vo.NameAwards
import com.mabahmani.imdb_scraping.databinding.ItemNameEventsAwardsOutcomeDescriptionTitleBinding

class NameEventsAwardsOutcomesDescriptionTitlesAdapter (private val itemClickListener: (NameAwards.Event.Award.AwardOutcome.AwardDetail.AwardTitle) -> Unit) : ListAdapter<NameAwards.Event.Award.AwardOutcome.AwardDetail.AwardTitle, NameEventsAwardsOutcomesDescriptionTitlesAdapter.ViewHolder>(
    DiffCallback
)  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemNameEventsAwardsOutcomeDescriptionTitleBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            itemClickListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(itemView: ItemNameEventsAwardsOutcomeDescriptionTitleBinding, private val itemClickListener: (NameAwards.Event.Award.AwardOutcome.AwardDetail.AwardTitle) -> Unit) :
        RecyclerView.ViewHolder(itemView.root) {

        private var binding: ItemNameEventsAwardsOutcomeDescriptionTitleBinding

        init {
            itemView.executePendingBindings()
            binding = itemView
        }
        fun bind(model: NameAwards.Event.Award.AwardOutcome.AwardDetail.AwardTitle){

            binding.titleName = model.titleLink.title
            binding.titleYear = model.titleReleaseYear

            binding.root.setOnClickListener {
                itemClickListener.invoke(model)
            }
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<NameAwards.Event.Award.AwardOutcome.AwardDetail.AwardTitle>() {
        override fun areItemsTheSame(oldItem: NameAwards.Event.Award.AwardOutcome.AwardDetail.AwardTitle, newItem: NameAwards.Event.Award.AwardOutcome.AwardDetail.AwardTitle): Boolean =
            oldItem.titleLink.titleId.value == newItem.titleLink.titleId.value

        override fun areContentsTheSame(oldItem: NameAwards.Event.Award.AwardOutcome.AwardDetail.AwardTitle, newItem: NameAwards.Event.Award.AwardOutcome.AwardDetail.AwardTitle): Boolean =
            oldItem == newItem
    }

}