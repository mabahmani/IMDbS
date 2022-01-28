package com.mabahmani.imdb_scraping.ui.main.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mabahmani.domain.vo.common.Name
import com.mabahmani.domain.vo.common.Title
import com.mabahmani.imdb_scraping.databinding.ItemCelebBinding
import com.mabahmani.imdb_scraping.databinding.ItemTitleBinding
import com.mabahmani.imdb_scraping.util.formatNumber
import timber.log.Timber

class TitlesAdapter(private val itemClickListener: (Title) -> Unit) :
    PagingDataAdapter<Title, TitlesAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemTitleBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            itemClickListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(itemView: ItemTitleBinding, private val itemClickListener: (Title) -> Unit) :
        RecyclerView.ViewHolder(itemView.root) {

        private var binding: ItemTitleBinding

        init {
            itemView.executePendingBindings()
            binding = itemView
        }

        fun bind(model: Title?) {

            binding.coverUrl = model?.cover?.getCustomImageWidthUrl(256)
            binding.titleName = model?.title + " " + model?.releaseYear

            var info = ""

            if (!model?.certificate.isNullOrEmpty()){
                info += model?.certificate + " | "
            }

            if (!model?.runtime.isNullOrEmpty()){
                info += model?.runtime + " | "
            }

            if (!model?.genres.isNullOrEmpty()){
                info += model?.genres
            }

            binding.titleInfo = info

            binding.rate = model?.rate

            binding.votes = model?.voteCount?.formatNumber()

            binding.directors = model?.directors?.joinToString { it.name }

            binding.stars = model?.stars?.joinToString { it.name }

            binding.summary = model?.summary
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<Title>() {
        override fun areItemsTheSame(oldItem: Title, newItem: Title): Boolean =
            oldItem.titleId?.value == newItem.titleId?.value

        override fun areContentsTheSame(oldItem: Title, newItem: Title): Boolean =
            oldItem == newItem
    }

}