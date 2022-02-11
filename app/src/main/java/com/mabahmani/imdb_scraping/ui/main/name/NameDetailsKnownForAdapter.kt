package com.mabahmani.imdb_scraping.ui.main.name

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mabahmani.domain.vo.common.Genre
import com.mabahmani.domain.vo.common.Title
import com.mabahmani.imdb_scraping.databinding.ItemGenreBinding
import com.mabahmani.imdb_scraping.databinding.ItemNameDetailKnownForBinding
import com.mabahmani.imdb_scraping.databinding.ItemNameDetailPhotoBinding

class NameDetailsKnownForAdapter (private val itemClickListener: (Title) -> Unit) : ListAdapter<Title, NameDetailsKnownForAdapter.ViewHolder>(
    DiffCallback
)  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemNameDetailKnownForBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            itemClickListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(itemView: ItemNameDetailKnownForBinding, private val itemClickListener: (Title) -> Unit) :
        RecyclerView.ViewHolder(itemView.root) {

        private var binding: ItemNameDetailKnownForBinding

        init {
            itemView.executePendingBindings()
            binding = itemView
        }
        fun bind(model: Title){

            binding.imageUrl = model.cover?.getCustomImageWidthUrl(512)
            binding.titleName = model.title
            binding.roleName = model.summary
            binding.year = model.releaseYear

            binding.root.setOnClickListener {
                itemClickListener.invoke(model)
            }
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<Title>() {
        override fun areItemsTheSame(oldItem: Title, newItem: Title): Boolean =
            oldItem.titleId?.value == newItem.titleId?.value

        override fun areContentsTheSame(oldItem: Title, newItem: Title): Boolean =
            oldItem == newItem
    }

}