package com.mabahmani.imdb_scraping.ui.main.title

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mabahmani.domain.vo.TitleParentsGuide
import com.mabahmani.imdb_scraping.databinding.ItemTitleParentsGuideBinding
import com.mabahmani.imdb_scraping.databinding.ItemTitleParentsGuideTextBinding

class TitleParentsGuideAdapter : ListAdapter<TitleParentsGuide.Guide, TitleParentsGuideAdapter.ViewHolder>(
    DiffCallback
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemTitleParentsGuideBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(itemView: ItemTitleParentsGuideBinding) :
        RecyclerView.ViewHolder(itemView.root) {

        private var binding: ItemTitleParentsGuideBinding

        init {
            itemView.executePendingBindings()
            binding = itemView
        }

        fun bind(model: TitleParentsGuide.Guide) {

            binding.titleWidget.setTitle(model.title)
            binding.guideRateType = model.guideRateType?.name.orEmpty()

            val adapter = TitleParentsGuideTextAdapter()

            binding.list.layoutManager = LinearLayoutManager(binding.root.context)
            binding.list.adapter = adapter

            adapter.submitList(model.guides)
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<TitleParentsGuide.Guide>() {
        override fun areItemsTheSame(oldItem: TitleParentsGuide.Guide, newItem: TitleParentsGuide.Guide): Boolean =
            oldItem.title == newItem.title

        override fun areContentsTheSame(oldItem: TitleParentsGuide.Guide, newItem: TitleParentsGuide.Guide): Boolean =
            oldItem == newItem
    }

}