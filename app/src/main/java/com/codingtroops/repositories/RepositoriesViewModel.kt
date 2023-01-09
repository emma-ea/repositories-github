package com.codingtroops.repositories

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


class RepositoriesViewModel(
    private val pagingSource: RepositoriesPagingSource =
        RepositoriesPagingSource()
) : ViewModel() {

    val repositories: Flow<PagingData<Repository>> = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = { pagingSource }
    ).flow.cachedIn(viewModelScope)

}