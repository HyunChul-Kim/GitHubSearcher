package com.chul.githubsearcher.network

import com.chul.githubsearcher.data.SearchUserResponse
import com.chul.githubsearcher.data.UserInfo
import com.chul.githubsearcher.data.UserResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubService {

    @GET("search/users")
    suspend fun searchUser(
        @Query("q") query: String,
        @Query("per_page") perPage: Int = 30,
        @Query("page") page: Int): SearchUserResponse

    @GET("users/{username}")
    suspend fun getUser(
        @Path("username") userName: String): UserResponse
}