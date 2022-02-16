package com.mabahmani.imdb_scraping.ui.main.title

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mabahmani.domain.vo.NameAwards
import com.mabahmani.domain.vo.TitleAwards
import com.mabahmani.domain.vo.TitleFullCasts
import com.mabahmani.imdb_scraping.databinding.*

class TitleFullCastAdapter (private val itemClickListener: (TitleFullCasts.CastItem) -> Unit) : ListAdapter<TitleFullCasts.CastItem, TitleFullCastAdapter.ViewHolder>(
    DiffCallback
)  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemTitleFullcastBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            itemClickListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(itemView: ItemTitleFullcastBinding, private val itemClickListener: (TitleFullCasts.CastItem) -> Unit) :
        RecyclerView.ViewHolder(itemView.root) {

        private var binding: ItemTitleFullcastBinding

        init {
            itemView.executePendingBindings()
            binding = itemView
        }
        fun bind(model: TitleFullCasts.CastItem){

            binding.titleWidget.setTitle(model.title)

            val adapter = TitleFullCastCreditAdapter{

            }

            binding.list.layoutManager = LinearLayoutManager(binding.root.context)
            binding.list.adapter = adapter

            adapter.submitList(model.casts)

            binding.root.setOnClickListener {
                itemClickListener.invoke(model)
            }
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<TitleFullCasts.CastItem>() {
        override fun areItemsTheSame(oldItem: TitleFullCasts.CastItem, newItem: TitleFullCasts.CastItem): Boolean =
            oldItem.title == newItem.title

        override fun areContentsTheSame(oldItem: TitleFullCasts.CastItem, newItem: TitleFullCasts.CastItem): Boolean =
            oldItem == newItem
    }

}