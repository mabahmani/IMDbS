package com.mabahmani.imdb_scraping.ui.main.name

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
import com.mabahmani.imdb_scraping.databinding.ItemNameDetailFilmographyBinding
import com.mabahmani.imdb_scraping.databinding.ItemNameDetailFilmographyCreditBinding
import com.mabahmani.imdb_scraping.databinding.ItemNameDetailFilmographyCreditEpisodeBinding
import com.mabahmani.imdb_scraping.util.toast

class NameDetailsFilmographyAdapter : ListAdapter<NameDetails.Filmography, NameDetailsFilmographyAdapter.ViewHolder>(
    DiffCallback
)  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemNameDetailFilmographyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(itemView: ItemNameDetailFilmographyBinding) :
        RecyclerView.ViewHolder(itemView.root) {

        private var binding: ItemNameDetailFilmographyBinding

        init {
            itemView.executePendingBindings()
            binding = itemView
        }
        fun bind(model: NameDetails.Filmography){

            binding.headTitle = model.title

            val adapter = NameDetailsFilmographyCreditAdapter{
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
            
            adapter.submitList(model.credits)
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<NameDetails.Filmography>() {
        override fun areItemsTheSame(oldItem: NameDetails.Filmography, newItem: NameDetails.Filmography): Boolean =
            oldItem.title == newItem.title

        override fun areContentsTheSame(oldItem: NameDetails.Filmography, newItem: NameDetails.Filmography): Boolean =
            oldItem == newItem
    }

}