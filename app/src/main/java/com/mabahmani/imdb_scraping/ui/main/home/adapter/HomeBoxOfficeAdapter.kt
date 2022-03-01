package com.mabahmani.imdb_scraping.ui.main.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mabahmani.domain.vo.common.BoxOffice
import com.mabahmani.imdb_scraping.databinding.ItemHomeBoxOfficeBinding
import java.text.NumberFormat
import java.util.*

class HomeBoxOfficeAdapter(private val itemClickListener: (BoxOffice.BoxOfficeItem) -> Unit) : ListAdapter<BoxOffice.BoxOfficeItem, HomeBoxOfficeAdapter.ViewHolder>(
    DiffCallback
) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            ItemHomeBoxOfficeBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            itemClickListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    class ViewHolder(itemView: ItemHomeBoxOfficeBinding, private val itemClickListener: (BoxOffice.BoxOfficeItem) -> Unit) :
        RecyclerView.ViewHolder(itemView.root) {
            var binding: ItemHomeBoxOfficeBinding
            init {
                itemView.executePendingBindings()
                binding = itemView
            }
            fun bind(model: BoxOffice.BoxOfficeItem){

                binding.positionText = (adapterPosition + 1).toString() + "."
                binding.titleText = model.title
                binding.grossText = "$" + NumberFormat.getNumberInstance(Locale.US).format(model.weekendGross.toInt())

                binding.root.setOnClickListener {
                    itemClickListener.invoke(model)
                }
            }
    }


    object DiffCallback : DiffUtil.ItemCallback<BoxOffice.BoxOfficeItem>() {
        override fun areItemsTheSame(oldItem: BoxOffice.BoxOfficeItem, newItem: BoxOffice.BoxOfficeItem): Boolean =
            oldItem.title == newItem.title

        override fun areContentsTheSame(oldItem: BoxOffice.BoxOfficeItem, newItem: BoxOffice.BoxOfficeItem): Boolean =
            oldItem == newItem
    }
}

