package com.mabahmani.imdb_scraping.ui.main.name.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mabahmani.domain.vo.NameAwards
import com.mabahmani.imdb_scraping.databinding.ItemNameEventsAwardsBinding

class NameEventsAwardsAdapter (private val itemClickListener: (NameAwards.Event.Award) -> Unit) : ListAdapter<NameAwards.Event.Award, NameEventsAwardsAdapter.ViewHolder>(
    DiffCallback
)  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemNameEventsAwardsBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            itemClickListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(itemView: ItemNameEventsAwardsBinding, private val itemClickListener: (NameAwards.Event.Award) -> Unit) :
        RecyclerView.ViewHolder(itemView.root) {

        private var binding: ItemNameEventsAwardsBinding

        init {
            itemView.executePendingBindings()
            binding = itemView
        }
        fun bind(model: NameAwards.Event.Award){

            binding.year = model.eventYear

            val adapter = NameEventsAwardsOutcomesAdapter()

            binding.list.layoutManager = LinearLayoutManager(binding.root.context)
            binding.list.adapter = adapter

            adapter.submitList(model.awardOutcomes)

            binding.root.setOnClickListener {
                itemClickListener.invoke(model)
            }

        }
    }

    object DiffCallback : DiffUtil.ItemCallback<NameAwards.Event.Award>() {
        override fun areItemsTheSame(oldItem: NameAwards.Event.Award, newItem: NameAwards.Event.Award): Boolean =
            oldItem.eventId.value == newItem.eventId.value

        override fun areContentsTheSame(oldItem: NameAwards.Event.Award, newItem: NameAwards.Event.Award): Boolean =
            oldItem == newItem
    }

}