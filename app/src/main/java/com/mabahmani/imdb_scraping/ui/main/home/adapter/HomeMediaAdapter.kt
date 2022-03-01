package com.mabahmani.imdb_scraping.ui.main.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mabahmani.domain.vo.Home
import com.mabahmani.domain.vo.enum.HomeMediaType
import com.mabahmani.imdb_scraping.R
import com.mabahmani.imdb_scraping.databinding.ItemHomeMediaBinding

class HomeMediaAdapter(private val itemClickListener: (Home.Media) -> Unit) : ListAdapter<Home.Media, HomeMediaAdapter.ViewHolder>(
    DiffCallback
) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            ItemHomeMediaBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            itemClickListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    class ViewHolder(itemView: ItemHomeMediaBinding, private val itemClickListener: (Home.Media) -> Unit) :
        RecyclerView.ViewHolder(itemView.root) {
            var binding: ItemHomeMediaBinding
            init {
                itemView.executePendingBindings()
                binding = itemView
            }
            fun bind(model: Home.Media){
                when(model.type){
                    HomeMediaType.LIST ->{
                        binding.icon.setImageResource(R.drawable.ic_bx_images)
                        binding.iconDescriptionText = null
                    }
                    HomeMediaType.GALLERY ->{
                        binding.icon.setImageResource(R.drawable.ic_bx_images)
                        binding.iconDescriptionText = null
                    }
                    HomeMediaType.VIDEO ->{
                        binding.icon.setImageResource(R.drawable.ic_bx_play_circle)
                        binding.iconDescriptionText = model.caption
                    }
                }

                binding.imageUrl = model.image.getCustomImageWidthUrl(512)
                binding.titleText = model.title

                if (model.date != null){
                    binding.dateText = model.date
                }

                binding.root.setOnClickListener {
                    itemClickListener.invoke(model)
                }
            }

    }


    object DiffCallback : DiffUtil.ItemCallback<Home.Media>() {
        override fun areItemsTheSame(oldItem: Home.Media, newItem: Home.Media): Boolean =
            oldItem.title == newItem.title

        override fun areContentsTheSame(oldItem: Home.Media, newItem: Home.Media): Boolean =
            oldItem == newItem
    }
}

