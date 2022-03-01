package com.mabahmani.imdb_scraping.ui.main.name.adapter

import android.content.Intent
import android.net.Uri
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mabahmani.domain.vo.NameDetails
import com.mabahmani.imdb_scraping.databinding.ItemNameDetailPersonalDetailBinding

class NameDetailsPersonalDetailsAdapter (private val itemClickListener: (NameDetails.PersonalDetails) -> Unit) : ListAdapter< NameDetails.PersonalDetails, NameDetailsPersonalDetailsAdapter.ViewHolder>(
    DiffCallback
)  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemNameDetailPersonalDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            itemClickListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(itemView: ItemNameDetailPersonalDetailBinding, private val itemClickListener: ( NameDetails.PersonalDetails) -> Unit) :
        RecyclerView.ViewHolder(itemView.root) {

        private var binding: ItemNameDetailPersonalDetailBinding

        init {
            itemView.executePendingBindings()
            binding = itemView
        }
        fun bind(model:  NameDetails.PersonalDetails){

            binding.title = model.title

            if (model.title.contains("Sites", true)){
                val spannableStringBuilder = SpannableStringBuilder()
                var start = 1
                model.links.forEach {

                    if (!it.title.contains("see more", true)){

                        spannableStringBuilder.append(it.title + " ")

                        try {
                            spannableStringBuilder.setSpan(object : ClickableSpan(){
                                override fun onClick(widget: View) {
                                    try {
                                        startActivity(binding.root.context, Intent(Intent.ACTION_VIEW, Uri.parse(it.url)), null)
                                    }catch (ex: Exception){

                                    }
                                }
                            },start - 1, start - 1 + it.title.length, SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE)

                            start = it.title.length + 2

                        }catch (ex: Exception){

                        }
                    }

                }
                binding.subtitleView.linksClickable = true
                binding.subtitleView.isClickable = true
                binding.subtitleView.movementMethod = LinkMovementMethod.getInstance()
                binding.subtitleView.text = spannableStringBuilder
            }

            else{
                binding.subtitle = model.subtitle
            }


            binding.root.setOnClickListener {
                itemClickListener.invoke(model)
            }
        }
    }

    object DiffCallback : DiffUtil.ItemCallback< NameDetails.PersonalDetails>() {
        override fun areItemsTheSame(oldItem:  NameDetails.PersonalDetails, newItem:  NameDetails.PersonalDetails): Boolean =
            oldItem.title == newItem.title

        override fun areContentsTheSame(oldItem:  NameDetails.PersonalDetails, newItem:  NameDetails.PersonalDetails): Boolean =
            oldItem == newItem
    }

}