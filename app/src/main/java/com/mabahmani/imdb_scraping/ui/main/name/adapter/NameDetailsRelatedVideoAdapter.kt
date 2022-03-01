package com.mabahmani.imdb_scraping.ui.main.name.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mabahmani.domain.vo.common.Video
import com.mabahmani.imdb_scraping.databinding.ItemNameDetailRelatedVideoBinding

class NameDetailsRelatedVideoAdapter (private val itemClickListener: (Video) -> Unit) : ListAdapter<Video, NameDetailsRelatedVideoAdapter.ViewHolder>(
    DiffCallback
)  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemNameDetailRelatedVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            itemClickListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(itemView: ItemNameDetailRelatedVideoBinding, private val itemClickListener: (Video) -> Unit) :
        RecyclerView.ViewHolder(itemView.root) {

        private var binding: ItemNameDetailRelatedVideoBinding

        init {
            itemView.executePendingBindings()
            binding = itemView
        }
        fun bind(model: Video){

            binding.imageUrl = model.preview.getOriginalImageSizeUrl()
            binding.title = model.title

            binding.root.setOnClickListener {
                itemClickListener.invoke(model)
            }
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<Video>() {
        override fun areItemsTheSame(oldItem: Video, newItem: Video): Boolean =
            oldItem.videoId.value == newItem.videoId.value

        override fun areContentsTheSame(oldItem: Video, newItem: Video): Boolean =
            oldItem == newItem
    }

}