package com.mabahmani.imdb_scraping.ui.main.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mabahmani.domain.vo.common.Event
import com.mabahmani.domain.vo.common.Name
import com.mabahmani.domain.vo.common.Title
import com.mabahmani.imdb_scraping.databinding.ItemCelebBinding
import com.mabahmani.imdb_scraping.databinding.ItemEventBinding
import com.mabahmani.imdb_scraping.databinding.ItemKeywordBinding
import com.mabahmani.imdb_scraping.databinding.ItemTitleBinding
import com.mabahmani.imdb_scraping.util.formatNumber
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView
import timber.log.Timber

class EventsAdapter(private val itemClickListener: (Event) -> Unit) :
    ListAdapter<Event, EventsAdapter.ViewHolder>(DiffCallback), FastScrollRecyclerView.SectionedAdapter {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            itemClickListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(itemView: ItemEventBinding, private val itemClickListener: (Event) -> Unit) :
        RecyclerView.ViewHolder(itemView.root) {

        private var binding: ItemEventBinding

        init {
            itemView.executePendingBindings()
            binding = itemView
        }

        fun bind(model: Event) {

            binding.event = model.name

        }
    }

    object DiffCallback : DiffUtil.ItemCallback<Event>() {
        override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean =
            oldItem.eventId.value == newItem.eventId.value

        override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean =
            oldItem == newItem
    }

    override fun getSectionName(position: Int): String {
        return getItem(position).name[0].toString()
    }

}