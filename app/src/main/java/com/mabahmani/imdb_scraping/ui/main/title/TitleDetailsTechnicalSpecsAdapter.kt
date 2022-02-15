package com.mabahmani.imdb_scraping.ui.main.title

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mabahmani.domain.vo.common.Text
import com.mabahmani.imdb_scraping.databinding.ItemTitleDetailsGenreBinding
import com.mabahmani.imdb_scraping.databinding.ItemTitleDetailsTechnicalSpecsBinding

class TitleDetailsTechnicalSpecsAdapter : ListAdapter<Text, TitleDetailsTechnicalSpecsAdapter.ViewHolder>(
    DiffCallback
)  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemTitleDetailsTechnicalSpecsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(itemView: ItemTitleDetailsTechnicalSpecsBinding) :
        RecyclerView.ViewHolder(itemView.root) {

        private var binding: ItemTitleDetailsTechnicalSpecsBinding

        init {
            itemView.executePendingBindings()
            binding = itemView
        }
        fun bind(model: Text){
            binding.title = model.title
            binding.subtitle = model.subtitle

        }
    }

    object DiffCallback : DiffUtil.ItemCallback<Text>() {
        override fun areItemsTheSame(oldItem: Text, newItem: Text): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: Text, newItem: Text): Boolean =
            oldItem == newItem
    }

}