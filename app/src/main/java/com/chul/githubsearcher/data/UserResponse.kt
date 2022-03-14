package com.chul.githubsearcher.data

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("public_repos") val publicRepos: Int
)