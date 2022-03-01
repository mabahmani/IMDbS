package com.mabahmani.imdb_scraping.ui.main.name.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mabahmani.domain.vo.NameDetails
import com.mabahmani.imdb_scraping.databinding.ItemNameDetailFilmographyCreditEpisodeBinding

class NameDetailsFilmographyCreditEpisodeAdapter (private val itemClickListener: (NameDetails.Filmography.Credit.Episode) -> Unit) : ListAdapter<NameDetails.Filmography.Credit.Episode, NameDetailsFilmographyCreditEpisodeAdapter.ViewHolder>(
    DiffCallback
)  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemNameDetailFilmographyCreditEpisodeBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            itemClickListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(itemView: ItemNameDetailFilmographyCreditEpisodeBinding, private val itemClickListener: (NameDetails.Filmography.Credit.Episode) -> Unit) :
        RecyclerView.ViewHolder(itemView.root) {

        private var binding: ItemNameDetailFilmographyCreditEpisodeBinding

        init {
            itemView.executePendingBindings()
            binding = itemView
        }
        fun bind(model: NameDetails.Filmography.Credit.Episode){

            binding.title = model.title
            binding.subtitle = model.subtitle

            binding.root.setOnClickListener {
                itemClickListener.invoke(model)
            }
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<NameDetails.Filmography.Credit.Episode>() {
        override fun areItemsTheSame(oldItem: NameDetails.Filmography.Credit.Episode, newItem: NameDetails.Filmography.Credit.Episode): Boolean =
            oldItem.titleId.value == newItem.titleId.value

        override fun areContentsTheSame(oldItem: NameDetails.Filmography.Credit.Episode, newItem: NameDetails.Filmography.Credit.Episode): Boolean =
            oldItem == newItem
    }

}