package com.chul.githubsearcher

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chul.githubsearcher.data.UserInfo
import com.chul.githubsearcher.databinding.ViewSearchListItemBinding

class SearchUserViewHolder(
    private val binding: ViewSearchListItemBinding
): RecyclerView.ViewHolder(binding.root) {

    fun bind(user: UserInfo) {
        Glide.with(binding.root.context).load(user.avatarUrl).into(binding.searchListItemUserProfile)
        binding.searchListItemUserName.text = user.login
    }
}