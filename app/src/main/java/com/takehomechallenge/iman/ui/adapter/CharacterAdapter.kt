package com.takehomechallenge.iman.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.takehomechallenge.iman.data.remote.response.CharacterList
import com.takehomechallenge.iman.databinding.ItemRowCharacterBinding

class CharacterAdapter(private val listCharacter: List<CharacterList>) :
    RecyclerView.Adapter<CharacterAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemClicked(data: CharacterList)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRowCharacterBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount() = listCharacter.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listCharacter[position]
        holder.bind(data)
    }

    inner class ViewHolder(private var binding: ItemRowCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: CharacterList) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(data.image)
                    .into(imgItemPhoto)
                tvItemName.text = data.name

                @Suppress("DEPRECATION")
                itemView.setOnClickListener {
                    onItemClickCallback.onItemClicked(listCharacter[adapterPosition])
                }
            }
        }
    }
}