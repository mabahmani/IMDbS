package com.mabahmani.imdb_scraping.ui.main.name

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mabahmani.domain.vo.NameDetails
import com.mabahmani.imdb_scraping.databinding.ItemNameDetailFilmographyCreditBinding
import com.mabahmani.imdb_scraping.databinding.ItemNameDetailFilmographyCreditEpisodeBinding

class NameDetailsFilmographyCreditAdapter (private val itemClickListener: (NameDetails.Filmography.Credit) -> Unit) : ListAdapter<NameDetails.Filmography.Credit, NameDetailsFilmographyCreditAdapter.ViewHolder>(
    DiffCallback
)  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemNameDetailFilmographyCreditBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            itemClickListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(itemView: ItemNameDetailFilmographyCreditBinding, private val itemClickListener: (NameDetails.Filmography.Credit) -> Unit) :
        RecyclerView.ViewHolder(itemView.root) {

        private var binding: ItemNameDetailFilmographyCreditBinding

        init {
            itemView.executePendingBindings()
            binding = itemView
        }
        fun bind(model: NameDetails.Filmography.Credit){

            binding.title = model.title
            binding.subtitle = model.subtitle
            binding.year = model.year

            val adapter = NameDetailsFilmographyCreditEpisodeAdapter{

            }

            binding.list.layoutManager = LinearLayoutManager(binding.root.context)
            binding.list.adapter = adapter

            adapter.submitList(model.episodes)
            
            binding.root.setOnClickListener {
                itemClickListener.invoke(model)
            }
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<NameDetails.Filmography.Credit>() {
        override fun areItemsTheSame(oldItem: NameDetails.Filmography.Credit, newItem: NameDetails.Filmography.Credit): Boolean =
            oldItem.titleId.value == newItem.titleId.value

        override fun areContentsTheSame(oldItem: NameDetails.Filmography.Credit, newItem: NameDetails.Filmography.Credit): Boolean =
            oldItem == newItem
    }

}