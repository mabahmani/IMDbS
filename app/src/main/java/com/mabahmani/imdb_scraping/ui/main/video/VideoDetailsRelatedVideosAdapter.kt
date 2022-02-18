package com.mabahmani.imdb_scraping.ui.main.video

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mabahmani.domain.vo.Home
import com.mabahmani.domain.vo.common.Video
import com.mabahmani.domain.vo.enum.HomeMediaType
import com.mabahmani.imdb_scraping.R
import com.mabahmani.imdb_scraping.databinding.ItemHomeMediaBinding
import com.mabahmani.imdb_scraping.databinding.ItemVideoDetailsRelatedVideosBinding

class VideoDetailsRelatedVideosAdapter(private val itemClickListener: (Video) -> Unit) : ListAdapter<Video, VideoDetailsRelatedVideosAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            ItemVideoDetailsRelatedVideosBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            itemClickListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    class ViewHolder(itemView: ItemVideoDetailsRelatedVideosBinding, private val itemClickListener: (Video) -> Unit) :
        RecyclerView.ViewHolder(itemView.root) {
            var binding: ItemVideoDetailsRelatedVideosBinding
            init {
                itemView.executePendingBindings()
                binding = itemView
            }
            fun bind(model: Video){

                binding.imageUrl = model.preview.getCustomImageWidthUrl(512)
                binding.titleText = model.title
                binding.iconDescriptionText = model.runtime
                binding.subtitleText = model.subtitle

                binding.root.setOnClickListener {
                    itemClickListener.invoke(model)
                }
            }

    }


    object DiffCallback : DiffUtil.ItemCallback<Video>() {
        override fun areItemsTheSame(oldItem: Video, newItem: Video): Boolean =
            oldItem.title == newItem.title

        override fun areContentsTheSame(oldItem: Video, newItem: Video): Boolean =
            oldItem == newItem
    }
}

