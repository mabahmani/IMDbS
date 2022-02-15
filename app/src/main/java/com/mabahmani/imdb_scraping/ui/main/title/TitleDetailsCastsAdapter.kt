package com.mabahmani.imdb_scraping.ui.main.title

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mabahmani.domain.vo.common.Cast
import com.mabahmani.imdb_scraping.databinding.ItemTitleDetailsCastBinding
import com.mabahmani.imdb_scraping.databinding.ItemTitleDetailsGenreBinding
import com.mabahmani.imdb_scraping.databinding.ItemTitleDetailsVideoBinding

class TitleDetailsCastsAdapter (private val itemClickListener: (Cast) -> Unit) : ListAdapter<Cast, TitleDetailsCastsAdapter.ViewHolder>(
    DiffCallback
)  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemTitleDetailsCastBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            itemClickListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(itemView: ItemTitleDetailsCastBinding, private val itemClickListener: (Cast) -> Unit) :
        RecyclerView.ViewHolder(itemView.root) {

        private var binding: ItemTitleDetailsCastBinding

        init {
            itemView.executePendingBindings()
            binding = itemView
        }
        fun bind(model: Cast){
            binding.avatarUrl = model.avatar.getCustomImageWidthUrl(256)
            binding.movieName = model.description
            binding.realName = model.name

            binding.root.setOnClickListener {
                itemClickListener.invoke(model)
            }
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<Cast>() {
        override fun areItemsTheSame(oldItem: Cast, newItem: Cast): Boolean =
            oldItem.nameId.value == newItem.nameId.value

        override fun areContentsTheSame(oldItem: Cast, newItem: Cast): Boolean =
            oldItem == newItem
    }

}