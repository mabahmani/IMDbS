package com.mabahmani.imdb_scraping.ui.main.title

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mabahmani.domain.vo.common.NameLink
import com.mabahmani.imdb_scraping.databinding.ItemNameEventsAwardsOutcomeDescriptionTitleBinding
import com.mabahmani.imdb_scraping.databinding.ItemTitleEventsAwardsOutcomeDescriptionNameBinding

class TitleEventsAwardsOutcomesDescriptionNamesAdapter (private val itemClickListener: (NameLink) -> Unit) : ListAdapter<NameLink, TitleEventsAwardsOutcomesDescriptionNamesAdapter.ViewHolder>(
    DiffCallback
)  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemTitleEventsAwardsOutcomeDescriptionNameBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            itemClickListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(itemView: ItemTitleEventsAwardsOutcomeDescriptionNameBinding, private val itemClickListener: (NameLink) -> Unit) :
        RecyclerView.ViewHolder(itemView.root) {

        private var binding: ItemTitleEventsAwardsOutcomeDescriptionNameBinding

        init {
            itemView.executePendingBindings()
            binding = itemView
        }
        fun bind(model: NameLink){

            binding.name = model.name

            binding.root.setOnClickListener {
                itemClickListener.invoke(model)
            }
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<NameLink>() {
        override fun areItemsTheSame(oldItem: NameLink, newItem: NameLink): Boolean =
            oldItem.nameId.value == newItem.nameId.value

        override fun areContentsTheSame(oldItem: NameLink, newItem: NameLink): Boolean =
            oldItem == newItem
    }

}