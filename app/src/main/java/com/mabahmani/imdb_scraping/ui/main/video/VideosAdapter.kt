package com.mabahmani.imdb_scraping.ui.main.video

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mabahmani.domain.vo.common.Video
import com.mabahmani.imdb_scraping.databinding.ItemVideoBinding

class VideosAdapter(private val itemClickListener: (Video?) -> Unit) :
    PagingDataAdapter<Video, VideosAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            itemClickListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(itemView: ItemVideoBinding, private val itemClickListener: (Video?) -> Unit) :
        RecyclerView.ViewHolder(itemView.root) {

        private var binding: ItemVideoBinding

        init {
            itemView.executePendingBindings()
            binding = itemView
        }

        fun bind(model: Video?) {
            binding.imageUrl = model?.preview?.getCustomImageWidthUrl(512)
            binding.title = model?.title

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