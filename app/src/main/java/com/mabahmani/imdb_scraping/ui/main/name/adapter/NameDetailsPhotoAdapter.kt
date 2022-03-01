package com.mabahmani.imdb_scraping.ui.main.name.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mabahmani.domain.vo.common.ImageLink
import com.mabahmani.imdb_scraping.databinding.ItemNameDetailPhotoBinding

class NameDetailsPhotoAdapter (private val itemClickListener: (ImageLink) -> Unit) : ListAdapter<ImageLink, NameDetailsPhotoAdapter.ViewHolder>(
    DiffCallback
)  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemNameDetailPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            itemClickListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(itemView: ItemNameDetailPhotoBinding, private val itemClickListener: (ImageLink) -> Unit) :
        RecyclerView.ViewHolder(itemView.root) {

        private var binding: ItemNameDetailPhotoBinding

        init {
            itemView.executePendingBindings()
            binding = itemView
        }
        fun bind(model: ImageLink){

            binding.imageUrl = model.image.getCustomImageWidthUrl(320)

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