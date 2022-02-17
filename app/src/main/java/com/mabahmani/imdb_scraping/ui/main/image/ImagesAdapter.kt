package com.mabahmani.imdb_scraping.ui.main.image

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mabahmani.domain.vo.common.ImageLink
import com.mabahmani.domain.vo.common.Name
import com.mabahmani.imdb_scraping.databinding.ItemCelebBinding
import com.mabahmani.imdb_scraping.databinding.ItemImageBinding

class ImagesAdapter(private val itemClickListener: (ImageLink?) -> Unit) :
    PagingDataAdapter<ImageLink, ImagesAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            itemClickListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(itemView: ItemImageBinding, private val itemClickListener: (ImageLink?) -> Unit) :
        RecyclerView.ViewHolder(itemView.root) {

        private var binding: ItemImageBinding

        init {
            itemView.executePendingBindings()
            binding = itemView
        }

        fun bind(model: ImageLink?) {
            binding.imageUrl = model?.image?.getCustomImageWidthUrl(512)

            binding.root.setOnClickListener {
                itemClickListener.invoke(model)
            }
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<ImageLink>() {
        override fun areItemsTheSame(oldItem: ImageLink, newItem: ImageLink): Boolean =
            oldItem.imageId?.value == newItem.imageId?.value

        override fun areContentsTheSame(oldItem: ImageLink, newItem: ImageLink): Boolean =
            oldItem == newItem
    }

}