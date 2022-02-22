package com.mabahmani.imdb_scraping.ui.main.event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mabahmani.domain.vo.EventDetails
import com.mabahmani.domain.vo.common.Id
import com.mabahmani.imdb_scraping.R
import com.mabahmani.imdb_scraping.databinding.ItemEventAwardCategoryBinding
import com.mabahmani.imdb_scraping.databinding.ItemEventNomineeBinding
import com.mabahmani.imdb_scraping.databinding.ItemEventSubNomineeBinding

class EventAwardCategoryAdapter: ListAdapter<EventDetails.Award.AwardCategory, EventAwardCategoryAdapter.ViewHolder>(
    DiffCallback
)  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemEventAwardCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(itemView: ItemEventAwardCategoryBinding) :
        RecyclerView.ViewHolder(itemView.root) {

        private var binding: ItemEventAwardCategoryBinding

        init {
            itemView.executePendingBindings()
            binding = itemView
        }
        fun bind(model: EventDetails.Award.AwardCategory){

            binding.awardCategory = model.awardCategoryTitle


            val adapter = EventNomineeAdapter{
                when(it.evalId()){
                    is Id.TitleId ->{
                        Navigation.findNavController(binding.root).navigate(
                            R.id.titleDetailsFragment,
                            Bundle().apply {
                                putString("titleId", it.id)
                                putString("title", it.name)
                            }
                        )
                    }
                    is Id.NameId ->{
                        Navigation.findNavController(binding.root).navigate(
                            R.id.nameDetailsFragment,
                            Bundle().apply {
                                putString("nameId", it.id)
                                putString("name", it.name)
                            }
                        )
                    }
                }
            }
            binding.list.layoutManager = LinearLayoutManager(binding.root.context)
            binding.list.adapter = adapter

            adapter.submitList(model.nominees)

        }
    }

    object DiffCallback : DiffUtil.ItemCallback<EventDetails.Award.AwardCategory>() {
        override fun areItemsTheSame(oldItem: EventDetails.Award.AwardCategory, newItem: EventDetails.Award.AwardCategory): Boolean =
            oldItem.awardCategoryTitle == newItem.awardCategoryTitle

        override fun areContentsTheSame(oldItem: EventDetails.Award.AwardCategory, newItem: EventDetails.Award.AwardCategory): Boolean =
            oldItem == newItem
    }

}