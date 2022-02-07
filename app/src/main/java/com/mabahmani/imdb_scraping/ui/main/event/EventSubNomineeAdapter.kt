package com.mabahmani.imdb_scraping.ui.main.event

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mabahmani.domain.vo.EventDetails
import com.mabahmani.imdb_scraping.R
import com.mabahmani.imdb_scraping.databinding.ItemEventSubNomineeBinding

class EventSubNomineeAdapter (private val itemClickListener: (EventDetails.Award.AwardCategory.Nominee.SubNominee) -> Unit) : ListAdapter<EventDetails.Award.AwardCategory.Nominee.SubNominee, EventSubNomineeAdapter.ViewHolder>(
    DiffCallback
)  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemEventSubNomineeBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            itemClickListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(itemView: ItemEventSubNomineeBinding, private val itemClickListener: (EventDetails.Award.AwardCategory.Nominee.SubNominee) -> Unit) :
        RecyclerView.ViewHolder(itemView.root) {

        private var binding: ItemEventSubNomineeBinding

        init {
            itemView.executePendingBindings()
            binding = itemView
        }
        fun bind(model: EventDetails.Award.AwardCategory.Nominee.SubNominee){

            if (bindingAdapterPosition == 0){
                binding.nameView.typeface =
                    Typeface.createFromAsset(
                        binding.root.context.assets,
                        binding.root.context.getString(R.string.font_bold)
                    )
            }

            else{
                binding.nameView.typeface =
                    Typeface.createFromAsset(
                        binding.root.context.assets,
                        binding.root.context.getString(R.string.font_regular)
                    )
            }

            binding.name = model.name
            binding.note = model.note

            binding.root.setOnClickListener {
                itemClickListener.invoke(model)
            }
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<EventDetails.Award.AwardCategory.Nominee.SubNominee>() {
        override fun areItemsTheSame(oldItem: EventDetails.Award.AwardCategory.Nominee.SubNominee, newItem: EventDetails.Award.AwardCategory.Nominee.SubNominee): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: EventDetails.Award.AwardCategory.Nominee.SubNominee, newItem: EventDetails.Award.AwardCategory.Nominee.SubNominee): Boolean =
            oldItem == newItem
    }

}