package com.chul.githubsearcher.data

import com.google.gson.annotations.SerializedName

data class UserInfo(
    val login: String,
    val id: String,
    @SerializedName("avatar_url") val avatarUrl: String,
    @SerializedName("repos_url") val repoUrl: String,
    @SerializedName("html_url") val url: String
)