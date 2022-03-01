package com.mabahmani.imdb_scraping.ui.main.name.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mabahmani.domain.vo.NameDetails
import com.mabahmani.domain.vo.common.Either
import com.mabahmani.imdb_scraping.R
import com.mabahmani.imdb_scraping.databinding.ItemNameDetailFilmographyCreditBinding
import com.mabahmani.imdb_scraping.util.toast

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
                when (it.titleId.validate()) {
                    is Either.Right -> {
                        Navigation.findNavController(binding.root).navigate(
                            R.id.titleDetailsFragment,
                            Bundle().apply {
                                putString("titleId", it.titleId.value)
                                putString("title", it.title)
                            }
                        )
                    }

                    else -> {
                        binding.root.context.toast(binding.root.context.getString(R.string.invalid_title_id))
                    }
                }
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