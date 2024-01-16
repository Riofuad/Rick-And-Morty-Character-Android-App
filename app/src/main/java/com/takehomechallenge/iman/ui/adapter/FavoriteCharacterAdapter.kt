package com.takehomechallenge.iman.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.takehomechallenge.iman.data.local.entity.FavoriteCharacter
import com.takehomechallenge.iman.databinding.ItemRowCharacterBinding
import com.takehomechallenge.iman.di.CharacterDiffCallback

class FavoriteCharacterAdapter : RecyclerView.Adapter<FavoriteCharacterAdapter.MyViewHolder>() {
    private val listFavorite = ArrayList<FavoriteCharacter>()

    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemClicked(data: FavoriteCharacter)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setListFavorites(favorite: List<FavoriteCharacter>) {
        val diffCallback = CharacterDiffCallback(this.listFavorite, favorite)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listFavorite.clear()
        this.listFavorite.addAll(favorite)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val binding = ItemRowCharacterBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup,
            false
        )
        return MyViewHolder(binding)
    }

    override fun getItemCount() = listFavorite.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = listFavorite[position]
        holder.bind(data)
    }

    inner class MyViewHolder(private val binding: ItemRowCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: FavoriteCharacter) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(data.image)
                    .into(imgItemPhoto)
                tvItemName.text = data.name
            }

            @Suppress("DEPRECATION")
            itemView.setOnClickListener {
                onItemClickCallback.onItemClicked(listFavorite[adapterPosition])
            }
        }
    }
}