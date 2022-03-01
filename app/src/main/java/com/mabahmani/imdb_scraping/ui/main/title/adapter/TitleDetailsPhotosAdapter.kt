package com.mabahmani.imdb_scraping.ui.main.title.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mabahmani.domain.vo.common.ImageLink
import com.mabahmani.imdb_scraping.databinding.ItemTitleDetailsPhotoBinding

class TitleDetailsPhotosAdapter (private val itemClickListener: (ImageLink) -> Unit) : ListAdapter<ImageLink, TitleDetailsPhotosAdapter.ViewHolder>(
    DiffCallback
)  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemTitleDetailsPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            itemClickListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(itemView: ItemTitleDetailsPhotoBinding, private val itemClickListener: (ImageLink) -> Unit) :
        RecyclerView.ViewHolder(itemView.root) {

        private var binding: ItemTitleDetailsPhotoBinding

        init {
            itemView.executePendingBindings()
            binding = itemView
        }
        fun bind(model: ImageLink){
            binding.imageUrl = model.image.getCustomImageWidthUrl(800)

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