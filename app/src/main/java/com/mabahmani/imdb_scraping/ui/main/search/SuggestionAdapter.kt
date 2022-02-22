package com.mabahmani.imdb_scraping.ui.main.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mabahmani.domain.vo.Suggestion
import com.mabahmani.domain.vo.common.Genre
import com.mabahmani.domain.vo.common.Video
import com.mabahmani.imdb_scraping.databinding.ItemGenreBinding
import com.mabahmani.imdb_scraping.databinding.ItemSuggestBinding

class SuggestionAdapter (private val itemClickListener: (Suggestion?, Video?) -> Unit) : ListAdapter<Suggestion, SuggestionAdapter.ViewHolder>(
    DiffCallback
)  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemSuggestBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            itemClickListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(itemView: ItemSuggestBinding, private val itemClickListener: (Suggestion?, Video?) -> Unit) :
        RecyclerView.ViewHolder(itemView.root) {

        private var binding: ItemSuggestBinding

        init {
            itemView.executePendingBindings()
            binding = itemView
        }
        fun bind(model: Suggestion){
            binding.imageUrl = model.image.getCustomImageWidthUrl(128)
            binding.title = model.name
            if(model.year == "0")
                binding.year = ""
            else
                binding.year = model.year
            binding.info = model.caption
            when {
                model.videos.size >= 2 -> {
                    binding.videoCover1 = model.videos[0].preview.getCustomImageWidthUrl(256)
                    binding.videoCover2 = model.videos[1].preview.getCustomImageWidthUrl(256)
                    binding.videoDuration1 = model.videos[0].runtime
                    binding.videoDuration2 = model.videos[1].runtime
                    binding.videoTitle1 = model.videos[0].title
                    binding.videoTitle2 = model.videos[1].title
                    binding.videoParent.visibility = View.VISIBLE
                }
                model.videos.isNotEmpty() -> {
                    binding.videoCover1 = model.videos[0].preview.getCustomImageWidthUrl(256)
                    binding.videoDuration1 = model.videos[0].runtime
                    binding.videoTitle1 = model.videos[0].title
                    binding.videoParent.visibility = View.VISIBLE
                }
                else -> {
                    binding.videoCover1 = ""
                    binding.videoCover2 = ""
                    binding.videoParent.visibility = View.GONE
                }
            }

            binding.root.setOnClickListener {
                itemClickListener.invoke(model, null)
            }

            binding.videoImage1Parent.setOnClickListener {
                itemClickListener.invoke(null, model.videos[0])
            }

            binding.videoImage2Parent.setOnClickListener {
                itemClickListener.invoke(null, model.videos[1])
            }
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<Suggestion>() {
        override fun areItemsTheSame(oldItem: Suggestion, newItem: Suggestion): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Suggestion, newItem: Suggestion): Boolean =
            oldItem == newItem
    }

}