package com.takehomechallenge.iman.di

import androidx.recyclerview.widget.DiffUtil
import com.takehomechallenge.iman.data.local.entity.FavoriteCharacter

class CharacterDiffCallback(
    private val mOldUserList: List<FavoriteCharacter>,
    private val mNewUserList: List<FavoriteCharacter>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = mOldUserList.size

    override fun getNewListSize(): Int = mNewUserList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldUserList[oldItemPosition].id == mNewUserList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldEmployee = mOldUserList[oldItemPosition]
        val newEmployee = mOldUserList[newItemPosition]
        return oldEmployee.name == newEmployee.name
    }
}