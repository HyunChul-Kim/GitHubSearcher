package com.chul.githubsearcher

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.chul.githubsearcher.data.UserInfo
import com.chul.githubsearcher.network.GitHubService
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class SearchUserPagingSource(
    private val service: GitHubService,
    private val query: String
): PagingSource<Int, UserInfo>() {

    override fun getRefreshKey(state: PagingState<Int, UserInfo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserInfo> {
        return try {
            val pageNumber = params.key ?: 1
            val response = service.searchUser(query, page = pageNumber)
            val userList = mutableListOf<UserInfo>()
            val data = response.items ?: emptyList()
            userList.addAll(data)
            coroutineScope {
                userList.forEach {
                    launch {
                        val userResponse = service.getUser(it.login)
                        it.repoCount = userResponse.publicRepos
                    }
                }
            }
            LoadResult.Page(
                data = userList,
                prevKey = if(pageNumber == 1) null else pageNumber - 1,
                nextKey = pageNumber + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}