package com.chul.githubsearcher.data

import com.google.gson.annotations.SerializedName

data class SearchUserResponse(
    @SerializedName("total_count") val totalCount: String,
    val items: List<UserInfo>?
)