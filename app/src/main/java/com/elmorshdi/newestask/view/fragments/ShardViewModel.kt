package com.elmorshdi.newestask.view.fragments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.elmorshdi.newestask.data.localdata.dao.Dao
import com.elmorshdi.newestask.data.localdata.model.Post
import com.elmorshdi.newestask.doman.RepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.emitAll
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
@HiltViewModel
class ShardViewModel
@Inject constructor(
    private val postRemoteMediator: NewsPageKeyedRemoteMediator,
    private val repository: RepositoryImp,
    private val dao: Dao
) : ViewModel() {


    @ExperimentalPagingApi
    val pager = Pager(
        PagingConfig(pageSize = 10),
        remoteMediator = postRemoteMediator
    ) {
        dao.getAllPosts()
    }.flow

    val pp = Pager(
        config = PagingConfig(10, enablePlaceholders = false),
        remoteMediator = postRemoteMediator
    ) {
        dao.getAllPosts()
    }.flow
    private val _posts: MutableSharedFlow<PagingData<Post>> = MutableSharedFlow()
    val posts: SharedFlow<PagingData<Post>>
        get() = _posts


    suspend fun gg(): Flow<PagingData<Post>> = repository.getPosts().flow.cachedIn(viewModelScope)

    suspend fun getPosts() {
        _posts.emitAll(repository.getPosts().flow.cachedIn(viewModelScope))

    }

}