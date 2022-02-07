package com.mabahmani.imdb_scraping.ui.main.event

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mabahmani.domain.vo.EventDetails
import com.mabahmani.imdb_scraping.databinding.ItemEventAwardBinding
import com.mabahmani.imdb_scraping.databinding.ItemEventAwardCategoryBinding
import com.mabahmani.imdb_scraping.databinding.ItemEventNomineeBinding
import com.mabahmani.imdb_scraping.databinding.ItemEventSubNomineeBinding

class EventAwardAdapter (private val itemClickListener: (EventDetails.Award) -> Unit) : ListAdapter<EventDetails.Award, EventAwardAdapter.ViewHolder>(
    DiffCallback
)  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemEventAwardBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            itemClickListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(itemView: ItemEventAwardBinding, private val itemClickListener: (EventDetails.Award) -> Unit) :
        RecyclerView.ViewHolder(itemView.root) {

        private var binding: ItemEventAwardBinding

        init {
            itemView.executePendingBindings()
            binding = itemView
        }
        fun bind(model: EventDetails.Award){

            binding.award = model.awardName


            val adapter = EventAwardCategoryAdapter{

            }
            binding.list.layoutManager = LinearLayoutManager(binding.root.context)
            binding.list.adapter = adapter

            adapter.submitList(model.awardCategories)

            binding.root.setOnClickListener {
                itemClickListener.invoke(model)
            }
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<EventDetails.Award>() {
        override fun areItemsTheSame(oldItem: EventDetails.Award, newItem: EventDetails.Award): Boolean =
            oldItem.awardName == newItem.awardName

        override fun areContentsTheSame(oldItem: EventDetails.Award, newItem: EventDetails.Award): Boolean =
            oldItem == newItem
    }

}