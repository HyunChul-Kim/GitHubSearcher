package com.chul.githubsearcher

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chul.githubsearcher.data.UserInfo
import com.chul.githubsearcher.databinding.ViewSearchListItemBinding

class SearchUserViewHolder(
    private val binding: ViewSearchListItemBinding,
    private val itemClick: (String) -> Unit
): RecyclerView.ViewHolder(binding.root) {

    private var url = ""

    init {
        binding.root.setOnClickListener {
            if(url.isNotEmpty()) {
                itemClick(url)
            }
        }

    }

    fun bind(user: UserInfo) {
        Glide.with(binding.root.context).load(user.avatarUrl).into(binding.searchListItemUserProfile)
        binding.searchListItemUserName.text = user.login
        url = user.url
    }
}