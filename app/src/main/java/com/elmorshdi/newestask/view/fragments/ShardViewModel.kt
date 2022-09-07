package com.elmorshdi.newestask.view.fragments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.elmorshdi.newestask.data.localdata.model.Post
import com.elmorshdi.newestask.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShardViewModel
@Inject constructor(
    private val repository: Repository,
) : ViewModel() {


    val post: StateFlow<Post?>
        get() = _post
    private val _post: MutableStateFlow<Post?> = MutableStateFlow(null)

    private val _filterPostsFlow: MutableSharedFlow<List<Post>> = MutableSharedFlow()
    val filterPostsFlow: SharedFlow<List<Post>>
        get() = _filterPostsFlow

    suspend fun getPosts(): Flow<PagingData<Post>> =
        repository.getPosts().flow.cachedIn(viewModelScope)

    suspend fun getPostById(id: Int) {
        viewModelScope.launch {

            _post.emit(repository.getPostById(id))
        }
    }

    suspend fun getFilteredList(id: Int) {
        val filtered = repository.filterByUser(id)
        _filterPostsFlow.emit(filtered)
    }


}