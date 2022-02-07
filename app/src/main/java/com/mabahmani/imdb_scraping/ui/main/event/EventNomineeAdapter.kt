package com.mabahmani.imdb_scraping.ui.main.event

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mabahmani.domain.vo.EventDetails
import com.mabahmani.imdb_scraping.databinding.ItemEventNomineeBinding
import com.mabahmani.imdb_scraping.databinding.ItemEventSubNomineeBinding

class EventNomineeAdapter (private val itemClickListener: (EventDetails.Award.AwardCategory.Nominee) -> Unit) : ListAdapter<EventDetails.Award.AwardCategory.Nominee, EventNomineeAdapter.ViewHolder>(
    DiffCallback
)  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemEventNomineeBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            itemClickListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(itemView: ItemEventNomineeBinding, private val itemClickListener: (EventDetails.Award.AwardCategory.Nominee) -> Unit) :
        RecyclerView.ViewHolder(itemView.root) {

        private var binding: ItemEventNomineeBinding

        init {
            itemView.executePendingBindings()
            binding = itemView
        }
        fun bind(model: EventDetails.Award.AwardCategory.Nominee){
            binding.imageUrl = model.image.getCustomImageWidthUrl(512)
            binding.isWinner = model.winner
            binding.note = model.note

            val adapter = EventSubNomineeAdapter{

            }
            binding.list.layoutManager = LinearLayoutManager(binding.root.context)
            binding.list.adapter = adapter

            adapter.submitList(model.primaryNominees + model.secondaryNominees)

            binding.root.setOnClickListener {
                itemClickListener.invoke(model)
            }
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<EventDetails.Award.AwardCategory.Nominee>() {
        override fun areItemsTheSame(oldItem: EventDetails.Award.AwardCategory.Nominee, newItem: EventDetails.Award.AwardCategory.Nominee): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: EventDetails.Award.AwardCategory.Nominee, newItem: EventDetails.Award.AwardCategory.Nominee): Boolean =
            oldItem == newItem
    }

}