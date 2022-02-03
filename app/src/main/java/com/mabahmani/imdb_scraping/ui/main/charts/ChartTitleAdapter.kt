package com.mabahmani.imdb_scraping.ui.main.charts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mabahmani.domain.vo.common.Title
import com.mabahmani.imdb_scraping.databinding.ItemChartBinding
import com.mabahmani.imdb_scraping.util.formatTwoDecimalNumber

class ChartTitleAdapter (private val itemClickListener: (Title) -> Unit) : ListAdapter<Title, ChartTitleAdapter.ViewHolder>(
    DiffCallback
)  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemChartBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            itemClickListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(itemView: ItemChartBinding, private val itemClickListener: (Title) -> Unit) :
        RecyclerView.ViewHolder(itemView.root) {

        private var binding: ItemChartBinding

        init {
            itemView.executePendingBindings()
            binding = itemView
        }
        fun bind(model: Title){
            binding.imageUrl = model.cover?.getCustomImageWidthUrl(256)
            binding.position = (bindingAdapterPosition + 1).toString() + "."
            binding.title = model.title
            binding.year = model.releaseYear
            binding.rate = model.rate?.formatTwoDecimalNumber()

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