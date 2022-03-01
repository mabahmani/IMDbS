package com.mabahmani.imdb_scraping.ui.main.search.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mabahmani.domain.vo.Calender
import com.mabahmani.domain.vo.common.Either
import com.mabahmani.imdb_scraping.R
import com.mabahmani.imdb_scraping.databinding.ItemCalenderBinding
import com.mabahmani.imdb_scraping.util.toast

class CalenderAdapter : ListAdapter<Calender, CalenderAdapter.ViewHolder>(
    DiffCallback
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCalenderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(itemView: ItemCalenderBinding) :
        RecyclerView.ViewHolder(itemView.root) {

        private var binding: ItemCalenderBinding

        init {
            itemView.executePendingBindings()
            binding = itemView
        }

        fun bind(model: Calender) {
            binding.date = model.date

            val adapter = CalenderTitleAdapter {
                when (it.titleId.validate()) {
                    is Either.Right -> {
                        Navigation.findNavController(binding.root).navigate(
                            R.id.titleDetailsFragment,
                            Bundle().apply {
                                putString("titleId", it.titleId.value)
                                putString("title", it.title)
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