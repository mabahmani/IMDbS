package com.mabahmani.imdb_scraping.ui.main.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mabahmani.domain.vo.HomeExtra
import com.mabahmani.domain.vo.common.Title
import com.mabahmani.imdb_scraping.databinding.ItemBornTodayBinding

class HomeBornTodayAdapter(private val itemClickListener: (HomeExtra.BornToday) -> Unit) : ListAdapter<HomeExtra.BornToday, HomeBornTodayAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            ItemBornTodayBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            itemClickListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    class ViewHolder(itemView: ItemBornTodayBinding, private val itemClickListener: (HomeExtra.BornToday) -> Unit) :
        RecyclerView.ViewHolder(itemView.root) {
            var binding: ItemBornTodayBinding
            init {
                itemView.executePendingBindings()
                binding = itemView
            }
            fun bind(model: HomeExtra.BornToday){

                binding.title = model.name
                binding.imageUrl = model.avatar.getCustomImageHeightUrl(512)

                if (model.death.isNullOrEmpty()){
                    binding.subtitle = model.age
                }
                else{
                    binding.subtitle = String.format("%s-%s", model.birth, model.death)
                }

                binding.root.setOnClickListener {
                    itemClickListener.invoke(model)
                }
            }
    }


    object DiffCallback : DiffUtil.ItemCallback<HomeExtra.BornToday>() {
        override fun areItemsTheSame(oldItem: HomeExtra.BornToday, newItem: HomeExtra.BornToday): Boolean =
            oldItem.nameId.value == newItem.nameId.value

        override fun areContentsTheSame(oldItem: HomeExtra.BornToday, newItem: HomeExtra.BornToday): Boolean =
            oldItem == newItem
    }
}

