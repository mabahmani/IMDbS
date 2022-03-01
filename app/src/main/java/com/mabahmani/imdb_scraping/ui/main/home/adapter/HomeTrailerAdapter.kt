package com.mabahmani.imdb_scraping.ui.main.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mabahmani.domain.vo.common.Trailer
import com.mabahmani.imdb_scraping.databinding.ItemHomeTrailerBinding

class HomeTrailerAdapter(private val itemClickListener: (Trailer) -> Unit) : ListAdapter<Trailer, HomeTrailerAdapter.ViewHolder>(
    DiffCallback
) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            ItemHomeTrailerBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            itemClickListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    class ViewHolder(itemView: ItemHomeTrailerBinding, private val itemClickListener: (Trailer) -> Unit) :
        RecyclerView.ViewHolder(itemView.root) {
            var binding: ItemHomeTrailerBinding
            init {
                itemView.executePendingBindings()
                binding = itemView
            }
            fun bind(model: Trailer){
                binding.title = model.title
                binding.caption = model.caption
                binding.duration = model.duration
                binding.trailerPreview = model.videoPreview.getCustomImageHeightUrl(1024)
                binding.coverImage = model.titleCover.getCustomImageWidthUrl(256)

                binding.root.setOnClickListener {
                    itemClickListener.invoke(model)
                }
            }

    }


    object DiffCallback : DiffUtil.ItemCallback<Trailer>() {
        override fun areItemsTheSame(oldItem: Trailer, newItem: Trailer): Boolean =
            oldItem.title == newItem.title

        override fun areContentsTheSame(oldItem: Trailer, newItem: Trailer): Boolean =
            oldItem == newItem
    }
}

