package com.mabahmani.imdb_scraping.ui.main.title

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mabahmani.domain.vo.NameAwards
import com.mabahmani.domain.vo.TitleAwards
import com.mabahmani.domain.vo.TitleFullCasts
import com.mabahmani.domain.vo.common.Either
import com.mabahmani.imdb_scraping.R
import com.mabahmani.imdb_scraping.databinding.*
import com.mabahmani.imdb_scraping.util.toast

class TitleFullCastAdapter : ListAdapter<TitleFullCasts.CastItem, TitleFullCastAdapter.ViewHolder>(
    DiffCallback
)  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemTitleFullcastBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(itemView: ItemTitleFullcastBinding) :
        RecyclerView.ViewHolder(itemView.root) {

        private var binding: ItemTitleFullcastBinding

        init {
            itemView.executePendingBindings()
            binding = itemView
        }
        fun bind(model: TitleFullCasts.CastItem){

            binding.titleWidget.setTitle(model.title)

            val adapter = TitleFullCastCreditAdapter{
                when (it.nameId.validate()) {
                    is Either.Right -> {
                        Navigation.findNavController(binding.root).navigate(
                            R.id.nameDetailsFragment,
                            Bundle().apply {
                                putString("nameId", it.nameId.value)
                                putString("name", it.name)
                            }
                        )
                    }

                    else -> {
                        binding.root.context.toast(binding.root.context.getString(R.string.invalid_name_id))
                    }
                }
            }

            binding.list.layoutManager = LinearLayoutManager(binding.root.context)
            binding.list.adapter = adapter

            adapter.submitList(model.casts)

        }
    }

    object DiffCallback : DiffUtil.ItemCallback<TitleFullCasts.CastItem>() {
        override fun areItemsTheSame(oldItem: TitleFullCasts.CastItem, newItem: TitleFullCasts.CastItem): Boolean =
            oldItem.title == newItem.title

        override fun areContentsTheSame(oldItem: TitleFullCasts.CastItem, newItem: TitleFullCasts.CastItem): Boolean =
            oldItem == newItem
    }

}