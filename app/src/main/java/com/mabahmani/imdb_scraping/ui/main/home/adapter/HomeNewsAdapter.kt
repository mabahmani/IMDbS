package com.mabahmani.imdb_scraping.ui.main.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mabahmani.domain.vo.common.News
import com.mabahmani.imdb_scraping.databinding.ItemHomeNewsBinding

class HomeNewsAdapter(private val itemClickListener: (News) -> Unit) : ListAdapter<News, HomeNewsAdapter.ViewHolder>(
    DiffCallback
) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            ItemHomeNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            itemClickListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    class ViewHolder(itemView: ItemHomeNewsBinding, private val itemClickListener: (News) -> Unit) :
        RecyclerView.ViewHolder(itemView.root) {
            var binding: ItemHomeNewsBinding
            init {
                itemView.executePendingBindings()
                binding = itemView
            }
            fun bind(model: News){

                binding.title = model.title
                binding.subtitle = String.format("%s - %s", model.date, model.source)
                binding.imageUrl = model.image.getCustomImageWidthUrl(512)

                binding.root.setOnClickListener {
                    itemClickListener.invoke(model)
                }
            }

    }


    object DiffCallback : DiffUtil.ItemCallback<News>() {
        override fun areItemsTheSame(oldItem: News, newItem: News): Boolean =
            oldItem.newsId.value == newItem.newsId.value

        override fun areContentsTheSame(oldItem: News, newItem: News): Boolean =
            oldItem == newItem
    }
}

