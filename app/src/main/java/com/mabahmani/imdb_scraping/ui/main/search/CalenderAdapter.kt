package com.mabahmani.imdb_scraping.ui.main.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mabahmani.domain.vo.Calender
import com.mabahmani.domain.vo.common.Genre
import com.mabahmani.imdb_scraping.databinding.ItemCalenderBinding
import com.mabahmani.imdb_scraping.databinding.ItemGenreBinding

class CalenderAdapter (private val itemClickListener: (Calender) -> Unit) : ListAdapter<Calender, CalenderAdapter.ViewHolder>(
    DiffCallback
)  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCalenderBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            itemClickListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(itemView: ItemCalenderBinding, private val itemClickListener: (Calender) -> Unit) :
        RecyclerView.ViewHolder(itemView.root) {

        private var binding: ItemCalenderBinding

        init {
            itemView.executePendingBindings()
            binding = itemView
        }
        fun bind(model: Calender){
            binding.date = model.date

            val adapter = CalenderTitleAdapter{

            }

            binding.list.layoutManager = LinearLayoutManager(binding.root.context)
            binding.list.adapter = adapter

            adapter.submitList(model.titles)
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<Calender>() {
        override fun areItemsTheSame(oldItem: Calender, newItem: Calender): Boolean =
            oldItem.date == newItem.date

        override fun areContentsTheSame(oldItem: Calender, newItem: Calender): Boolean =
            oldItem == newItem
    }

}