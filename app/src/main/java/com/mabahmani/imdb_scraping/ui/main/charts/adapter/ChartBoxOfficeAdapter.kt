package com.mabahmani.imdb_scraping.ui.main.charts.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mabahmani.domain.vo.common.BoxOffice
import com.mabahmani.imdb_scraping.databinding.ItemChartBoxOfficeBinding

class ChartBoxOfficeAdapter (private val itemClickListener: (BoxOffice.BoxOfficeItem) -> Unit) : ListAdapter<BoxOffice.BoxOfficeItem, ChartBoxOfficeAdapter.ViewHolder>(
    DiffCallback
)  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemChartBoxOfficeBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            itemClickListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(itemView: ItemChartBoxOfficeBinding, private val itemClickListener: (BoxOffice.BoxOfficeItem) -> Unit) :
        RecyclerView.ViewHolder(itemView.root) {

        private var binding: ItemChartBoxOfficeBinding

        init {
            itemView.executePendingBindings()
            binding = itemView
        }
        fun bind(model: BoxOffice.BoxOfficeItem){
            binding.imageUrl = model.image?.getCustomImageWidthUrl(256)
            binding.title = model.title
            binding.weekend = model.weekendGross
            binding.groos = model.gross
            binding.weeks = model.weeks

            binding.root.setOnClickListener {
                itemClickListener.invoke(model)
            }
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<BoxOffice.BoxOfficeItem>() {
        override fun areItemsTheSame(oldItem: BoxOffice.BoxOfficeItem, newItem: BoxOffice.BoxOfficeItem): Boolean =
            oldItem.titleId.value == newItem.titleId.value

        override fun areContentsTheSame(oldItem: BoxOffice.BoxOfficeItem, newItem: BoxOffice.BoxOfficeItem): Boolean =
            oldItem == newItem
    }

}