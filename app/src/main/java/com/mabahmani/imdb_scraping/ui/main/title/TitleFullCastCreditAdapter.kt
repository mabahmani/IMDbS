package com.mabahmani.imdb_scraping.ui.main.title

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mabahmani.domain.vo.NameAwards
import com.mabahmani.domain.vo.TitleAwards
import com.mabahmani.domain.vo.common.Cast
import com.mabahmani.imdb_scraping.databinding.*

class TitleFullCastCreditAdapter (private val itemClickListener: (Cast) -> Unit) : ListAdapter<Cast, TitleFullCastCreditAdapter.ViewHolder>(
    DiffCallback
)  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemTitleFullcastCreditBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            itemClickListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(itemView: ItemTitleFullcastCreditBinding, private val itemClickListener: (Cast) -> Unit) :
        RecyclerView.ViewHolder(itemView.root) {

        private var binding: ItemTitleFullcastCreditBinding

        init {
            itemView.executePendingBindings()
            binding = itemView
        }
        fun bind(model: Cast){

            binding.title = model.name
            binding.subtitle = model.description
            binding.imageUrl = model.avatar.getCustomImageWidthUrl(256)

            binding.root.setOnClickListener {
                itemClickListener.invoke(model)
            }
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<Cast>() {
        override fun areItemsTheSame(oldItem: Cast, newItem: Cast): Boolean =
            oldItem.nameId.value == newItem.nameId.value

        override fun areContentsTheSame(oldItem: Cast, newItem: Cast): Boolean =
            oldItem == newItem
    }

}