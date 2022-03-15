package com.chul.githubsearcher.network

import com.chul.githubsearcher.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkManager {

    private const val baseUrl = "https://api.github.com/"

    /**
     * 하드코딩 된 access token을 github에 push 할 경우 해당 token이 폐지되어
     * local.properties 파일에 추가하여 적용
     */
    private const val token = BuildConfig.GITHUB_ACCESS_TOKEN

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(OkHttpClient.Builder()
            .addInterceptor {
                val request = it.request().newBuilder().addHeader("Authorization", "token $token").build()
                it.proceed(request)
            }
            .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
            .build())
        .build()

    fun getGitHubService(): GitHubService = retrofit.create(GitHubService::class.java)
}