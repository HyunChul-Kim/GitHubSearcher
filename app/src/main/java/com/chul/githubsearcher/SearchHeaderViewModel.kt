package com.chul.githubsearcher

import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.chul.githubsearcher.network.NetworkManager

class SearchHeaderViewModel: ViewModel() {

    private val gitHubService = NetworkManager.getGitHubService()

    /**
     * 검색 키워드 변경을 관찰하기 위한 LiveData
     * 이 키워드는 searchUser() 호출하면 데이터가 변경 됨
     */
    private val searchKeyword = MutableLiveData<String>().apply { value = "" }

    /**
     * searchKeyword 데이터 변경 될 경우 해당 검색어로 검색하여 PagingData를 return 하는 LiveData
     */
    val userFlow = searchKeyword.switchMap { keyword ->
        Pager(
            PagingConfig(pageSize = 30)
        ) {
            SearchUserPagingSource(gitHubService, keyword)
        }.liveData
            .cachedIn(viewModelScope)
    }

    /**
     * EditText의 text 값과 양방향 데이터 바인딩하는 변수
     * 검색 할 때 이 변수를 이용하여 검색
     */
    var keyword = ""

    fun searchUser(user: String) {
        val query = "$user type:users"
        searchKeyword.value = user
    }
}