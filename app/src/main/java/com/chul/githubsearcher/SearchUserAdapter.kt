package com.chul.githubsearcher

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.chul.githubsearcher.data.UserInfo
import com.chul.githubsearcher.databinding.ViewSearchListItemBinding

class SearchUserAdapter: PagingDataAdapter<UserInfo, SearchUserViewHolder>(UserInfoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchUserViewHolder {
        val binding = ViewSearchListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchUserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchUserViewHolder, position: Int) {
        val item = getItem(position) ?: return
        holder.bind(item)
    }
}

class UserInfoDiffCallback: DiffUtil.ItemCallback<UserInfo>() {
    override fun areItemsTheSame(oldItem: UserInfo, newItem: UserInfo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: UserInfo, newItem: UserInfo): Boolean {
        return oldItem == newItem
    }

}