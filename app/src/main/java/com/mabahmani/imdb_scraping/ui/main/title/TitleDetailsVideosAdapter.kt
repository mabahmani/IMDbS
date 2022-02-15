package com.mabahmani.imdb_scraping.ui.main.title

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mabahmani.domain.vo.common.Video
import com.mabahmani.imdb_scraping.databinding.ItemTitleDetailsGenreBinding
import com.mabahmani.imdb_scraping.databinding.ItemTitleDetailsVideoBinding

class TitleDetailsVideosAdapter (private val itemClickListener: (Video) -> Unit) : ListAdapter<Video, TitleDetailsVideosAdapter.ViewHolder>(
    DiffCallback
)  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemTitleDetailsVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            itemClickListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(itemView: ItemTitleDetailsVideoBinding, private val itemClickListener: (Video) -> Unit) :
        RecyclerView.ViewHolder(itemView.root) {

        private var binding: ItemTitleDetailsVideoBinding

        init {
            itemView.executePendingBindings()
            binding = itemView
        }
        fun bind(model: Video){
            binding.titleText = model.title
            binding.durationText = model.runtime
            binding.imageUrl = model.preview.getCustomImageWidthUrl(512)

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