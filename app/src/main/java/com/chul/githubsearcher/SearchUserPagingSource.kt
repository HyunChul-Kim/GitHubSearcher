package com.chul.githubsearcher

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.chul.githubsearcher.data.UserInfo
import com.chul.githubsearcher.network.GitHubService

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
            // Start refresh at page 1 if undefined.
            val nextPageNumber = params.key ?: 1
            val response = service.searchUser(query, page = nextPageNumber)
            LoadResult.Page(
                data = response.items,
                prevKey = null, // Only paging forward.
                nextKey = nextPageNumber + 1
            )
        } catch (e: Exception) {
            // Handle errors in this block and return LoadResult.Error if it is an
            // expected error (such as a network failure).
            LoadResult.Error(e)
        }
    }
}