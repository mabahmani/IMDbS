package com.mabahmani.imdb_scraping.ui.main.trailers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mabahmani.domain.vo.common.Title
import com.mabahmani.domain.vo.common.Trailer
import com.mabahmani.imdb_scraping.databinding.ItemChartBinding
import com.mabahmani.imdb_scraping.databinding.ItemTrailerBinding
import com.mabahmani.imdb_scraping.util.formatTwoDecimalNumber

class TrailersAdapter (private val itemClickListener: (Trailer, Boolean) -> Unit) : ListAdapter<Trailer, TrailersAdapter.ViewHolder>(
    DiffCallback
)  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemTrailerBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            itemClickListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(itemView: ItemTrailerBinding, private val itemClickListener: (Trailer, Boolean) -> Unit) :
        RecyclerView.ViewHolder(itemView.root) {

        private var binding: ItemTrailerBinding

        init {
            itemView.executePendingBindings()
            binding = itemView
        }
        fun bind(model: Trailer){
            binding.imageUrl = model.titleCover.getCustomImageHeightUrl(512)
            binding.runtime = model.duration
            binding.title = model.title
            binding.subtitle = model.caption

            binding.coverParent.setOnClickListener {
                itemClickListener.invoke(model, true)
            }

            binding.titleParent.setOnClickListener {
                itemClickListener.invoke(model, false)
            }
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<Trailer>() {
        override fun areItemsTheSame(oldItem: Trailer, newItem: Trailer): Boolean =
            oldItem.videoId.value == newItem.videoId.value

        override fun areContentsTheSame(oldItem: Trailer, newItem: Trailer): Boolean =
            oldItem == newItem
    }

}