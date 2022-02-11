package com.mabahmani.imdb_scraping.ui.main.name

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mabahmani.domain.vo.NameDetails
import com.mabahmani.imdb_scraping.databinding.ItemNameDetailFilmographyBinding
import com.mabahmani.imdb_scraping.databinding.ItemNameDetailFilmographyCreditBinding
import com.mabahmani.imdb_scraping.databinding.ItemNameDetailFilmographyCreditEpisodeBinding

class NameDetailsFilmographyAdapter (private val itemClickListener: (NameDetails.Filmography) -> Unit) : ListAdapter<NameDetails.Filmography, NameDetailsFilmographyAdapter.ViewHolder>(
    DiffCallback
)  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemNameDetailFilmographyBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            itemClickListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(itemView: ItemNameDetailFilmographyBinding, private val itemClickListener: (NameDetails.Filmography) -> Unit) :
        RecyclerView.ViewHolder(itemView.root) {

        private var binding: ItemNameDetailFilmographyBinding

        init {
            itemView.executePendingBindings()
            binding = itemView
        }
        fun bind(model: NameDetails.Filmography){

            binding.headTitle = model.title

            val adapter = NameDetailsFilmographyCreditAdapter{

            }

            binding.list.layoutManager = LinearLayoutManager(binding.root.context)
            binding.list.adapter = adapter
            
            adapter.submitList(model.credits)
            
            binding.root.setOnClickListener {
                itemClickListener.invoke(model)
            }
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<NameDetails.Filmography>() {
        override fun areItemsTheSame(oldItem: NameDetails.Filmography, newItem: NameDetails.Filmography): Boolean =
            oldItem.title == newItem.title

        override fun areContentsTheSame(oldItem: NameDetails.Filmography, newItem: NameDetails.Filmography): Boolean =
            oldItem == newItem
    }

}